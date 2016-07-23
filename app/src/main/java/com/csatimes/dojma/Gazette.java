package com.csatimes.dojma;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.csatimes.dojma.DHC.directory;


public class Gazette extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_WRITE_STORAGE = 112;
    ArrayAdapter<String> adapter;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String[] pdfsList = null;
    private boolean hasPermission;
    private GazetteItem[] gazetteItems;
    private String currentFileDownloading = "";
    private int flag = 0;
    private boolean downloading = false;
    private String firstTime = "GAZETTE_FIRST_TIME";
    private TextView emptyList;

    public Gazette() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasPermission = true;
                } else {
                    Snackbar.make(listView, "Write permission denied, pdfs will be handled by browser", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate gazettes");
        sharedPreferences = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        int pdfs = sharedPreferences.getInt("GAZETTE_number", 0);
        pdfsList = new String[0];
        gazetteItems = new GazetteItem[pdfs];
        if (pdfs != 0) {
            adapter = new ArrayAdapter<>(getContext(), R.layout.gazette_list_format, pdfsList);
            pdfsList = new String[pdfs];
            for (int i = 0; i < pdfs; i++) {
                pdfsList[i] = sharedPreferences.getString("GAZETTE_number_" + i + "_title", "null");
                gazetteItems[i] = new GazetteItem(pdfsList[i], sharedPreferences.getString("GAZETTE_number_" + i + "_url", "null"));
                if (isOnline()) {
                    new DownloadList(0).execute();
                }
            }
        } else {
            Log.e("TAG", "gazettes zero!");
            editor.putInt("GAZETTE_number", 0);
            editor.apply();
        }
        downloading = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG", "onResum gazette");
        if (isOnline()) {
            new DownloadList(0).execute();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);
        downloading = false;

        Log.e("TAG", "onCreateView of gazettes called");

        listView = (ListView) view.findViewById(R.id.gazette_listview);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.gazette_swipe);

        swipe.setColorSchemeResources(R.color.amber500, R.color.blue500, R.color
                .brown500, R.color.cyan500, R.color.deeporange500, R.color.deepPurple500, R.color.green500, R
                .color.grey500, R.color.indigo500, R.color.lightblue500, R.color.lime500, R.color
                .orange500, R.color.pink500, R.color.red500, R.color.teal500, R.color.violet500, R
                .color.yellow500);

        swipe.setRefreshing(false);
        if (adapter != null) listView.setAdapter(adapter);

        hasPermission = (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        listView.setOnItemClickListener(this);
        swipe.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        if (isOnline())
            new DownloadList(1).execute();
        else {
            Snackbar.make(listView, R.string.no_internet_msg, Snackbar.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (isOnline())
            new DownloadList(0).execute();

        if (pdfsList.length != 0 && hasPermission) {

            {
                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = gazetteItems[i].getTitle() + ".pdf";
                File file = new File(pdfFolder, pdfName);


                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Snackbar snackBar = Snackbar.make(listView, "No application to load PDF", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                        snctetxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        snackBar.show();
                    }
                } else {
                    if (isOnline())
                        if (!downloading) {
                            new DownloadPDF(gazetteItems[i].getTitle()).execute(gazetteItems[i].getLink());
                            Snackbar snackBar = Snackbar.make(listView, "Starting download ", Snackbar.LENGTH_LONG);
                            snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                            TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                            snctetxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                            snackBar.show();
                        } else {
                            Snackbar snackBar = Snackbar.make(listView, "Try again later. Another file download in progress", Snackbar.LENGTH_LONG);
                            snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                            TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                            snctetxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                            snackBar.show();
                        }
                    else {
                        Snackbar.make(listView, R.string.no_internet_msg, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        } else if (!hasPermission) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gazetteItems[i].getLink()));
            startActivity(intent);
        }

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    private class DownloadPDF extends AsyncTask<String, Integer, Void> {
        String title = "";
        File checkFile;
        String link = "";
        boolean failure = false;

        DownloadPDF(String title) {
            this.title = title;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            downloading = true;
            currentFileDownloading = title;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == 0) {
                Snackbar snackBar = Snackbar.make(listView, "Download failed", Snackbar.LENGTH_LONG);
                snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red500));
                snackBar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DownloadPDF(title).execute(link);
                    }
                });
                TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                TextView snctetxt2 = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_action);
                snctetxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                snctetxt2.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                snackBar.show();
                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = title + ".pdf";
                failure = true;
                checkFile = new File(pdfFolder, pdfName);
                if (checkFile.exists()) {
                    checkFile.delete();
                }
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            link = strings[0];
            try {
                URL url = new URL(strings[0]);
                Log.e("TAG", strings[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = title + ".pdf";
                File file = new File(pdfFolder, pdfName);
                FileOutputStream f = new FileOutputStream(file);
                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                failure = true;

                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
                failure = false;

            } catch (Exception e) {
                e.printStackTrace();
                publishProgress(0);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            downloading = false;
            failure = true;

            File pdfFolder = new File(directory + "/");
            pdfFolder.mkdirs();
            String pdfName = title + ".pdf";

            checkFile = new File(pdfFolder, pdfName);
            if (checkFile.exists()) {
                checkFile.deleteOnExit();
                checkFile.delete();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!failure) {
                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = title + ".pdf";

                checkFile = new File(pdfFolder, pdfName);
                if (checkFile.exists()) {
                    Uri pathuri = Uri.fromFile(checkFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(pathuri, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Snackbar snackBar = Snackbar.make(listView, "No application to load PDF", Snackbar.LENGTH_LONG);
                        snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                        snctetxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        snackBar.show();
                    }
                }
            } else {
                File pdfFolder = new File(directory + "/");
                boolean folder = pdfFolder.mkdirs();
                String pdfName = currentFileDownloading + ".pdf";
                File deleteFile = new File(pdfFolder, pdfName);
                if (deleteFile.exists()) {
                    Log.e("TAG", "failed file exists!");
                    boolean delete = deleteFile.delete();
                    if (!delete) {
                        Log.e("TAG", "couldn't delete file");
                        boolean folderDelete = pdfFolder.delete();
                        if (!folderDelete) {
                            Snackbar.make(listView, "Seems like a pdf was not downloaded successfully. " +
                                    "Please manually delete dojma folder", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
            downloading = false;
        }
    }

    private class DownloadList extends AsyncTask<Void, Void, Void> {

        InputStream stream;
        InputStreamReader reader;
        BufferedReader scanner;
        private String response;
        private int num = 1;

        public DownloadList(int num) {
            this.num = num;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(DHC.GazetteLink);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                StringBuilder sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
                response = sb.toString();
                in.close();
            } catch (Exception e) {
                response = null;
                if (num == 1)
                    Toast.makeText(getContext(), "Did not receive response from server or input/output exception", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (num == 1)
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            try {

                scanner.close();
                reader.close();
                stream.close();

            } catch (IOException e) {
                if (num == 1) {
                    Toast.makeText(getContext(), "IOexp on cancelled", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Nothing really something you can solve :P", Toast.LENGTH_SHORT).show();
                }
            }
            if (num == 1) {
                swipe.setRefreshing(false);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (num == 1) {
                swipe.setRefreshing(false);
            }

            if (response != null) {
                try {
                    stream = new ByteArrayInputStream(response.getBytes("US-ASCII"));
                    reader = new InputStreamReader(stream);
                    scanner = new BufferedReader(reader);
                    int noOfPdfs = Integer.parseInt(scanner.readLine());
                    editor.putInt("GAZETTE_number", noOfPdfs);
                    GazetteItem[] items = new GazetteItem[noOfPdfs];
                    String[] titles = new String[noOfPdfs];
                    for (int i = 0; i < noOfPdfs; i++) {
                        items[i] = new GazetteItem(scanner.readLine(), scanner.readLine());
                        titles[i] = items[i].getTitle();
                        editor.putString("GAZETTE_number_" + i + "_title", items[i].getTitle());
                        editor.putString("GAZETTE_number_" + i + "_url", items[i].getLink());
                    }
                    gazetteItems = items;
                    editor.apply();

                    scanner.close();
                    reader.close();
                    stream.close();

                    if (num == 1)
                        Toast.makeText(getContext(), "List updated!", Toast.LENGTH_SHORT).show();

                    if (adapter != null) {
                        Log.e("TAG", "adapter not null! notified");
                        pdfsList = titles;
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("TAG", "adapter was null");
                        pdfsList = titles;
                        adapter = new ArrayAdapter<>(getContext(), R.layout.gazette_list_format, pdfsList);
                        listView.setAdapter(adapter);
                        Toast.makeText(getContext(), "Adapter set for first time", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (num == 1) {
                        Toast.makeText(getContext(), "IOException Reload again later", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Nothing really something you can solve :P (Your phone's issue) ", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                if (num == 1)
                    Toast.makeText(getContext(), "Could not download gazette list! Try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


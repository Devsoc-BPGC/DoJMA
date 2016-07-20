package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.csatimes.dojma.DHC.directory;


public class Gazette extends Fragment {

    ListView listView;

    public Gazette() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);
        String[] text = {
                "Volume 1 Issue 5",
                "Volume 1 Issue 4",
                "Volume 1 Issue 3",
                "Volume 1 Issue 2",
                "Volume 1 Issue 1"
        };
        final String[] gazetteLinks = {
                "https://github.com/MobileApplicationsClub/DoJMA-Assets-Repo/raw/master/Gazette/pdfs/vol1issue5.pdf\n",
                "https://github.com/MobileApplicationsClub/DoJMA-Assets-Repo/raw/master/Gazette/pdfs/vol1issue4.pdf\n",
                "https://github.com/MobileApplicationsClub/DoJMA-Assets-Repo/raw/master/Gazette/pdfs/vol1issue3.pdf\n",
                "https://github.com/MobileApplicationsClub/DoJMA-Assets-Repo/raw/master/Gazette/pdfs/vol1issue2.pdf\n",
                "https://github.com/MobileApplicationsClub/DoJMA-Assets-Repo/raw/master/Gazette/pdfs/vol1issue1.pdf\n"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.gazette_list_format, text);
        listView = (ListView) view.findViewById(R.id.gazette_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = "vol1issue" + (5 - i) + ".pdf";
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
                        snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red500));

                        //To get the snackbar text
                        TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);

                        //Set color of the snackbar
                        snctetxt.setTextColor(ContextCompat.getColor
                                (getContext(), R.color.white));


                        snackBar.show();
                    }
                } else {
                    new DownloadPDF(i).execute(gazetteLinks[i]);
                    Snackbar snackBar = Snackbar.make(listView, "Starting download ", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    //To get the snackbar text
                    TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    TextView snctetxt2 = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_action);

                    //Set color of the snackbar
                    snctetxt.setTextColor(ContextCompat.getColor
                            (getContext(), R.color.white));
                    snctetxt2.setTextColor(ContextCompat.getColor
                            (getContext(), R.color.white));
                    snackBar.show();
                }
            }
        });
        return view;
    }

    class DownloadPDF extends AsyncTask<String, Integer, Void> {
        String response;
        int number = 0;
        File checkFile;

        public DownloadPDF(int index) {
            number = index;
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
                        new DownloadPDF(number).execute();
                    }
                });
                //To get the snackbar text
                TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
                TextView snctetxt2 = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_action);

                //Set color of the snackbar
                snctetxt.setTextColor(ContextCompat.getColor
                        (getContext(), R.color.white));
                snctetxt2.setTextColor(ContextCompat.getColor
                        (getContext(), R.color.white));

                snackBar.show();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                // Read all the text returned by the server
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                File pdfFolder = new File(directory + "/");
                pdfFolder.mkdirs();
                String pdfName = "vol1issue" + (5 - number) + ".pdf";

                File file = new File(pdfFolder, pdfName);
                FileOutputStream f = new FileOutputStream(file);


                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    f.write(buffer, 0, len1);
                }
                f.close();
            } catch (Exception e) {
                publishProgress(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            File pdfFolder = new File(directory + "/");
            pdfFolder.mkdirs();
            String pdfName = "vol1issue" + (5 - number) + ".pdf";

            checkFile = new File(pdfFolder, pdfName);

            if (checkFile.exists()) {
                Log.e("TAG", "file downloaded");

                Uri pathuri = Uri.fromFile(checkFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pathuri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Snackbar snackBar = Snackbar.make(listView, "No application to load PDF", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red500));

                    //To get the snackbar text
                    TextView snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);

                    //Set color of the snackbar
                    snctetxt.setTextColor(ContextCompat.getColor
                            (getContext(), R.color.white));
                    snctetxt = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);

                    //Set color of the snackbar
                    snctetxt.setTextColor(ContextCompat.getColor
                            (getContext(), R.color.white));

                    snackBar.show();
                }

            } else {
                Log.e("TAG", "file does not exist");
            }


        }
    }
}


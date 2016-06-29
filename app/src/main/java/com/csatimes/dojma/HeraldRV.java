package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Vikramaditya Kukreja on 19-06-2016.
 */

public class HeraldRV extends RecyclerView.Adapter<HeraldRV.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private RealmResults<HeraldNewsItemFormat> results;
    private int pixels = DHC.dpToPx(25);
    private Realm database;

    public HeraldRV(Context context, RealmResults<HeraldNewsItemFormat> results, Realm database) {
        this.context = context;
        this.results = results;
        this.database = database;
        Fresco.initialize(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View herald_card_view_format = inflater.inflate(R.layout.herald_card_view_format, parent, false);
        // Return a new holder instance
        return new ViewHolder(herald_card_view_format);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final HeraldNewsItemFormat foobar = results.get(position);
        holder.date.setText(foobar.getOriginalDate());
        holder.author.setText(foobar.getAuthor());
        holder.title.setText(foobar.getTitle());

        final int pos = position;


        if (foobar.getDesc() == null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(results.get(position).getLink(), new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            HeraldNewsItemFormat foo = bgRealm.where(HeraldNewsItemFormat.class)
                                    .equalTo("postID", results.get(pos).getPostID())
                                    .findFirst();
                            Document bar = Jsoup.parse(response);
                            Elements _foobar = bar.getElementsByTag("p");
                            if (_foobar.first().hasText()) {
                                StringBuilder sb = new StringBuilder(_foobar.first().text());
                                if (sb.length() < 100) {
                                    sb.append(" ... ");
                                    if (_foobar.last().hasText())
                                        sb.append(_foobar.last().text());
                                }
                                foo.setDesc(sb.toString());
                            } else {
                                foo.setDesc(foo.getTitle());
                            }
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(request);
        } else {
            holder.desc.setText(foobar.getDesc());
        }
        holder.imageView.setImageURI(Uri.parse(foobar.getImageURL())
        );

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT <= 22) {
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
        } else {

        }
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        //results.deleteFromRealm(position);
        this.notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SimpleDraweeView imageView;
        public TextView title;
        public TextView author;
        public TextView date;
        public TextView desc;
        //  public ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.herald_rv_item_image);
            author = (TextView) itemView.findViewById(R.id.herald_rv_item_author);
            date = (TextView) itemView.findViewById(R.id.herald_rv_item_date);
            title = (TextView) itemView.findViewById(R.id.herald_rv_item_title);
            desc = (TextView) itemView.findViewById(R.id.herald_rv_desc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            {
                if (view.getId() == itemView.getId()) {
                    /*if (isOnline())*/
                    {
                        Intent openWebpage = new Intent(context, OpenWebpage.class);
                        openWebpage.putExtra("URL", results.get(getAdapterPosition()).getLink());
                        openWebpage.putExtra("TITLE", results.get(getAdapterPosition()).getTitle());
                        context.startActivity(openWebpage);
                    }/* else {
                        Snackbar.make(view, "Unable to connect to internet", Snackbar.LENGTH_LONG).show();
                    }*/
                }
            }

        }
    }
}

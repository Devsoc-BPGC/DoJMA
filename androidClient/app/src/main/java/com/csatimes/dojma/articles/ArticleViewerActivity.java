package com.csatimes.dojma.articles;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.GetPostResponse;
import com.csatimes.dojma.models.Image;
import com.csatimes.dojma.models.Post;
import com.csatimes.dojma.utilities.Browser;
import com.csatimes.dojma.utilities.DojmaApi;
import com.csatimes.dojma.utilities.DojmaApiValues;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.csatimes.dojma.models.Post.FIELD_ID;
import static com.csatimes.dojma.models.Post.persistInRealm;
import static com.csatimes.dojma.utilities.DHC.MIME_TYPE_HTML;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DojmaApiValues.DOJMA_API_BASE_URL;

/**
 * Activity to read article in custom ui.
 * Use {@link #readArticle(Context, int)} to launch this activity conveniently.
 *
 * @author Rushikesh Jogdand [rushikeshjogdand1@gmail.com]
 */
public class ArticleViewerActivity extends AppCompatActivity {

    /**
     * Key to pass articleId with.
     */
    public static final String EXTRA_ARTICLE_ID = "articleId";
    private static final String TAG = TAG_PREFIX + ArticleViewerActivity.class.getSimpleName();
    private final SimpleDateFormat articleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private int articleId;

    private TextView titleTv;
    private TextView dateTv;
    private View emptySpace;
    private SimpleDraweeView articleImage;
    private WebView contentWv;
    private Browser browser;
    private Realm realm;

    /**
     * Convenient method to launch this activity.
     *
     * @param context   context.
     * @param articleId of desired article.
     */
    public static void readArticle(@NonNull final Context context, final int articleId) {
        final Intent intent = new Intent(context, ArticleViewerActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acquireArticleId(getIntent(), savedInstanceState);
        browser = new Browser(this);
        initViews();
        realm = Realm.getDefaultInstance();
        realm.where(Post.class)
                .equalTo(FIELD_ID, articleId)
                .findAllAsync()
                .addChangeListener(posts -> {
                    final Post post;
                    if (posts.size() > 0) {
                        post = posts.get(0);
                        if (post != null) {
                            handlePost(realm.copyFromRealm(post));
                        }
                    }
                });
        final DojmaApi api = new Retrofit.Builder()
                .baseUrl(DOJMA_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DojmaApi.class);
        //noinspection NullableProblems
        api.getPost(articleId).enqueue(new Callback<GetPostResponse>() {
            @Override
            public void onResponse(final Call<GetPostResponse> call, final Response<GetPostResponse> response) {
                final GetPostResponse body = response.body();
                if (!response.isSuccessful()
                        || body == null
                        || !"ok".equals(body.status)) {
                    Log.e(TAG, "problem in response, call was " + call + " response is:" + response);
                    finish();
                    return;
                }
                final Post post = body.post;
                persistInRealm(post, realm);
            }

            @Override
            public void onFailure(final Call<GetPostResponse> call, final Throwable t) {
                Log.e(TAG, "call: " + call + " error message: " + t.getMessage(), t);
                finish();
            }
        });
    }

    private void acquireArticleId(final Intent intent, final Bundle savedInstanceState) {
        final boolean didCallerIntentPassId = intent != null && intent.hasExtra(EXTRA_ARTICLE_ID);
        final boolean isIdInSavedInstanceState = savedInstanceState != null && savedInstanceState.containsKey(EXTRA_ARTICLE_ID);
        if (didCallerIntentPassId) {
            articleId = intent.getIntExtra(EXTRA_ARTICLE_ID, 0);
        } else if (isIdInSavedInstanceState) {
            articleId = savedInstanceState.getInt(EXTRA_ARTICLE_ID);
        } else {
            throw new IllegalStateException("Must call with an intent passing article id");
        }
    }

    private void initViews() {
        setContentView(R.layout.activity_article_viewer);
        findViewById(R.id.fab_back).setOnClickListener(view -> onBackPressed());
        titleTv = findViewById(R.id.tv_article_title);
        dateTv = findViewById(R.id.tv_article_date);
        contentWv = findViewById(R.id.wv_article_content);
        contentWv.getSettings().setJavaScriptEnabled(false);
        contentWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
                browser.launchUrl(request.getUrl().toString());
                return true;
            }
        });
        articleImage = findViewById(R.id.sdv_article_image);
        emptySpace = findViewById(R.id.space_above_article_card);
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putInt(EXTRA_ARTICLE_ID, articleId);
        super.onSaveInstanceState(outState);
    }

    private void handlePost(@NonNull final Post post) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            titleTv.setText(Html.fromHtml(post.title, Html.FROM_HTML_MODE_LEGACY));
        } else {
            titleTv.setText(Html.fromHtml(post.title));
        }
        contentWv.loadData(post.content, MIME_TYPE_HTML, null);
        try {
            dateTv.setText(Html.fromHtml(articleDateFormat.format(DojmaApiValues.DOJMA_API_DATE_SDF.parse(post.date))));
        } catch (final ParseException e) {
            Log.e(TAG, e.getMessage(), e.fillInStackTrace());
        }
        if (post.fullThumbnailImage != null) {
            final Image headerImage = post.fullThumbnailImage;
            final int scaledHeight = headerImage.height * Resources.getSystem().getDisplayMetrics().widthPixels / headerImage.width;
            final ViewGroup.LayoutParams imageParams = articleImage.getLayoutParams();
            imageParams.height = scaledHeight;
            articleImage.setLayoutParams(imageParams);
            articleImage.setImageURI(headerImage.url);
            final ViewGroup.LayoutParams params = emptySpace.getLayoutParams();
            params.height = scaledHeight;
            emptySpace.setLayoutParams(params);
        }
    }
}

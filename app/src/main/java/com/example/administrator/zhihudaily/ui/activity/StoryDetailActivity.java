package com.example.administrator.zhihudaily.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.inter.StoryDetailViewInterface;
import com.example.administrator.zhihudaily.presenter.StoryDetailPresenter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/31.
 */

public class StoryDetailActivity extends AppCompatActivity implements StoryDetailViewInterface {
    public static final String ID = "id";

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.story_detail_layout)
    CoordinatorLayout storyDetailLayout;

    private int id;
    private StoryDetailPresenter storyDetailPresenter;
    private String css, html;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra(ID, -1);
        storyDetailPresenter = new StoryDetailPresenter(this);
        storyDetailPresenter.fetchDetailResult(id);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webview.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webview.getSettings().setAppCacheEnabled(true);
    }


    @Override
    public void showTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void showTitlePage(String url) {
        Glide.with(this)
                .load(url)
                .into(backdrop);
    }

    @Override
    public void showWebView(String body) {
        css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        html = "<html><head>" + css + "</head><body>" + body + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webview.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showError() {
        Snackbar.make(storyDetailLayout, "没有网络连接", Snackbar.LENGTH_SHORT).show();
    }
}

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.injector.component.DaggerStoryDetailComponent;
import com.example.administrator.zhihudaily.injector.component.StoryDetailComponent;
import com.example.administrator.zhihudaily.injector.module.ActivityModule;
import com.example.administrator.zhihudaily.injector.module.StoryDetailModule;
import com.example.administrator.zhihudaily.presenter.StoryDetailPresenter;
import com.example.administrator.zhihudaily.presenter.contract.StoryDetailContract;
import com.example.administrator.zhihudaily.utils.SharedPrefUtils;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/31.
 */

public class StoryDetailActivity extends AppCompatActivity implements StoryDetailContract.View {
    @Inject
    StoryDetailPresenter mPresenter;
    public static final String ID = "id";
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.story_detail_layout)
    CoordinatorLayout storyDetailLayout;
    private int id;
    private String css, html;
    private boolean isNightMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
        mPresenter.attachView(this);
        isNightMode = SharedPrefUtils.isNightMode(this);
        if (isNightMode)
            setTheme(R.style.NightTheme);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra(ID, -1);
        mPresenter.fetchDetailResult(id);
        initView();
    }

    private void initInject(){
        StoryDetailComponent storyDetailComponent = DaggerStoryDetailComponent.builder()
                .applicationComponent(((DailyApplication)getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .storyDetailModule(new StoryDetailModule(this))
                .build();
        storyDetailComponent.inject(this);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
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
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backdrop);
    }

    @Override
    public void showWebView(String body) {
        css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        html = !isNightMode ? "<html><head>" + css + "</head><body>" + body + "</body></html>" : "<html><head>" + css + "</head><body class=\"night\">" + body + "</body></html>";
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}

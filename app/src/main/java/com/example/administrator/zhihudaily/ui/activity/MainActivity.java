package com.example.administrator.zhihudaily.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.app.DailyApplication;
import com.example.administrator.zhihudaily.base.AppActivity;
import com.example.administrator.zhihudaily.base.BaseFragment;
import com.example.administrator.zhihudaily.injector.component.ApplicationComponent;
import com.example.administrator.zhihudaily.ui.fragment.HomeFragment;
import com.example.administrator.zhihudaily.ui.fragment.MenuFragment;
import com.example.administrator.zhihudaily.utils.SharedPrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    MenuFragment mMenuFragment;
    Boolean isNightMode;
    BaseFragment mCurrentFragment;
    public void replaceFragment(BaseFragment fragment) {
        addFragment(fragment);
        mCurrentFragment = fragment;
        closeMenu();
    }


    @Override
    protected BaseFragment getFirstFragment() {
        mCurrentFragment = HomeFragment.getInstance();
        return mCurrentFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fl_content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ServiceManager serviceManager = new ServiceManager(this);
//        serviceManager.startService();
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        mMenuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu_fragment);
        setTitle("首页");
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void refreshUI() {
        mCurrentFragment.refreshUI();
        mMenuFragment.refreshUI();
        refreshToolBar();
    }

    private void refreshSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue statusBarColor = new TypedValue();
            TypedValue navigationBarColor = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimaryDark, statusBarColor, true);
            theme.resolveAttribute(R.attr.colorPrimaryDark, navigationBarColor, true);
            Resources resources = getResources();
            getWindow().setStatusBarColor(resources.getColor(statusBarColor.resourceId));
            getWindow().setNavigationBarColor(resources.getColor(navigationBarColor.resourceId));
        }

    }

    private void refreshToolBar() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        mToolbar.setBackgroundColor(getResources().getColor(typedValue.resourceId));
    }

    private void toggleThemeSetting() {
        isNightMode = SharedPrefUtils.isNightMode(this);
        if (isNightMode) {
            setTheme(R.style.DayTheme);
            isNightMode = false;
        } else {
            setTheme(R.style.NightTheme);
            isNightMode = true;
        }
        SharedPrefUtils.setNightMode(this,isNightMode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_action_daynight:
                showAnimation();
                toggleThemeSetting();
                refreshUI();
                refreshSystemBar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    public void setTitle(String title){
        mToolbar.setTitle(title);
    }

    private void showAnimation() {
        final View decorview = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorview);
        if (decorview instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackground(new BitmapDrawable((getResources()), cacheBitmap));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorview).addView(view, layoutParams);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorview).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    @Override
    public void onClick(View view) {

    }

    public ApplicationComponent getApplicationComponent() {
        ApplicationComponent applicationComponent = ((DailyApplication) getApplication()).getApplicationComponent();
        return applicationComponent;
    }
}

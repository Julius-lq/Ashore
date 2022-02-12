package cn.lgzbs.ashore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Build;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.lgzbs.ashore.fragment.GuidePage_1;
import cn.lgzbs.ashore.fragment.GuidePage_3;
import cn.lgzbs.ashore.fragment.GuidePage_2;


public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private GuidePageAdapter mPageAdapter;
    private List<Fragment> mFragments;
    private LinearLayout mDotLayout;
    private ImageView[] dots;
    private int currentIndex;

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        mDotLayout = (LinearLayout) findViewById(R.id.dots_layout);
    }

    private void initData() {

        mFragments = new ArrayList<Fragment>();
        mFragments.add(GuidePage_1.newInstance());
        mFragments.add(GuidePage_2.newInstance());
        mFragments.add(GuidePage_3.newInstance());
        mFragments.add(GuidePage_4.newInstance());
//        mFragments.add(GuidePage_4.newInstance());
//        mFragments.add(GuidePage_5.newInstance());
        mPageAdapter = new GuidePageAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mPageAdapter);
    }

    private void initListeners() {

        mViewPager.addOnPageChangeListener(this);
    }

    private void initDots() {

        dots = new ImageView[mFragments.size()];
        for (int i = 0; i < mFragments.size(); i++) {
            dots[i] = (ImageView) mDotLayout.getChildAt(i);
            if (i != 0) {
                dots[i].setImageResource(R.drawable.ic_circle_dot);
            }
        }
        currentIndex = 0;
        dots[currentIndex].setImageResource(R.drawable.ic_rect_dot);
    }

    private void setCurrentDot(int position) {

        if (position < 0 || position > mFragments.size() - 1
                || currentIndex == position) {
            return;
        }
        dots[position].setImageDrawable(null);
        dots[position].setImageResource(R.drawable.ic_rect_dot);
        dots[currentIndex].setImageDrawable(null);
        dots[currentIndex].setImageResource(R.drawable.ic_circle_dot);
        currentIndex = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }//隐藏顶部导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }//隐藏顶部导航栏
        }
        initView();
        initData();
        initDots();
        initListeners();
    }

    // 当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {

        // 设置底部小点选中状态
        setCurrentDot(position);
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

}


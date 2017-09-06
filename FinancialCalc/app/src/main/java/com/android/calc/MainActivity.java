package com.android.calc;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FinancialFragment.OnFragmentInteractionListener{
    private final static String TAG = "fht";

    @BindView(R.id.viewpager)
    public ViewPager mViewPager;

    @BindView(R.id.navigation)
    public BottomNavigationView navigation;

    @BindView(R.id.container)
    ConstraintLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d(TAG, "select home");
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    Log.d(TAG, "select dashbaord");
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    Log.d(TAG, "select notification");
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        //为viewpager添加页面改变事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG," pageScroll postion = "+position+" , positionOffset ="+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //将当前的页面对应的底部标签设为选中状态
                Log.d(TAG, "　pageSelected position = "+position);
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "pageScrollStateChanged  state = "+state);
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "uri = "+uri.toString());
    }


    class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FinancialFragment.newInstance("1111","2222");
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

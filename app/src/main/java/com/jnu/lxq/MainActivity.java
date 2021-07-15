package com.jnu.lxq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.jnu.lxq.dataprocessor.PayDataBank;
import com.jnu.lxq.dataprocessor.ReasonDataBank;
import com.jnu.lxq.fragment_3.HomeFragment;
import com.jnu.lxq.fragment_3.PayFragment;
import com.jnu.lxq.fragment_3.ReceiveFragment;

import java.util.ArrayList;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {
    private PayDataBank paydata;
    private PayFragment.PayAdapter adapter;
    private ReasonDataBank reasonDataBank;

    private static final int REQUEST_CODE_ADD_SUCCESS = 201;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reasonDataBank = new ReasonDataBank(this);
        reasonDataBank.Load();
        if(reasonDataBank.getReasonList().size() == 0){
              reasonDataBank.getReasonList().add("金榜题名");
              reasonDataBank.getReasonList().add("新造华堂");
              reasonDataBank.getReasonList().add("结婚大喜");
              reasonDataBank.Save();
        }
//        ArrayList<String> list= reasonDataBank.getReasonList();
//        list = new ArrayList<String>();
        initview();
    }

    private void initview() {
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new PayFragment());
        datas.add(new HomeFragment());
        datas.add(new ReceiveFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("随礼");
        titles.add("主页");
        titles.add("收礼");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_header);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_content);

        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(1).select();
    }


    public static class MyPageAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> datas;
        ArrayList<String> titles;


        public MyPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void setData(ArrayList<Fragment> datas) {
            this.datas = datas;
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return datas == null ? null : datas.get(position);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles == null ? null : titles.get(position);
        }
    }




}
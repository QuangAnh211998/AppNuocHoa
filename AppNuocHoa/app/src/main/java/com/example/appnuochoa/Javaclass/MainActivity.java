package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.SectionsPagerAdapter;
import com.example.appnuochoa.model.Giohangmua;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewpager;

    ImageButton imggiohang;

    public static ArrayList<Giohangmua> listGiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(sectionsPagerAdapter);

        imggiohang = findViewById(R.id.btnGioHang);
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
            }
        });

        if (listGiohang != null){//nếu mạng có dữ liệu k tạo mảng mới

        }else {//nếu chưa có dữ liệu
            listGiohang = new ArrayList<>();// tạo mảng cấp phát dữ liệu
        }

        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        createAhBottom();

    }


    private void createAhBottom() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Trang chủ", R.drawable.ic_home_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Thông báo", R.drawable.ic_notifications_black_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Tài khoản", R.drawable.ic_account_circle_black_24dp);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorWhite));

        //mau khi chon tab
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        //mau khi chua chon tab
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBlack));

        // layout mặc định
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewpager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }
}

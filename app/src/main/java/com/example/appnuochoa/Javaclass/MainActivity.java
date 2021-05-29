package com.example.appnuochoa.Javaclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.appnuochoa.Fragment.AccountFragment;
import com.example.appnuochoa.Fragment.CategoryFragment;
import com.example.appnuochoa.Fragment.HomeFragment;
import com.example.appnuochoa.Fragment.QlyspFragment;
import com.example.appnuochoa.Fragment.ThongkeFragment;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.CheckConnection;
import com.example.appnuochoa.model.Giohangmua;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static BottomNavigationView bottomNavigation;
    public static ArrayList<Giohangmua> listGiohang;

    private static SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();

        bottomNavigation = findViewById(R.id.btn_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        int maloaitk = luutaikhoan.getInt("Maloaitk",0);
        if(maloaitk==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new QlyspFragment()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        if (listGiohang != null) {//nếu mạng có dữ liệu k tạo mảng mới
        } else {//nếu chưa có dữ liệu
            listGiohang = new ArrayList<>();// tạo mảng cấp phát dữ liệu
        }
        checkid();


    }
    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b){
        checkid();
    }

    public static void checkid() {
        int maloaitk = luutaikhoan.getInt("Maloaitk",0);
        if(maloaitk == 1){
            bottomNavigation.getMenu().findItem(R.id.nav_notifi).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.nav_home).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.nav_qlsp).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.nav_thongke).setVisible(true);
        }else {
            bottomNavigation.getMenu().findItem(R.id.nav_notifi).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.nav_home).setVisible(true);
            bottomNavigation.getMenu().findItem(R.id.nav_qlsp).setVisible(false);
            bottomNavigation.getMenu().findItem(R.id.nav_thongke).setVisible(false);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_notifi:
                    fragment = new CategoryFragment();
                    break;
                case R.id.nav_qlsp:
                    fragment = new QlyspFragment();
                    break;
                case R.id.nav_thongke:
                    fragment = new ThongkeFragment();
                    break;
                case R.id.nav_account:
                    fragment = new AccountFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }
    };

}

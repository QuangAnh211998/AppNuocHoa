package com.example.appnuochoa.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnuochoa.Javaclass.Dangky;
import com.example.appnuochoa.Javaclass.Dangnhap;
import com.example.appnuochoa.Javaclass.MainActivity;
import com.example.appnuochoa.Admin.Quanlydonhang;
import com.example.appnuochoa.Javaclass.QLdonhangKH;
import com.example.appnuochoa.Javaclass.SanphamDamua;
import com.example.appnuochoa.Javaclass.Sodiachi;
import com.example.appnuochoa.Javaclass.ThietlapTaikhoan;
import com.example.appnuochoa.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class AccountFragment extends Fragment {
    private TextView tvhoten, tvsdt, tvemail;
    private Button btndangnhap, btndangky, btndangxuat;
    private LinearLayout tvsodiachi, tvspdamua, tvchovanchuyen, tvdanggiao;
    private LinearLayout tvqldonhang, tvdahuy, tvthietlaptk, tvdagiao;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvhoten = view.findViewById(R.id.tvhoten);
        tvsdt = view.findViewById(R.id.tvsdt);
        tvemail = view.findViewById(R.id.tvemail);
        tvspdamua = view.findViewById(R.id.spdamua);
        tvchovanchuyen = view.findViewById(R.id.dhchovanchuyen);
        tvdanggiao = view.findViewById(R.id.dhdanggiao);
        tvdagiao = view.findViewById(R.id.dhdagiao);
        tvdahuy = view.findViewById(R.id.dhdahuy);
        btndangnhap = view.findViewById(R.id.btndangnhap);
        btndangky = view.findViewById(R.id.btndangky);
        btndangxuat = view.findViewById(R.id.btndangxuat);
        tvsodiachi = view.findViewById(R.id.sodiachi);
        tvspdamua = view.findViewById(R.id.spdamua);
        tvqldonhang = view.findViewById(R.id.qldonhang);
        tvthietlaptk = view.findViewById(R.id.thietlaptk);

        EventBus.getDefault().register(this);

        initPreferences();
        CheckData();
        EventButton();
        EventTextview();
        return view;
    }

    private void EventTextview() {
        tvsodiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Sodiachi.class));
            }
        });
        tvspdamua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SanphamDamua.class));
            }
        });
        tvthietlaptk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThietlapTaikhoan.class));
            }
        });
        tvqldonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = luutaikhoan.getString("Email", "");
                int maloaitk = luutaikhoan.getInt("Maloaitk", 0);
                if (TextUtils.isEmpty(email)) {
                    startActivity(new Intent(getContext(), Dangnhap.class));
                }else {
                    if(maloaitk == 1){
                        startActivity(new Intent(getContext(), Quanlydonhang.class));
                    }else {
                        startActivity(new Intent(getContext(), QLdonhangKH.class));
                    }
                }
            }

        });
    }

    private void initPreferences() {
        luutaikhoan = getContext().getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }

    private void EventButton() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Dangnhap.class));
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Dangky.class));
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                CheckData();
                MainActivity.checkid();
            }
        });
    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b) {
        CheckData();
    }

    private void CheckData() {
        String email = luutaikhoan.getString("Email", "");
        int maloaitk = luutaikhoan.getInt("Maloaitk", 0);
        if (!TextUtils.isEmpty(email)) {
            btndangxuat.setVisibility(View.VISIBLE);
            btndangnhap.setVisibility(View.GONE);
            btndangky.setVisibility(View.GONE);
            tvhoten.setText(luutaikhoan.getString("Hoten", ""));
            tvsdt.setText(luutaikhoan.getString("Sdt", ""));
            tvemail.setText(luutaikhoan.getString("Email", ""));
            if (maloaitk == 2) {
                tvsodiachi.setVisibility(View.VISIBLE);
                tvspdamua.setVisibility(View.VISIBLE);
                tvthietlaptk.setVisibility(View.VISIBLE);
            } else if (maloaitk == 1) {
                tvsodiachi.setVisibility(View.GONE);
                tvspdamua.setVisibility(View.GONE);
                tvemail.setVisibility(View.GONE);
                tvthietlaptk.setVisibility(View.GONE);
            }

        } else {
            btndangnhap.setVisibility(View.VISIBLE);
            btndangky.setVisibility(View.VISIBLE);
            btndangxuat.setVisibility(View.GONE);
            tvemail.setVisibility(View.VISIBLE);
            tvhoten.setText("Họ tên");
            tvsdt.setText("Số điện thoại");
            tvemail.setText("Email");
            tvsodiachi.setVisibility(View.GONE);
            tvspdamua.setVisibility(View.GONE);
            tvthietlaptk.setVisibility(View.GONE);
        }
    }
}

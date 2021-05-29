package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    Toolbar toolbargiohang;
    ListView lvgiohang;
    TextView tvthongbao;
    static TextView tvtongtien;
    Button btnthanhtoan, btnmuatiep;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        ActionToobar();
        CheckData();
        EventUntil();

    }

    private static void EventUntil() {
        long tongtien = 0;
        for (int i =0; i<MainActivity.listGiohang.size(); i++){
            tongtien += MainActivity.listGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {

    }

    private void ActionToobar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvgiohang = findViewById(R.id.lvgiohang);
        tvthongbao = findViewById(R.id.tvthongbao);
        tvtongtien = findViewById(R.id.tvtongtien);
        btnthanhtoan = findViewById(R.id.btn_thanhtoan);
        btnmuatiep = findViewById(R.id.btn_muatiep);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(GioHang.this, MainActivity.listGiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}

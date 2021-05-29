package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Giohangmua;
import com.example.appnuochoa.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChitietSanpham extends AppCompatActivity {

    Toolbar toolbarctsp;
    ImageView imageViewctsp;
    TextView txtten,txtloai,txtxuatxu,txthuong,txtgia,txtmota;
    Spinner spinner;
    Button btn_themgiohang;

    int id = 0;
    String tenspct = "";
    String loaispct = "";
    String xuatxu = "";
    String hinhanhspct = "";
    String huongsp = "";
    int giaspct = 0;
    int soluong = 0;
    String mota = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);
        Anhxa();
        ActionToolbar();
        GetChitietSanpham();
        EventSpinner();
        EventButtonGH();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButtonGH() {
        btn_themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void EventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetChitietSanpham() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        id = sanpham.getId();
        tenspct = sanpham.getTensp();
        loaispct = sanpham.getLoaisp();
        xuatxu = sanpham.getXuatxu();
        hinhanhspct = sanpham.getHinhanh();
        huongsp = sanpham.getHuongsp();
        giaspct = sanpham.getGiaban();
        soluong = sanpham.getSoluong();
        mota = sanpham.getMota();

        txtten.setMaxLines(2);
        txtten.setEllipsize(TextUtils.TruncateAt.END);
        txtten.setText(tenspct);
        txtloai.setText("Nước hoa: "+loaispct);
        txtxuatxu.setText("Made in "+xuatxu);
        txthuong.setText("Nhóm hương: " +huongsp);
        txtmota.setText(mota);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+ decimalFormat.format(giaspct)+" Đ");
        Picasso.get().load(hinhanhspct).into(imageViewctsp);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarctsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set hình <-
        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarctsp =(Toolbar) findViewById(R.id.toolbarctsp);
        imageViewctsp = (ImageView)findViewById(R.id.imganhchtsp);
        txtten = (TextView)findViewById(R.id.tenctsp);
        txtloai = (TextView)findViewById(R.id.loaisp);
        txtxuatxu = (TextView)findViewById(R.id.xuatxu);
        txthuong = (TextView)findViewById(R.id.huong);
        txtgia = (TextView)findViewById(R.id.giactsp);
        txtmota = (TextView)findViewById(R.id.motasp);
        spinner = (Spinner)findViewById(R.id.spinerctsp);
        btn_themgiohang = (Button)findViewById(R.id.themgiohang);

    }
}

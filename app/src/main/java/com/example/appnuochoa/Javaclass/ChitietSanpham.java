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

    private Toolbar toolbarctsp;
    private ImageView imageViewctsp;
    private TextView txtten,txtloai,txtxuatxu,txthuong,txtgia,txtmota;
    private Spinner spinner;
    private Button btn_themgiohang;

    int id = 0;
    String tenspct = "";
    String loaispct = "";
    String xuatxu = "";
    String hinhanhspct = "";
    String huongsp = "";
    int giaspct = 0;
    public  static int soluong = 0;
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
                if(MainActivity.listGiohang.size() >0) {
                    int slmoi = Integer.parseInt(spinner.getSelectedItem().toString());//lấy số lượng spinner
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.listGiohang.size(); i++) {//kiểm tra giá trị trong mảng
                        if (MainActivity.listGiohang.get(i).getIdgh() == id) {//nếu cùng id
                            //cập nhật lại số lượng
                            MainActivity.listGiohang.get(i).setSoluongsp(MainActivity.listGiohang.get(i).getSoluongsp() + slmoi);
                            if (MainActivity.listGiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.listGiohang.get(i).setSoluongsp(10);//so lượng >10 set lại = 10
                            }
                            MainActivity.listGiohang.get(i).setGiasp(giaspct * MainActivity.listGiohang.get(i).getSoluongsp());
                            exists = true;// trùng id cập nhật lại dữ liệu
                        }
                    }
                    if(exists == false){//nếu k trùng id tạo dữ liệu mới
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamua = soluong * giaspct;
                        MainActivity.listGiohang.add(new Giohangmua(id,tenspct,Giamua,hinhanhspct,soluong));
                    }
                }else {//nếu chưa có dữ liệu sẽ đưa dữ liệu mới vào
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());// lấy số lượng từ spinner
                    long Giamua = soluong * giaspct;
                    MainActivity.listGiohang.add(new Giohangmua(id,tenspct,Giamua,hinhanhspct,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void EventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
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
        soluong = sanpham.getSoluong();
        giaspct = sanpham.getGiaban();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set hình
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

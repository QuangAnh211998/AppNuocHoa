package com.example.appnuochoa.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Fragment.QlyspFragment;
import com.example.appnuochoa.Fragment.ThongkeFragment;
import com.example.appnuochoa.Javaclass.MainActivity;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CapnhatSanpham extends AppCompatActivity {

    private EditText edttensp, edtloaisp, edtxuatxu, edthuong, edtsoluong, edtgiaban;
    private ImageButton imgbtnloai,imgbtnxuatxu;
    private Button btncapnhat;
    private Toolbar toolbar;
    private int idsp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhat_sanpham);

        Anhxa();
        ActionToolbar();
        GetSanpham();
        EventImgButton();

        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSanpham();
            }
        });

    }
    private void EventImgButton(){
        imgbtnloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemloai();
            }
        });
        imgbtnxuatxu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemxuatxu();
            }
        });
    }

    private void UpdateSanpham() {
        String tensp = edttensp.getText().toString();
        String loaisp = edtloaisp.getText().toString();
        String xuatxu = edtxuatxu.getText().toString();
        String huong = edthuong.getText().toString();
//        String soluong = edtsoluong.getText().toString();

        if(tensp.length()<3||loaisp.length()<2||xuatxu.length()<2||huong.equals("")||edtsoluong.length()<1|| edtgiaban.length()<5){
            Toast.makeText(getApplicationContext(), "Vùi lòng nhập đúng, đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            int soluong = Integer.parseInt(edtsoluong.getText().toString().trim());
            int giaban = Integer.parseInt(edtgiaban.getText().toString().trim());

            final String query = "UPDATE nuochoa SET ten = '" + tensp + "', loai = '" + loaisp + "', xuatxu = '" + xuatxu + "', " +
                    "huong = '" + huong + "' , soluong = '" + soluong + "', giaban = '" + giaban + "' WHERE id = '" + idsp + "' ";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("truyvan", query);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void GetSanpham() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtin");
        idsp = sanpham.getId();
        String tensp = sanpham.getTensp();
        String loaisp = sanpham.getLoaisp();
        String xuatxu = sanpham.getXuatxu();
        String hinhanhsp = sanpham.getHinhanh();
        String huongsp = sanpham.getHuongsp();
        int soluong = sanpham.getSoluong();
        int giasp = sanpham.getGiaban();
        String mota = sanpham.getMota();

        edttensp.setText(tensp);
        edtloaisp.setText(loaisp);
        edtxuatxu.setText(xuatxu);
        edthuong.setText(huongsp);
        edtsoluong.setText(String.valueOf(soluong));
        edtgiaban.setText(String.valueOf(giasp));

    }

    private void MenuItemloai() {
        PopupMenu popupMenuloai = new PopupMenu(this,imgbtnloai);
        popupMenuloai.getMenuInflater().inflate(R.menu.menuloai,popupMenuloai.getMenu());
        popupMenuloai.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.itemnam:
                        edtloaisp.setText("Nam");
                        break;
                    case R.id.itemnu:
                        edtloaisp.setText("Nữ");
                        break;
                }
                return false;
            }
        });
        popupMenuloai.show();

    }
    private void MenuItemxuatxu() {
        PopupMenu popupMenuloai = new PopupMenu(this,imgbtnxuatxu);
        popupMenuloai.getMenuInflater().inflate(R.menu.menuxuatxu,popupMenuloai.getMenu());
        popupMenuloai.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.france:
                        edtloaisp.setText("France");
                        break;
                    case R.id.francespain:
                        edtloaisp.setText("France/Spain");
                        break;
                    case R.id.franceuk:
                        edtloaisp.setText("France/Uk");
                        break;
                    case R.id.italy:
                        edtloaisp.setText("Italy");
                        break;
                    case R.id.uk:
                        edtloaisp.setText("UK");
                        break;
                    case R.id.usa:
                        edtloaisp.setText("USA");
                        break;
                    case R.id.spain:
                        edtloaisp.setText("Spain");
                        break;
                }
                return false;
            }
        });
        popupMenuloai.show();

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set hình
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {

        edttensp = findViewById(R.id.edttensp);
        edtloaisp = findViewById(R.id.edtloai);
        edtxuatxu = findViewById(R.id.edtxuatxu);
        edthuong = findViewById(R.id.edthuong);
        edtsoluong = findViewById(R.id.edtsoluong);
        edtgiaban = findViewById(R.id.edtgiaban);
        imgbtnloai = findViewById(R.id.imgbtnloai);
        imgbtnxuatxu = findViewById(R.id.imgbtnxuatxy);
        btncapnhat = findViewById(R.id.btncapnhatsp);
        toolbar = findViewById(R.id.toolbarsuasp);
    }
}

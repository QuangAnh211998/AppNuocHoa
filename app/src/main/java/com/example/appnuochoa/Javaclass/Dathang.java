package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.DathangAdapter;
import com.example.appnuochoa.adapter.GiohangAdapter;
import com.example.appnuochoa.model.Diachi;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Dathang extends AppCompatActivity {

    private ListView lvdathang;
    private TextView tvtenkh, tvsdtkh, tvdiachi, tvthaydoi,  tvphiship;
    private TextView tvtamtinh, tvthanhtien;
    private Button btnxacnhan;
    private Toolbar toolbar;

    String hoten= "";
    String sdt = "";
    String diachikh="";
    long tongtien = 0;
    long thanhtien = 0;
    long phiship = 0;

    DathangAdapter dathangAdapter;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);

        Anhxa();
        ActionToolbar();
        EventData();
        GetDiachi();
        EventClick();

    }

    private void GetDiachi() {
        Intent intent = getIntent();
         hoten = intent.getStringExtra("hoten");
         sdt = intent.getStringExtra("sdt");
         diachikh = intent.getStringExtra("diachi");

        tvtenkh.setText(hoten);
        tvsdtkh.setText(sdt);
        tvdiachi.setText(diachikh);
    }

    private void EventClick() {
        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        final int makh = luutaikhoan.getInt("Id", 0);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvtenkh.length()<1||tvsdtkh.length()<1||tvdiachi.length()<1){
                    Toast.makeText(Dathang.this, "V??i l??ng ch???n ?????a ch??? nh???n h??ng!", Toast.LENGTH_SHORT).show();
                }else if(tvtamtinh.length()<1||tvphiship.length()<1||tvthanhtien.length()<1) {
                    Toast.makeText(Dathang.this, "Kh??ng c?? s???n ph???m ????? ?????t h??ng!", Toast.LENGTH_SHORT).show();
                }else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkdonhang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String madonhang) {
                                    if (Integer.parseInt(madonhang) > 0) {
                                        StringRequest request = new StringRequest(Request.Method.POST, Sever.linkchitietdonhang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.contains("thanhcong")) {
                                                            MainActivity.listGiohang.clear();
                                                            Toast.makeText(Dathang.this, "?????t h??ng th??nh c??ng!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Dathang.this, MainActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(Dathang.this, "?????t h??ng th???t b???i!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0; i < MainActivity.listGiohang.size(); i++) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("madonhang", madonhang);
                                                        jsonObject.put("masp", MainActivity.listGiohang.get(i).getIdgh());
                                                        jsonObject.put("tensp", MainActivity.listGiohang.get(i).getTensp());
                                                        jsonObject.put("soluong", MainActivity.listGiohang.get(i).getSoluongsp());
                                                        jsonObject.put("thanhtien", MainActivity.listGiohang.get(i).getGiasp());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);

                                                }
                                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                                hashMap.put("json", jsonArray.toString());
                                                return hashMap;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        requestQueue.add(request);
                                    }
                                    Log.d("madonhang", madonhang);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("makh", String.valueOf(makh));
                            params.put("hoten", hoten);
                            params.put("sdt", sdt);
                            params.put("diachi", diachikh);
                            params.put("tongtien", String.valueOf(thanhtien));
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

        tvthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sodiachi.class));
            }
        });
    }

    public void EventData() {
        for (int i =0; i<MainActivity.listGiohang.size(); i++){
            tongtien += MainActivity.listGiohang.get(i).getGiasp();
            if (MainActivity.listGiohang.size()>1 || MainActivity.listGiohang.get(i).getSoluongsp()>1){
                phiship = 0;
                thanhtien = tongtien + phiship;
            }else {
                phiship = 30000;
                thanhtien = tongtien + phiship;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtamtinh.setText(decimalFormat.format(tongtien) + " ??");
        tvphiship.setText(decimalFormat.format(phiship)+ " ??");
        tvthanhtien.setText(decimalFormat.format(thanhtien)+ " ??");
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set h??nh <-
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GioHang.class));
            }
        });
    }

    private void Anhxa() {
        tvtenkh = findViewById(R.id.tvtenkh);
        tvsdtkh = findViewById(R.id.tvsdtkh);
        tvdiachi = findViewById(R.id.tvdiachikh);
        tvthaydoi = findViewById(R.id.tvthaydoi);
        tvtamtinh = findViewById(R.id.tamtinh);
        tvphiship = findViewById(R.id.phiship);
        tvthanhtien = findViewById(R.id.thanhtien);
        btnxacnhan = findViewById(R.id.xacnhan);
        lvdathang = findViewById(R.id.listdathang);
        toolbar = findViewById(R.id.toolbardathang);

        dathangAdapter = new DathangAdapter(Dathang.this, MainActivity.listGiohang);
        lvdathang.setAdapter(dathangAdapter);
    }

}

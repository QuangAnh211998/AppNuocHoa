package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.appnuochoa.Fragment.HomeFragment;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.SPdamuaAdapter;
import com.example.appnuochoa.model.Damua;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SanphamDamua extends AppCompatActivity {

    private ListView lvdamua;
    private TextView tvthongbao;
    private Toolbar toolbar;
    private ArrayList<Damua> listdamua;
    SPdamuaAdapter spdamuaAdapter;
    String hoanthanh = "hoàn thành";

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_damua);

        tvthongbao = findViewById(R.id.txtthongbao);
        lvdamua = findViewById(R.id.listspdamua);
        toolbar = findViewById(R.id.toolbardamua);

        listdamua = new ArrayList<>();
        spdamuaAdapter = new SPdamuaAdapter(getApplicationContext(),listdamua);
        lvdamua.setAdapter(spdamuaAdapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GetSPdamua();
    }
    private void GetSPdamua() {

        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        int makh = luutaikhoan.getInt("Id", 0);

        final String query ="SELECT dh.madonhang, dh.makh,dh.ngaydathang,dh.trangthai,ctdh.masp,ctdh.tensp,ctdh.soluong," +
                "nh.giaban,nh.hinhanh FROM donhang dh INNER JOIN chitietdonhang ctdh ON dh.madonhang = ctdh.madonhang " +
                "INNER JOIN nuochoa nh ON ctdh.masp = nh.id WHERE dh.makh ='"+ makh +"' AND dh.trangthai = '"+hoanthanh+"'";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkspdamua,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        tvthongbao.setVisibility(View.VISIBLE);
                    } else {
                        tvthongbao.setVisibility(View.GONE);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int Id = jsonObject.getInt("Id");
                                int Makh = jsonObject.getInt("Makh");
                                String Ngaymua = jsonObject.getString("Ngaydh");
                                String Trangthai = jsonObject.getString("Trangthai");
                                int Masp = jsonObject.getInt("Masp");
                                String Tensp = jsonObject.getString("Tensp");
                                int Soluong = jsonObject.getInt("Soluong");
                                int Giaban = jsonObject.getInt("Giaban");
                                String Hinhanh = jsonObject.getString("Hinhanh");
                                listdamua.add(new Damua(Id, Makh,Ngaymua,Trangthai,Masp,Tensp,Soluong,Giaban,Hinhanh));
                                spdamuaAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SanphamDamua.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("spdamua", query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

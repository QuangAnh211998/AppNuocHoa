package com.example.appnuochoa.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.QLdonhangKH;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.QLDonhangAdapter;
import com.example.appnuochoa.model.Donhang;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quanlydonhang extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvdonhang;
    static ArrayList<Donhang> listdonhang;
    QLDonhangAdapter donhangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlydonhang);

        toolbar = findViewById(R.id.toolbarqldh);
        lvdonhang = findViewById(R.id.lvdonhang);

        getDonhang();
        ActionToobar();
    }

    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDonhang(){
        listdonhang = new ArrayList<>();
        donhangAdapter = new QLDonhangAdapter(Quanlydonhang.this,listdonhang);
        lvdonhang.setAdapter(donhangAdapter);

        final String query = "SELECT * FROM donhang ORDER BY ngaydathang DESC";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkgetdonhang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response.equals("null")) {
                            } else {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        int id = jsonObject.getInt("Madh");
                                        String hoten = jsonObject.getString("Hoten");
                                        String sdt = jsonObject.getString("Sdt");
                                        String diachi = jsonObject.getString("Diachi");
                                        String ngaydat = jsonObject.getString("Ngaydat");
                                        int tongtien = jsonObject.getInt("Tongtien");
                                        String trangthai = jsonObject.getString("Trangthai");
                                        listdonhang.add(new Donhang(id, hoten, sdt, diachi, ngaydat, tongtien, trangthai));
                                        donhangAdapter.notifyDataSetChanged();

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
                Toast.makeText(Quanlydonhang.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("donhang", query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

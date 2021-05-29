package com.example.appnuochoa.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.GioHang;
import com.example.appnuochoa.Javaclass.Timkiem;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.Sanpham_adapter;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadData extends AppCompatActivity {

    private LinearLayout timkiem;
    private ImageButton imageButton;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private ArrayList<Sanpham> listsanpham;
    Sanpham_adapter spadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);

        recyclerView = findViewById(R.id.recyclerview);
        imageButton = findViewById(R.id.btnGioHang);
        timkiem = findViewById(R.id.seach);
        toolbar = findViewById(R.id.toobar);

        listsanpham = new ArrayList<>();
        spadapter = new Sanpham_adapter(getApplicationContext(),listsanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        recyclerView.setAdapter(spadapter);

        EventClick();

        Intent intent = getIntent();
        String check = intent.getStringExtra("data");
        if( check.equals("nam")){
            GetDaTa(Sever.linknuochoanam);
        }else if( check.equals("nu")){
            GetDaTa(Sever.linknuochoanu);
        }else if( check.equals("muc1")){
            GetDaTa(Sever.linktimkiemmuc1);
        }else if( check.equals("muc2")){
            GetDaTa(Sever.linktimkiemmuc2);
        }else if( check.equals("muc3")){
            GetDaTa(Sever.linktimkiemmuc3);
        }else if( check.equals("muc4")){
            GetDaTa(Sever.linktimkiemmuc4);
        }
    }

    private void GetDaTa(String link) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("getDataSuccess", response.length() + "");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int Id = jsonObject.getInt("Id");
                        String Ten = jsonObject.getString("Ten");
                        String Loai = jsonObject.getString("Loai");
                        String Xuatxu = jsonObject.getString("Xuatxu");
                        String Hinhanh = jsonObject.getString("Hinhanh");
                        String Huong = jsonObject.getString("Huong");
                        Integer Soluong = jsonObject.getInt("Soluong");
                        Integer Giaban = jsonObject.getInt("Giaban");
                        String Mota = jsonObject.getString("Mota");

                        listsanpham.add(new Sanpham(Id,Ten,Loai,Xuatxu,Hinhanh,Huong,Soluong,Giaban,Mota));
                        spadapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void EventClick() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GioHang.class));
            }
        });
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Timkiem.class));
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

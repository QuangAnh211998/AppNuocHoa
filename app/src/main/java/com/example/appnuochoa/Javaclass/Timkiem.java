package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.Sanpham_adapter;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Timkiem extends AppCompatActivity {

    private SearchView searchView;
    private ImageButton  btnback;
    private RecyclerView recyclerView;
    private ArrayList<Sanpham> listtimkiem;
    private Sanpham_adapter timkiemAdapter;


//    String[] listname = {"Chanel 2019", "VerSace", "Dior"};
//    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem);

        recyclerView = findViewById(R.id.recytimkiem);
        searchView = findViewById(R.id.seachview);
        btnback = findViewById(R.id.btnback);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timkiem();
                listtimkiem = new ArrayList<>();
                timkiemAdapter = new Sanpham_adapter(getApplicationContext(),listtimkiem);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
                recyclerView.setAdapter(timkiemAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Timkiem() {

        String tentk = searchView.getQuery().toString().trim();
        final String query = "SELECT * FROM nuochoa WHERE  ten LIKE '%"+tentk+"%' OR loai LIKE '%"+tentk+"%' " +
                "OR xuatxu LIKE '%"+tentk+"%'  OR huong LIKE '%"+tentk+"%'";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linktimkiem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        Toast.makeText(Timkiem.this, "Sản phẩm bạn cần tìm không có!",Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int Id = jsonObject.getInt("Id");
                                String Ten = jsonObject.getString("Ten");
                                String Loai = jsonObject.getString("Loai");
                                String Xuatxu = jsonObject.getString("Xuatxu");
                                String Hinhanh = jsonObject.getString("Hinhanh");
                                String Huong = jsonObject.getString("Huong");
                                Integer Soluong = jsonObject.getInt("Soluong");
                                Integer Giaban = jsonObject.getInt("Giaban");
                                String Mota = jsonObject.getString("Mota");

                                listtimkiem.add(new Sanpham(Id,Ten,Loai,Xuatxu,Hinhanh,Huong,Soluong,Giaban,Mota));
                                timkiemAdapter.notifyDataSetChanged();
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
                Toast.makeText(Timkiem.this, "Error",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("timkiem", query);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }

}


package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.SodiachiAdapter;
import com.example.appnuochoa.model.Diachi;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sodiachi extends AppCompatActivity {

    private ListView lvdiachi;
    private Button btnthemdiachi;
    private Toolbar toolbar;
    ArrayList<Diachi> listdiachi;
    SodiachiAdapter sodiachiAdapter;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodiachi);

        lvdiachi = findViewById(R.id.listdiachi);
        btnthemdiachi = findViewById(R.id.themdiachi);
        toolbar = findViewById(R.id.toolbarsodiachi);
        listdiachi = new ArrayList<>();
        sodiachiAdapter = new SodiachiAdapter(Sodiachi.this, listdiachi);
        lvdiachi.setAdapter(sodiachiAdapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GetDiachi();
        EventClick();
    }

    public void DeleteDiachi(int iddc){
        final String query = "DELETE FROM diachigiaohang WHERE id ='"+iddc+"'";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Toast.makeText(Sodiachi.this,"Xóa thành công!", Toast.LENGTH_LONG).show();
                            GetDiachi();
                        }else {
                            Toast.makeText(Sodiachi.this,"lỗi xóa!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sodiachi.this,"Error!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("truyvan",query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void EventClick() {
        btnthemdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Themdiachi.class));
            }
        });
    }


    private void GetDiachi() {

        listdiachi.clear();

        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
        int makh = luutaikhoan.getInt("Id", 0);

        final String query = "SELECT * FROM diachigiaohang WHERE  makh ='" + makh + "'";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkdiachi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        Toast.makeText(Sodiachi.this, "Bạn chưa có địa chỉ nào? Vui lòng thêm địa chỉ!", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int Id = jsonObject.getInt("Id");
                                int Makh = jsonObject.getInt("Makh");
                                String Hoten = jsonObject.getString("Hoten");
                                String SDT = jsonObject.getString("Sdt");
                                String Diachi = jsonObject.getString("Diachi");

                                listdiachi.add(new Diachi(Id, Makh, Hoten, SDT, Diachi));
                                sodiachiAdapter.notifyDataSetChanged();
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
                Toast.makeText(Sodiachi.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("diachi", query);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

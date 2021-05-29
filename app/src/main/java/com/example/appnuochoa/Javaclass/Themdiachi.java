package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sever;

import java.util.HashMap;
import java.util.Map;

public class Themdiachi extends AppCompatActivity {

    private EditText edthoten, edtsdt, edtdiachi;
    private Button btnthem;
    private Toolbar toolbar;
    private ProgressBar loading;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdiachi);

        edthoten = findViewById(R.id.edthoten);
        edtsdt = findViewById(R.id.edtsdt);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnthem = findViewById(R.id.btnthem);
        toolbar = findViewById(R.id.toolbarthemdiachi);
        loading = findViewById(R.id.loading);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Themdiachi();
            }
        });
        EventToobar();
        initPreferences();
    }
    private void initPreferences() {
        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }

    public void Themdiachi() {
        loading.setVisibility(View.VISIBLE);

        final int makh = luutaikhoan.getInt("Id",0);

        if (edthoten.length() < 6) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ họ tên", Toast.LENGTH_SHORT).show();
        } else if (edtsdt.length() > 10 || edtsdt.length() <= 9) {
            Toast.makeText(this, "Bạn chưa nhập hoặc nhập sai sdt", Toast.LENGTH_SHORT).show();
        } else if (edtdiachi.length() < 20) {
            Toast.makeText(this, "Địa chỉ cần ghi đầy đủ", Toast.LENGTH_SHORT).show();
        } else {
            final String hoten = this.edthoten.getText().toString();
            final String sdt = this.edtsdt.getText().toString().trim();
            final String diachi = this.edtdiachi.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, Sever.linkthemdiachi,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("themthanhcong")) {
                                Toast.makeText(Themdiachi.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Sodiachi.class));
                                loading.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.setVisibility(View.GONE);
                            Toast.makeText(Themdiachi.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("makh",String.valueOf(makh));
                    params.put("hoten", hoten);
                    params.put("sdt", sdt);
                    params.put("diachi", diachi);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }

    private void EventToobar() {
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

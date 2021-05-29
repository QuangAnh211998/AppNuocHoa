package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.example.appnuochoa.Fragment.AccountFragment;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class Dangnhap extends AppCompatActivity {

    private EditText edtname, edtpass;
    private Button dangnhap;
    private TextView tvdangky;
    private ProgressBar loading;
    private String taikhoan, matkhau;
    private ImageButton back;

    public SharedPreferences luutaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        EventBus.getDefault().register(this);
        edtname = findViewById(R.id.name);
        edtpass = findViewById(R.id.pass);
        dangnhap = findViewById(R.id.dangnhap);
        tvdangky = findViewById(R.id.tvdangky);
        loading = findViewById(R.id.loading);
        back = findViewById(R.id.btnback);

        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtname.getText().toString().trim();
                matkhau = edtpass.getText().toString().trim();
                if (!taikhoan.isEmpty() || !matkhau.isEmpty()) {
                    Login();
                } else {
                    edtname.setError("Vui lòng nhập tài khoản");
                    edtpass.setError("Vui lòng nhập mật khẩu");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EventText();
    }

    public void Login() {
        loading.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, Sever.linkdangnhap,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("id");
                                    String sdt = object.getString("sdt").trim();
                                    String email = object.getString("email").trim();
                                    String hoten = object.getString("hoten").trim();
                                    String gioitinh = object.getString("gioitinh").trim();
                                    int maloaitk = object.getInt("maloaitk");
                                    String matkhau = object.getString("matkhau").trim();
                                    Toast.makeText(Dangnhap.this, "Bạn đăng nhập với tài khoản:\n" + email, Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);

                                    SharedPreferences.Editor editor = luutaikhoan.edit();
                                    editor.putInt("Id", id);
                                    editor.putString("Sdt", sdt);
                                    editor.putString("Email", email);
                                    editor.putString("Hoten", hoten);
                                    editor.putString("Gioitinh", gioitinh);
                                    editor.putInt("Maloaitk", maloaitk);
                                    editor.putString("Matkhau", matkhau);
                                    editor.commit();
                                    //MainActivity.listTaikhoan.add(new Taikhoan(sdt, email, hoten, matkhau));
                                }
                                //khi đăng nhập gửi dư liệu sang cho fragment luôn
                                EventBus.getDefault().post(true,"loginSuccess");
                                finish();
                            } else {
                                loading.setVisibility(View.GONE);
                                Toast.makeText(Dangnhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(Dangnhap.this, "Erorr", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tentaikhoan", taikhoan);
                params.put("matkhau", matkhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void EventText() {
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dangky.class));
            }
        });
    }
}





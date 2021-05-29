package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Fragment.AccountFragment;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sever;
import com.google.android.material.textfield.TextInputLayout;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class ThietlapTaikhoan extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edttenkh, edtsdtkh, edtemailkh, edtmkcu, edtmkmoi, edtnlmk;
    private TextInputLayout inputLayoutmkcu, inputLayoutmkmoi, inputLayoutnlmk;
    private RadioButton radiobtnnam, radiobtnnu;
    private CheckBox checkBox;
    private Button btnluutk;
    private String gioitinh,mkcu;
    private int idkh;

    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietlap_taikhoan);


        Anhxa();
        EventToolbar();
        initPreferences();
        GetTaiKhoan();


        btnluutk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radiobtnnam.isChecked()) {
                    gioitinh = "Nam";
                }
                if (radiobtnnu.isChecked()) {
                    gioitinh = "Nữ";
                }
                if(checkBox.isChecked()){
                    UpdateTKMK();
                }else {
                    UpdateTaiKhoan();
                }
            }
        });
    }

    private void UpdateTaiKhoan() {

            final String hotenkh = edttenkh.getText().toString();
            if(hotenkh.length()<7){
                Toast.makeText(getApplicationContext(),"Tên phải có ít nhất từ 2 từ!", Toast.LENGTH_SHORT).show();
            }else {
                final String query = "UPDATE user SET hoten = '" + hotenkh + "', gioitinh = '" + gioitinh + "'WHERE id ='" + idkh + "'";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    editor.putString("Hoten", hotenkh);
                                    editor.putString("Gioitinh", gioitinh);
                                    editor.commit();

                                    EventBus.getDefault().post(true, "loginSuccess");
                                    Toast.makeText(ThietlapTaikhoan.this, " ok", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ThietlapTaikhoan.this, "lỗi!", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ThietlapTaikhoan.this, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
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
    private void UpdateTKMK(){
        String matkhaucu = edtmkcu.getText().toString().trim();
        int sosanh;
        sosanh = mkcu.compareTo(matkhaucu);
        if(sosanh<0 || sosanh >0){
            Toast.makeText(getApplicationContext(),"Mật khẩu cũ không đúng!",Toast.LENGTH_SHORT).show();
        }else if(edtmkmoi.length()<6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có từ 6 ký tự!", Toast.LENGTH_SHORT).show();
        }else if(edttenkh.length()<7){
            Toast.makeText(getApplicationContext(),"Tên phải có ít nhất từ 2 từ!", Toast.LENGTH_SHORT).show();
        }else {
            final String mkmoi = edtmkmoi.getText().toString().trim();
            String nlmkmoi = edtnlmk.getText().toString().trim();
            int sosanh1;
            sosanh1 = mkmoi.compareTo(nlmkmoi);
            if(sosanh1 <0 || sosanh1 > 0){
                Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
            }else {
                final String hotenkh = edttenkh.getText().toString();
                    final String query = "UPDATE user SET hoten = '" +hotenkh+ "', gioitinh = '" +gioitinh+ "'," +
                            " matkhau = '"+ mkmoi +"' WHERE id ='" + idkh + "'";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    editor.putString("Hoten", hotenkh);
                                    editor.putString("Gioitinh", gioitinh);
                                    editor.putString("Matkhau", mkmoi);
                                    editor.commit();

                                    EventBus.getDefault().post(true, "loginSuccess");
                                    Toast.makeText(ThietlapTaikhoan.this, " Thành công!", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ThietlapTaikhoan.this, "lỗi!", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ThietlapTaikhoan.this, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
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
    }

    private void GetTaiKhoan() {

        String email = luutaikhoan.getString("Email", "");
        gioitinh = luutaikhoan.getString("Gioitinh", "");
        if (!TextUtils.isEmpty(email)) {
            idkh = luutaikhoan.getInt("Id", 0);
            mkcu = luutaikhoan.getString("Matkhau","");
            String hoten = luutaikhoan.getString("Hoten", "");
            edttenkh.setText(hoten);
            edtsdtkh.setText(luutaikhoan.getString("Sdt", ""));
            edtemailkh.setText(luutaikhoan.getString("Email", ""));
            if (gioitinh.equals("Nam")) {
                radiobtnnam.setChecked(true);
            } else {
                radiobtnnu.setChecked(true);
            }
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        inputLayoutmkcu.setVisibility(View.VISIBLE);
                        inputLayoutmkmoi.setVisibility(View.VISIBLE);
                        inputLayoutnlmk.setVisibility(View.VISIBLE);
                    } else {
                        inputLayoutmkcu.setVisibility(View.GONE);
                        inputLayoutmkmoi.setVisibility(View.GONE);
                        inputLayoutnlmk.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void initPreferences() {
        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }


    private void EventToolbar() {
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
        toolbar = findViewById(R.id.toolbartltk);
        btnluutk = findViewById(R.id.btnluu);
        checkBox = findViewById(R.id.checkboxmk);
        radiobtnnam = findViewById(R.id.radiobtnnam);
        radiobtnnu = findViewById(R.id.radiobtnnu);
        edttenkh = findViewById(R.id.hotenkh);
        edtsdtkh = findViewById(R.id.sdtkh);
        edtemailkh = findViewById(R.id.emailkh);
        edtmkcu = findViewById(R.id.matkhaucu);
        edtmkmoi = findViewById(R.id.matkhaumoi);
        edtnlmk = findViewById(R.id.nlmatkhaumoi);
        inputLayoutmkcu = findViewById(R.id.inputlayoutmkcu);
        inputLayoutmkmoi = findViewById(R.id.inputlayoutmkmoi);
        inputLayoutnlmk = findViewById(R.id.inputlayoutnlmk);
    }
}

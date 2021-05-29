package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dangky extends AppCompatActivity {

    private EditText  edtmatkhau,edtnhaplaimk, edthoten, edtemail, edtsdt;
    private Button btndangky;
    private ProgressBar loading;
    private ImageButton back;
    private RadioButton radiobtnnam, radiobtnnu;
    String gioitinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        Anhxa();


        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radiobtnnam.isChecked()) {
                    gioitinh = "Nam";
                } if(radiobtnnu.isChecked()) {
                    gioitinh = "Nữ";
                }
                Register();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Register() {
        loading.setVisibility(View.VISIBLE);

        if ( edtsdt.length() <= 9) {
            loading.setVisibility(View.GONE);
            Toast.makeText(this, "Bạn chưa nhập hoặc nhập sai sdt!", Toast.LENGTH_SHORT).show();
        } else if (edthoten.length() < 6) {
            loading.setVisibility(View.GONE);
            Toast.makeText(this, "Bạn cần nhập đầy đủ họ tên!", Toast.LENGTH_SHORT).show();
        } else if (edtemail.equals("@gmail.com")) {
            loading.setVisibility(View.GONE);
            Toast.makeText(this, "Email không đúng!", Toast.LENGTH_SHORT).show();
        } else if (edtmatkhau.length() < 6) {
            loading.setVisibility(View.GONE);
            Toast.makeText(this, "Mật khẩu phải có từ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
        } else {
            final String sdt = this.edtsdt.getText().toString().trim();
            final String email = this.edtemail.getText().toString().trim();
            final String hoten = this.edthoten.getText().toString();
            final String matkhau = this.edtmatkhau.getText().toString().trim();
            final String nhaplaimk = this.edtnhaplaimk.getText().toString().trim();
            int sosanh;
            sosanh = matkhau.compareTo(nhaplaimk);
            if (sosanh < 0 || sosanh > 0) {
                loading.setVisibility(View.GONE);
                Toast.makeText(Dangky.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            } else {

                StringRequest request = new StringRequest(Request.Method.POST, Sever.linkdangky,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("register")) {
                                    Toast.makeText(Dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Dangnhap.class));
                                    loading.setVisibility(View.GONE);
                                } else if (response.trim().contains("tontaisdt")) {
                                    loading.setVisibility(View.GONE);
                                    Toast.makeText(Dangky.this, "SDT đã được sử dụng!", Toast.LENGTH_SHORT).show();

                                } else if (response.trim().contains("tontaiemail")) {
                                    loading.setVisibility(View.GONE);
                                    Toast.makeText(Dangky.this, "Email đã được sử dụng!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            loading.setVisibility(View.GONE);
                                Toast.makeText(Dangky.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("sdt", sdt);
                        params.put("email", email);
                        params.put("hoten", hoten);
                        params.put("gioitinh", gioitinh);
                        params.put("matkhau", matkhau);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(request);
            }
        }
    }

    private void Anhxa() {
        loading = findViewById(R.id.loading);
        btndangky = findViewById(R.id.btndangky);
        edtmatkhau = findViewById(R.id.edt_matkhau);
        edtnhaplaimk = findViewById(R.id.edt_nhaplaimk);
        edthoten = findViewById(R.id.edt_hoten);
        edtemail = findViewById(R.id.edt_email);
        edtsdt = findViewById(R.id.edt_sdt);
        back = findViewById(R.id.btnback);
        radiobtnnam = findViewById(R.id.radiobtnnam);
        radiobtnnu = findViewById(R.id.radiobtnnu);

    }
}

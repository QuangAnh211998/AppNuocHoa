package com.example.appnuochoa.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.MainActivity;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sever;

import java.util.HashMap;
import java.util.Map;

public class Themsanpham extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerloai, spinnerxuatxu;
    private EditText edttensp,edtloaisp,edtxuatxu,edthinhanh,edthuong,
            edtsoluong, edtgiaban, edtmota;
    private Button btnthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsanpham);

        Anhxa();
        ActionToolbar();
        EventSpinner();
        InsertSanpham();

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertSanpham();
            }
        });
    }

    private void InsertSanpham() {
        if(edttensp.length()<3||edthinhanh.equals("")||edthuong.equals("")||edtsoluong.equals("")||edtgiaban.length()<5||edtmota.equals("")){
            Toast.makeText(getApplicationContext(), "Vùi lòng nhập đúng, đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            final String tensp = edttensp.getText().toString();
            final String loaisp = edtloaisp.getText().toString().trim();
            final String xuatxu = edtxuatxu.getText().toString();
            final String hinhanh = edthinhanh.getText().toString().trim();
            final String huong = edthuong.getText().toString();
            final int soluong = Integer.parseInt(edtsoluong.getText().toString().trim());
            final int giaban = Integer.parseInt(edtgiaban.getText().toString().trim());
            final String mota = edtmota.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, Sever.linkthemsanpham,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("themthanhcong")) {
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else {
                                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ten", tensp);
                    params.put("loai", loaisp);
                    params.put("xuatxu", xuatxu);
                    params.put("hinhanh", hinhanh);
                    params.put("huong", huong);
                    params.put("soluong", String.valueOf(soluong));
                    params.put("giaban", String.valueOf(giaban));
                    params.put("mota", mota);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void EventSpinner() {

        String[] loaisp = new String[]{"Nam", "Nữ"};
        String[] xuatxu = new String[]{"France","Rrance/Spain","France/Uk","Spain","Italy","Uk","Use"};

        final ArrayAdapter<String> arrloai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, loaisp);
        spinnerloai.setAdapter(arrloai);
        spinnerloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtloaisp.setText(arrloai.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> arrxuatxu = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, xuatxu);
        spinnerxuatxu.setAdapter(arrxuatxu);
        spinnerxuatxu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtxuatxu.setText(arrxuatxu.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarthemsp);
        spinnerloai = findViewById(R.id.spinnerloai);
        spinnerxuatxu = findViewById(R.id.spinnerxuatxu);
        edttensp = findViewById(R.id.edttensp);
        edtloaisp = findViewById(R.id.edtloai);
        edtxuatxu = findViewById(R.id.edtxuatxu);
        edthinhanh = findViewById(R.id.edthinhanh);
        edthuong = findViewById(R.id.edthuong);
        edtsoluong = findViewById(R.id.edtsoluong);
        edtgiaban = findViewById(R.id.edtgiaban);
        edtmota = findViewById(R.id.edtmota);
        btnthem = findViewById(R.id.btnthemsp);
    }
}

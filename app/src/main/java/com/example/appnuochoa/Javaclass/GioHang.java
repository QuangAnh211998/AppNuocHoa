package com.example.appnuochoa.Javaclass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnuochoa.Fragment.HomeFragment;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.GiohangAdapter;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    private Toolbar toolbargiohang;
    private ListView lvgiohang;
    private TextView tvthongbao;
    static TextView tvtongtien;
    private Button btndathang, btnmuatiep;
    GiohangAdapter giohangAdapter;
    private SharedPreferences luutaikhoan;
    private SharedPreferences.Editor editor;

    static int soluongmua = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initPreferences();
        Anhxa();
        ActionToobar();
        CheckData();
        EventData();
        EventItemLisView();
        EventButton();

    }

    private void initPreferences() {
        luutaikhoan = getSharedPreferences("thongtintaikhoan", Context.MODE_PRIVATE);
        editor = luutaikhoan.edit();
    }

    private void EventButton() {
        btnmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = luutaikhoan.getString("Email","");
                if(MainActivity.listGiohang.size() > 0 ){
                    if(soluongmua <= ChitietSanpham.soluong) {
                        if (TextUtils.isEmpty(email)) {
                            startActivity(new Intent(getApplicationContext(), Dangnhap.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), Dathang.class));
                        }
                    }else {
                        Toast.makeText(GioHang.this, "S??? l?????ng ?????t mua l???n h??n s??? l?????ng c?? trong kho !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(GioHang.this, "Gi??? h??ng c???a b???n ??ang tr???ng!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void EventItemLisView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("X??a s???n ph???m ");
                builder.setMessage("B???n c?? mu???n x??a s???n ph???m n??y kh??ng?");
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(MainActivity.listGiohang.size() <= 0){
                            tvthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.listGiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventData();
                            if(MainActivity.listGiohang.size()<=0){
                                tvthongbao.setVisibility(View.VISIBLE);
                            }else {
                                tvthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventData();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventData();
                    }
                });
                builder.show();
                return false;
            }
        });

    }

    public static void EventData() {
        long tongtien = 0;
        for (int i =0; i<MainActivity.listGiohang.size(); i++){
            tongtien += MainActivity.listGiohang.get(i).getGiasp();
            soluongmua = MainActivity.listGiohang.get(i).getSoluongsp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtongtien.setText(decimalFormat.format(tongtien) + " ??");
    }

    private void CheckData() {
        if(MainActivity.listGiohang.size() <=0){//n???u m???ng k c?? d??? li???u
            //c???p nh???p v??? adapter
            giohangAdapter.notifyDataSetChanged();
            //cho th??ng b??o hi???n ra
            tvthongbao.setVisibility(View.VISIBLE);
            //???n t???ng ti???n
            tvtongtien.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            tvthongbao.setVisibility(View.INVISIBLE);
            tvtongtien.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToobar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void Anhxa() {
        lvgiohang = findViewById(R.id.lvgiohang);
        tvthongbao = findViewById(R.id.tvthongbao);
        tvtongtien = findViewById(R.id.tvtongtien);
        btndathang = findViewById(R.id.btn_danghang);
        btnmuatiep = findViewById(R.id.btn_muatiep);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(GioHang.this, MainActivity.listGiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}

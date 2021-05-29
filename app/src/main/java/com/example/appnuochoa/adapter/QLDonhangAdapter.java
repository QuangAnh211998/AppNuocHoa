package com.example.appnuochoa.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Admin.Quanlydonhang;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Donhang;
import com.example.appnuochoa.model.Sever;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QLDonhangAdapter extends BaseAdapter {


    String trangthaidh = "";
    private Quanlydonhang context;
   private ArrayList<Donhang> listdonhang;

    public QLDonhangAdapter(Quanlydonhang context, ArrayList<Donhang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @Override
    public int getCount() {
        return listdonhang.size();
    }

    @Override
    public Object getItem(int position) {
        return listdonhang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        private TextView tvmadh, tvtrangthai, tvhoten, tvngaydat;
        private TextView tvtongtien, tvsdt,tvdiachi;
        private ImageButton btntrangthai;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        QLDonhangAdapter.ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new QLDonhangAdapter.ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_donhang, null);

            viewHolder.tvtrangthai = view.findViewById(R.id.tvtrangthai);
            viewHolder.tvmadh = view.findViewById(R.id.tvmadh);
            viewHolder.tvhoten = view.findViewById(R.id.tvhotendh);
            viewHolder.tvsdt = view.findViewById(R.id.tvsdtdh);
            viewHolder.tvngaydat = view.findViewById(R.id.tvngaydat);
            viewHolder.tvtongtien = view.findViewById(R.id.tvtongtiendh);
            viewHolder.tvdiachi = view.findViewById(R.id.tvdiachidh);
            viewHolder.btntrangthai = view.findViewById(R.id.btntrangthai);

            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (QLDonhangAdapter.ViewHolder) view.getTag();
        }

        final Donhang donhang = (Donhang) getItem(position);
//        viewHolder.tvmadh.setText("Đơn hàng : "+damua.getId()+"");
        viewHolder.tvmadh.setText("Đơn hàng: "+donhang.getIddh()+"");
        viewHolder.tvhoten.setText(donhang.getHoten());
        viewHolder.tvsdt.setText(" - "+donhang.getSdt());
        viewHolder.tvdiachi.setText(donhang.getDiachi());
        viewHolder.tvngaydat.setText("Ngày đặt : "+donhang.getNgaydat());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvtongtien.setText("Tổng tiền: "+decimalFormat.format(donhang.getTongtien()) + " đ");
        viewHolder.tvtrangthai.setText(donhang.getTrangthai());

        final String trangthai = donhang.getTrangthai();

        viewHolder.btntrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangthai.equals("hoàn thành")){
                    Toast.makeText(context, "Đơn hàng này đã hoàn thành!", Toast.LENGTH_SHORT).show();
                }else {
                    DialogTrangthai( donhang.getIddh());
                }
            }
        });


        return  view;
    }
    private void DialogTrangthai(final int madh){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog_trangthai);

        final RadioButton radchovanchuyen = dialog.findViewById(R.id.radchovc);
        final RadioButton raddanggiao = dialog.findViewById(R.id.raddanggiao);
        final RadioButton radhoanthanh = dialog.findViewById(R.id.radhoanthanh);
        final RadioButton radhuydon = dialog.findViewById(R.id.radhuydon);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);
        Button btnhuy = dialog.findViewById(R.id.btnhuy);

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radchovanchuyen.isChecked()){
                    trangthaidh = "chờ vận chuyển";
                }
                if(raddanggiao.isChecked()){
                    trangthaidh = "đang giao";
                }
                if(radhoanthanh.isChecked()){
                    trangthaidh = "hoàn thành";
                }
                if (radhuydon.isChecked()){
                    trangthaidh = "đã hủy";
                }
                UpdateTrangthai(madh);
                dialog.dismiss();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void UpdateTrangthai(int madh) {

        final String query = "UPDATE donhang SET trangthai = '" + trangthaidh + "' WHERE madonhang ='" + madh + "'";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            context.getDonhang();
                        } else {
                            Toast.makeText(context, "lỗi!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}

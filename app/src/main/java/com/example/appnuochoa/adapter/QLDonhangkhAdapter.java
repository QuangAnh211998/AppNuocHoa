package com.example.appnuochoa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.QLdonhangKH;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Donhang;
import com.example.appnuochoa.model.Sever;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QLDonhangkhAdapter extends BaseAdapter {

    private QLdonhangKH context;
    private ArrayList<Donhang> listdhkh;

    public QLDonhangkhAdapter(QLdonhangKH context, ArrayList<Donhang> listdhkh) {
        this.context = context;
        this.listdhkh = listdhkh;
    }

    @Override
    public int getCount() {
        return listdhkh.size();
    }

    @Override
    public Object getItem(int position) {
        return listdhkh.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        private TextView hoten,diachi,tongtien,ngaydat,trangthai;
        private ImageButton btnhuydon;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        QLDonhangkhAdapter.ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new QLDonhangkhAdapter.ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_donhangkh, null);

            viewHolder.trangthai = view.findViewById(R.id.tvtrangthai);
            viewHolder.hoten = view.findViewById(R.id.tvhotendh);
            viewHolder.ngaydat = view.findViewById(R.id.tvngaydat);
            viewHolder.tongtien = view.findViewById(R.id.tvtongtiendh);
            viewHolder.diachi = view.findViewById(R.id.tvdiachidh);
            viewHolder.btnhuydon = view.findViewById(R.id.btnhuydon);

            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (QLDonhangkhAdapter.ViewHolder) view.getTag();
        }

        final Donhang donhang = (Donhang) getItem(position);
        viewHolder.hoten.setText(donhang.getHoten());
        viewHolder.diachi.setText(donhang.getDiachi());
        viewHolder.ngaydat.setText("Ngày đặt : "+donhang.getNgaydat());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tongtien.setText("Tổng tiền: "+decimalFormat.format(donhang.getTongtien()) + " đ");
        viewHolder.trangthai.setText(donhang.getTrangthai());

        final String trangthai = donhang.getTrangthai();

        viewHolder.btnhuydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangthai.equals("chờ xác nhận")) {
                    XacnhanHuy(donhang.getIddh());
                }else {
                }
            }
        });
        return  view;
    }
    private void XacnhanHuy(final int madh){
            AlertDialog.Builder dialoghuy = new AlertDialog.Builder(context);
            dialoghuy.setMessage("Bạn có muốn hủy đơn hàng này không?");
            dialoghuy.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    UpdateTrangthai(madh, "đã hủy");
                }
            });
            dialoghuy.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                }
            });
            dialoghuy.show();
    }
    private void UpdateTrangthai(int madh, String huydon) {

        final String query = "UPDATE donhang SET trangthai = '" + huydon + "' WHERE madonhang ='" + madh + "'";
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

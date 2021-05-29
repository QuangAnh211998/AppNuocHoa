package com.example.appnuochoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnuochoa.Javaclass.Dathang;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Damua;
import com.example.appnuochoa.model.Diachi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SPdamuaAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Damua> listspdamua;

    public SPdamuaAdapter(Context context, ArrayList<Damua> listspdamua) {
        this.context = context;
        this.listspdamua = listspdamua;
    }
    @Override
    public int getCount() {
        return listspdamua.size();
    }

    @Override
    public Object getItem(int position) {
        return listspdamua.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        private TextView  tvtensp, tvsoluong, tvngaymua, tvtthanhtien;
        private ImageView imghinhanh;

    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        SPdamuaAdapter.ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new SPdamuaAdapter.ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_spdamua, null);

            viewHolder.tvtensp = view.findViewById(R.id.tensp);
            viewHolder.tvsoluong = view.findViewById(R.id.soluongsp);
            viewHolder.tvngaymua = view.findViewById(R.id.ngaymua);
            viewHolder.tvtthanhtien = view.findViewById(R.id.thanhtien);
            viewHolder.imghinhanh = view.findViewById(R.id.hinhanhsp);

            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (SPdamuaAdapter.ViewHolder) view.getTag();
        }

        Damua damua = (Damua) getItem(position);
//        viewHolder.tvmadh.setText("Đơn hàng : "+damua.getId()+"");
        viewHolder.tvtensp.setText(damua.getTensp());
        viewHolder.tvsoluong.setText("Số lượng : "+damua.getSoluong());
        viewHolder.tvngaymua.setText("Ngày đặt : "+damua.getNgaymua());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvtthanhtien.setText(decimalFormat.format(damua.getGiaban()) + " đ");
        Picasso.get().load(damua.getHinhanh()).into(viewHolder.imghinhanh);

        return  view;
    }
}


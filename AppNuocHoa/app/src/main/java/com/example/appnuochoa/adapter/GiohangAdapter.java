package com.example.appnuochoa.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnuochoa.Javaclass.GioHang;
import com.example.appnuochoa.Javaclass.MainActivity;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Giohangmua;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohangmua> arraygiohang;

    ViewHolder viewHolder = null;

    public GiohangAdapter(Context context, ArrayList<Giohangmua> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView tvtengiohang, tvgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnvalues, btnplus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.tvtengiohang = view.findViewById(R.id.tengiohang);
            viewHolder.tvgiagiohang = view.findViewById(R.id.giagiohang);
            viewHolder.imggiohang = view.findViewById(R.id.imggiohang);
            viewHolder.btnminus = view.findViewById(R.id.btnminus);
            viewHolder.btnvalues = view.findViewById(R.id.btnvalues);
            viewHolder.btnplus = view.findViewById(R.id.btnplus);
            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohangmua giohangmua = (Giohangmua) getItem(i);
        viewHolder.tvtengiohang.setText(giohangmua.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvgiagiohang.setText(decimalFormat.format(giohangmua.getGiasp()) + " Đ");
        Picasso.get().load(giohangmua.getHinhsp()).into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohangmua.getSoluongsp()+ "");

        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl >=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);//ẩn
            viewHolder.btnminus.setVisibility(View.VISIBLE);//hiện
        }else if (sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if(sl >=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString())+1;
                int slhientai = MainActivity.listGiohang.get(i).getSoluongsp();
                long giahientai = MainActivity.listGiohang.get(i).getGiasp();
                MainActivity.listGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat)/slhientai;
                MainActivity.listGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.tvgiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
            }
        });
        return view;
    }
}

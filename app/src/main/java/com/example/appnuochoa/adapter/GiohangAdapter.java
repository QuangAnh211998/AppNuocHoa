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

import com.example.appnuochoa.Fragment.HomeFragment;
import com.example.appnuochoa.Javaclass.GioHang;
import com.example.appnuochoa.Javaclass.MainActivity;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Giohangmua;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Giohangmua> arraygiohang;

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
        public TextView tvtengiohang, tvgiagiohang, tvvalues;
        public ImageView imggiohang;
        public Button btnminus, btnplus;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.tvtengiohang = view.findViewById(R.id.tengiohang);
            viewHolder.tvgiagiohang = view.findViewById(R.id.giagiohang);
            viewHolder.imggiohang = view.findViewById(R.id.imggiohang);
            viewHolder.btnminus = view.findViewById(R.id.btnminus);
            viewHolder.tvvalues = view.findViewById(R.id.tvvalues);
            viewHolder.btnplus = view.findViewById(R.id.btnplus);
            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohangmua giohang = (Giohangmua) getItem(position);
        viewHolder.tvtengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" đ");
        Picasso.get().load(giohang.getHinhsp()).into(viewHolder.imggiohang);
        viewHolder.tvvalues.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewHolder.tvvalues.getText().toString());
        if(sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);

        } else if(sl >= 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);

        }
        final ViewHolder btnviewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btnviewHolder.tvvalues.getText().toString()) + 1;
                int slhientai = MainActivity.listGiohang.get(position).getSoluongsp();
                long giaht = MainActivity.listGiohang.get(position).getGiasp();
                MainActivity.listGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.listGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                btnviewHolder.tvgiagiohang.setText(decimalFormat.format(giamoinhat)+ " đ");
                GioHang.EventData();
                if (slmoinhat > 9){
                    btnviewHolder.btnplus.setVisibility(View.INVISIBLE);
                    btnviewHolder.btnminus.setVisibility(View.VISIBLE);
                    btnviewHolder.tvvalues.setText(String.valueOf(slmoinhat));

                }else {
                    btnviewHolder.btnminus. setVisibility(View.VISIBLE);
                    btnviewHolder.btnplus. setVisibility(View.VISIBLE);
                    btnviewHolder.tvvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(btnviewHolder.tvvalues.getText().toString()) - 1;
                int slhientai = MainActivity.listGiohang.get(position).getSoluongsp();
                long giaht = MainActivity.listGiohang.get(position).getGiasp();
                MainActivity.listGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.listGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                btnviewHolder.tvgiagiohang.setText(decimalFormat.format(giamoinhat)+ " đ");
                GioHang.EventData();
                if (slmoinhat < 2){
                    btnviewHolder.btnminus.setVisibility(View.INVISIBLE);
                    btnviewHolder.btnplus.setVisibility(View.VISIBLE);
                    btnviewHolder.tvvalues.setText(String.valueOf(slmoinhat));
                }else {
                    btnviewHolder.btnminus. setVisibility(View.VISIBLE);
                    btnviewHolder.btnplus. setVisibility(View.VISIBLE);
                    btnviewHolder.tvvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return  view;
    }
}

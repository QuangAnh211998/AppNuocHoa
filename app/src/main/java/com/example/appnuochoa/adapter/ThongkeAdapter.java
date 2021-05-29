package com.example.appnuochoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Diachi;
import com.example.appnuochoa.model.Thongke;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThongkeAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Thongke> listthongke;

    public ThongkeAdapter(Context context, ArrayList<Thongke> listthongke) {
        this.context = context;
        this.listthongke = listthongke;
    }

    @Override
    public int getCount() {
        return listthongke.size();
    }

    @Override
    public Object getItem(int position) {
        return listthongke.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        private TextView tvtongtien,tvthoigian;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ThongkeAdapter.ViewHolder viewHolder = null;
        if (view == null){ //nếu chưa có chưa có dữ liệu
            viewHolder = new ThongkeAdapter.ViewHolder();
            //gán layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_thongke, null);
            viewHolder.tvthoigian = view.findViewById(R.id.tvthoigian);
            viewHolder.tvtongtien = view.findViewById(R.id.tvtongtien_tk);

            view.setTag(viewHolder);
        }else {//nếu có dữ liệu chỉ cần gán lại
            viewHolder = (ThongkeAdapter.ViewHolder) view.getTag();
        }
        Thongke thongke = (Thongke) getItem(position);
        viewHolder.tvthoigian.setText("Tháng/Năm : " +thongke.getThoigian());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvtongtien.setText(decimalFormat.format(thongke.getTongtien()) + " đ");

        return view;
    }
}

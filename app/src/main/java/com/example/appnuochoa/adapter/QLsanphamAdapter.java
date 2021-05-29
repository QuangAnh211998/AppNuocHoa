package com.example.appnuochoa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Admin.CapnhatSanpham;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QLsanphamAdapter extends RecyclerView.Adapter<QLsanphamAdapter.ItemHolder> {

    private Context context;
    private ArrayList<Sanpham> listsanpham;
    private ArrayList<Sanpham> arrayListSP;

    public QLsanphamAdapter(Context context, ArrayList<Sanpham> listsanpham) {
        this.context = context;
        this.listsanpham = listsanpham;
        this.arrayListSP = new ArrayList<Sanpham>();
        this.arrayListSP.addAll(listsanpham);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_qlsanpham, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {

        final Sanpham sanpham = listsanpham.get(position);
        holder.tvtensp.setMaxLines(2);
        holder.tvtensp.setEllipsize(TextUtils.TruncateAt.END);
        holder.tvtensp.setText(sanpham.getTensp());
        holder.tvloai.setText("Nước hoa: "+ sanpham.getLoaisp());
        holder.tvxuatxu.setText("Made in "+sanpham.getXuatxu());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgiaban.setText("Giá: "+decimalFormat.format(sanpham.getGiaban()) + " đ");
        Picasso.get().load(sanpham.getHinhanh()).into(holder.imghinhsp);

        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacnhanXoa(listsanpham.get(position).getId(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listsanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView imghinhsp;
        private TextView tvtensp,tvxuatxu,tvgiaban,tvloai;
        private Button btnsua,btnxoa;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhsp = itemView.findViewById(R.id.imgsanpham);
            tvtensp =  itemView.findViewById(R.id.tvtensp);
            tvxuatxu = itemView.findViewById(R.id.tvxuatxu);
            tvloai = itemView.findViewById(R.id.tvloai);
            tvgiaban = itemView.findViewById(R.id.tvgiaban);
            btnsua = itemView.findViewById(R.id.btnsua);
            btnxoa = itemView.findViewById(R.id.btnxoa);

            btnsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CapnhatSanpham.class);
                    intent.putExtra("thongtin",listsanpham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
    private void XacnhanXoa(final int id, final int position){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa địa chỉ này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DeleteSanpham(id,position);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }
    private void DeleteSanpham(int idsp, final int position){
        final String query = "DELETE FROM nuochoa WHERE id ='"+idsp+"'";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkUpdate_Delete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            listsanpham.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context,"Xóa thành công!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context,"lỗi xóa!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error!", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("truyvan",query);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listsanpham.clear();
        if (charText.length() == 0) {
            listsanpham.addAll(arrayListSP);
        } else {
            for (Sanpham sp : arrayListSP) {
                if (sp.getTensp().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listsanpham.add(sp);
                }
                if (sp.getMota().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listsanpham.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}


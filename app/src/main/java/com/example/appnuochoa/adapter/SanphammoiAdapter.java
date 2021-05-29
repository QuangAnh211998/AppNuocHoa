package com.example.appnuochoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnuochoa.Javaclass.ChitietSanpham;
import com.example.appnuochoa.R;
import com.example.appnuochoa.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphammoiAdapter extends RecyclerView.Adapter<SanphammoiAdapter.ItemHolderSPmoi> {
    private Context context;
    ArrayList<Sanpham> arraysanphammoi;

    public SanphammoiAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanphammoi = arraysanpham;
    }

    @Override
    public SanphammoiAdapter.ItemHolderSPmoi onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoi, null);
        SanphammoiAdapter.ItemHolderSPmoi itemHoldermoi = new SanphammoiAdapter.ItemHolderSPmoi(v);
        return itemHoldermoi;
    }

    @Override
    public void onBindViewHolder(@NonNull final SanphammoiAdapter.ItemHolderSPmoi holder, final int position) {

        final Sanpham sanpham = arraysanphammoi.get(position);
        holder.txttensp.setMaxLines(2);
        holder.txttensp.setEllipsize(TextUtils.TruncateAt.END);
        holder.txttensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiaban.setText(decimalFormat.format(sanpham.getGiaban()) + " đ");
        Picasso.get().load(sanpham.getHinhanh()).into(holder.imghinhanhsp);

    }

    @Override
    public int getItemCount() {
        return arraysanphammoi.size();
    }

    public class ItemHolderSPmoi extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsp;
        public TextView txttensp, txtgiaban;
        public LinearLayout viewItemmoi;

        public ItemHolderSPmoi(@NonNull View itemView) {
            super(itemView);
            imghinhanhsp = (ImageView)itemView.findViewById(R.id.img_sanpham);
            txttensp = (TextView)itemView.findViewById(R.id.txt_tensp);
            txtgiaban = (TextView)itemView.findViewById(R.id.txt_giaban);
            viewItemmoi = (LinearLayout) itemView.findViewById(R.id.viewItemspmoi);

            viewItemmoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChitietSanpham.class);
                    intent.putExtra("thongtinsp", arraysanphammoi.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

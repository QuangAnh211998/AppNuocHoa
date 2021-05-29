package com.example.appnuochoa.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.GioHang;
import com.example.appnuochoa.Javaclass.Timkiem;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.Sanpham_adapter;
import com.example.appnuochoa.adapter.SanphammoiAdapter;
import com.example.appnuochoa.model.Giohangmua;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewhome, recyclerViewspmoi;
    public static ArrayList<Sanpham> listsp;
    private ArrayList<Sanpham> listspmoi;
    private LinearLayout timkiem;
    private ImageButton imggiohang;
    Sanpham_adapter spadapter;
    SanphammoiAdapter spmoiadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewhome = (RecyclerView) view.findViewById(R.id.recyclerviewhome);
        listsp = new ArrayList<>();
        spadapter = new Sanpham_adapter(getContext(), listsp);
        recyclerViewhome.setHasFixedSize(true);
        recyclerViewhome.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewhome.setAdapter(spadapter);


        recyclerViewspmoi = (RecyclerView) view.findViewById(R.id.recyclerviewspmoi);
        listspmoi = new ArrayList<>();
        spmoiadapter = new SanphammoiAdapter(getContext(), listspmoi);
        recyclerViewspmoi.setHasFixedSize(true);
        recyclerViewspmoi.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewspmoi.setAdapter(spmoiadapter);

        imggiohang = view.findViewById(R.id.btnGioHang);
        timkiem = view.findViewById(R.id.seach);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Timkiem.class));
            }
        });
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GioHang.class));
            }
        });

        GetSanphammoinhat();
        GetSanpham();
        return view;

    }


    private void GetSanphammoinhat() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.linksanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("getDataSuccess", response.length() + "");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int Id = jsonObject.getInt("Id");
                        String Ten = jsonObject.getString("Ten");
                        String Loai = jsonObject.getString("Loai");
                        String Xuatxu = jsonObject.getString("Xuatxu");
                        String Hinhanh = jsonObject.getString("Hinhanh");
                        String Huong = jsonObject.getString("Huong");
                        Integer Soluong = jsonObject.getInt("Soluong");
                        Integer Giaban = jsonObject.getInt("Giaban");
                        String Mota = jsonObject.getString("Mota");

                        listspmoi.add(new Sanpham(Id, Ten, Loai, Xuatxu, Hinhanh, Huong, Soluong, Giaban, Mota));
                        spmoiadapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewspmoi.setLayoutManager(layoutManager);
    }

    private void GetSanpham() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.linksanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("getDataSuccess", response.length() + "");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int Id = jsonObject.getInt("Id");
                        String Ten = jsonObject.getString("Ten");
                        String Loai = jsonObject.getString("Loai");
                        String Xuatxu = jsonObject.getString("Xuatxu");
                        String Hinhanh = jsonObject.getString("Hinhanh");
                        String Huong = jsonObject.getString("Huong");
                        Integer Soluong = jsonObject.getInt("Soluong");
                        Integer Giaban = jsonObject.getInt("Giaban");
                        String Mota = jsonObject.getString("Mota");

                        listsp.add(new Sanpham(Id, Ten, Loai, Xuatxu, Hinhanh, Huong, Soluong, Giaban, Mota));
                        spadapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}


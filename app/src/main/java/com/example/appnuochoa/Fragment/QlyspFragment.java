package com.example.appnuochoa.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Admin.Themsanpham;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.QLsanphamAdapter;
import com.example.appnuochoa.model.Sanpham;
import com.example.appnuochoa.model.Sever;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QlyspFragment extends Fragment {

    private SearchView searchView;
    private TextView tvthemsp;
    private RecyclerView recyclerView;
    private ArrayList<Sanpham> listqlsp;
    QLsanphamAdapter qlspadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlysp, container, false);

        searchView = view.findViewById(R.id.seachviewad);
        tvthemsp = view.findViewById(R.id.tvthem);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        listqlsp = new ArrayList<>();
        qlspadapter = new QLsanphamAdapter(getContext(), listqlsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(qlspadapter);

//        timkiem = view.findViewById(R.id.seach);
//        timkiem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), TimkiemAdmin.class));
//            }
//        });
        tvthemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Themsanpham.class));
            }
        });

        GetSanpham();
        SearchView();
        return view;
    }
    private  void SearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                qlspadapter.filter(newText.trim());
                return false;
            }
        });
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

                        listqlsp.add(new Sanpham(Id, Ten, Loai, Xuatxu, Hinhanh, Huong, Soluong, Giaban, Mota));
                        qlspadapter = new QLsanphamAdapter(getContext(),listqlsp);
                        recyclerView.setAdapter(qlspadapter);
                        qlspadapter.notifyDataSetChanged();

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

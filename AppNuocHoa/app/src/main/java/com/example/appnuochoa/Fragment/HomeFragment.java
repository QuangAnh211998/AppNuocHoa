package com.example.appnuochoa.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.Sanpham_adapter;
import com.example.appnuochoa.adapter.SanphammoiAdapter;
import com.example.appnuochoa.model.Sanpham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewhome, recyclerViewspmoi;
    ArrayList<Sanpham> listsp;
    ArrayList<Sanpham> listspmoi;
    Sanpham_adapter spadapter;
    SanphammoiAdapter spmoiadapter;
    public String urlhome = "http://192.168.1.6/servernuochoa/getnuochoa.php";
   // public String urlspmoi = "http://192.168.1.6/servernuochoa/getsanphammoi.php";
    public String urlspmoi = "http://192.168.1.6/servernuochoa/getsanphammoi.php";

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listsp = new ArrayList<>();
        spadapter = new Sanpham_adapter(getContext(), listsp);
        recyclerViewhome = (RecyclerView) view.findViewById(R.id.recyclerviewhome);
        listsp = new ArrayList<>();
        spadapter = new Sanpham_adapter(getContext(),listsp);
        recyclerViewhome.setHasFixedSize(true);
        recyclerViewhome.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewhome.setAdapter(spadapter);

        listspmoi = new ArrayList<>();
        spmoiadapter = new SanphammoiAdapter(getContext(), listspmoi);
        recyclerViewspmoi = (RecyclerView) view.findViewById(R.id.recyclerviewspmoi);
        listspmoi = new ArrayList<>();
        spmoiadapter = new SanphammoiAdapter(getContext(),listspmoi);
        recyclerViewspmoi.setHasFixedSize(true);
        recyclerViewspmoi.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewspmoi.setAdapter(spmoiadapter);

        GetSanphammoinhat(urlspmoi);
        GetSanpham(urlhome);
        return view;

    }


    private void GetSanphammoinhat(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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

                        listspmoi.add(new Sanpham(Id,Ten,Loai,Xuatxu,Hinhanh,Huong,Soluong,Giaban,Mota));
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

    private void GetSanpham(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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

                        listsp.add(new Sanpham(Id,Ten,Loai,Xuatxu,Hinhanh,Huong,Soluong,Giaban,Mota));
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


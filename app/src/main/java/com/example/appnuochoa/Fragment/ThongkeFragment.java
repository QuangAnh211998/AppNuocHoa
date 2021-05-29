package com.example.appnuochoa.Fragment;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnuochoa.Javaclass.SanphamDamua;
import com.example.appnuochoa.Javaclass.Sodiachi;
import com.example.appnuochoa.R;
import com.example.appnuochoa.adapter.SodiachiAdapter;
import com.example.appnuochoa.adapter.ThongkeAdapter;
import com.example.appnuochoa.model.Damua;
import com.example.appnuochoa.model.Sever;
import com.example.appnuochoa.model.Thongke;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongkeFragment extends Fragment {

    private RadioButton radionam,radiothang;
    private EditText edtthoigian;
    private ListView lvthongke;
    private TextView tvxacnhan;
    private BarChart barChart;
    private ArrayList<Thongke> listthongke;
//    ThongkeAdapter thongkeAdapter;

    String trangthai = "hoàn thành";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);

        barChart = view.findViewById(R.id.barchar);

        edtthoigian = view.findViewById(R.id.edtnamtk);
//        lvthongke = view.findViewById(R.id.lvthongke);
        tvxacnhan = view.findViewById(R.id.tvxacnhan);
        radionam = view.findViewById(R.id.radiobtnnam);
        radiothang = view.findViewById(R.id.radiobtnthang);

        tvxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radiothang.isChecked()) {
                    listthongke = new ArrayList<>();
//                    thongkeAdapter = new ThongkeAdapter(getContext(), listthongke);
//                    lvthongke.setAdapter(thongkeAdapter);
                    Thongketheothang();
                }if(radionam.isChecked()){
                    listthongke = new ArrayList<>();
//                    thongkeAdapter = new ThongkeAdapter(getContext(), listthongke);
//                    lvthongke.setAdapter(thongkeAdapter);
                    Thongketheonam();
                }
            }
        });
        return view;
    }

    private void Thongketheothang() {

        if (edtthoigian.length() < 4) {
            Toast.makeText(getContext(),"Bạn cần nhập đúng năm !", Toast.LENGTH_SHORT).show();
        } else {
            String nam = edtthoigian.getText().toString().trim();
            final String query = "Select MonTh(ngaydathang) as thoigian , sum(tongtien) as tongtien From donhang Where" +
                    " trangthai = '" + trangthai + "' AND Year(ngaydathang)= " + nam + " group by MonTh(ngaydathang)";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkthongke, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response.equals("null")) {
                            Toast.makeText(getContext(), "không có!", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String Thoigian = jsonObject.getString("thoigian");
                                    int Tongtien = jsonObject.getInt("tongtien");
                                    listthongke.add(new Thongke(Thoigian, Tongtien));

//                                thongkeAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<BarEntry> barEntry = new ArrayList<>();
                    int thang1 = 0;
                    int tt1 = 0;
                    int thang2 = 0;
                    int tt2 = 0;
                    int thang3 = 0;
                    int tt3 = 0;
                    int thang4 = 0;
                    int tt4 = 0;
                    int thang5 = Integer.parseInt(listthongke.get(0).getThoigian());
                    int tt5 = listthongke.get(0).getTongtien();
                    int thang6 = Integer.parseInt(listthongke.get(1).getThoigian());
                    int tt6 = listthongke.get(1).getTongtien();
                    int thang7 = 0;
                    int tt7 = 0;
                    int thang8 = 0;
                    int tt8 = 0;
                    int thang9 = 0;
                    int tt9 = 0;
                    int thang10 = 0;
                    int tt10 = 0;
                    int thang11 = 0;
                    int tt11 = 0;
                    int thang12 = 0;
                    int tt12 = 0;

                    barEntry.add(new BarEntry(thang1, tt1));
                    barEntry.add(new BarEntry(thang2, tt2));
                    barEntry.add(new BarEntry(thang3, tt3));
                    barEntry.add(new BarEntry(thang4, tt4));
                    barEntry.add(new BarEntry(thang5, tt5));
                    barEntry.add(new BarEntry(thang6, tt6));
                    barEntry.add(new BarEntry(thang7, tt7));
                    barEntry.add(new BarEntry(thang8, tt8));
                    barEntry.add(new BarEntry(thang9, tt9));
                    barEntry.add(new BarEntry(thang10,tt10));
                    barEntry.add(new BarEntry(thang11,tt11));
                    barEntry.add(new BarEntry(thang12,tt12));


                    BarDataSet barDataSet = new BarDataSet(barEntry, "Tháng");
                    barDataSet.setColors(Color.RED);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(15f);
                    BarData barData = new BarData(barDataSet);
                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Biểu đồ doanh thu theo tháng");
                    barChart.animateY(4000);

//                String[] thang = new String[]{"thang1","thang2","thang3","thang4","thang5","thang6","thang7",};
//                xAxis.setValueFormatter(new MyAxis(thang));
                    XAxis xAxis = barChart.getXAxis();
//                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1);
                    xAxis.setGranularityEnabled(true);

                    barChart.setDragEnabled(true);
                    barChart.getXAxis().setAxisMinimum(0);
                    barChart.getXAxis().setAxisMaximum(12);
//                barChart.getAxisLeft().setAxisMinimum(1);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("thongke", query);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }

    private void Thongketheonam() {

        if (edtthoigian.length() < 4) {
            Toast.makeText(getContext(),"Bạn cần nhập đúng năm !", Toast.LENGTH_SHORT).show();
        } else {
            String nam = edtthoigian.getText().toString().trim();
            final String query = "Select Year(ngaydathang) as thoigian, sum(tongtien) as tongtien From donhang Where " +
                    " trangthai = '" + trangthai + "' AND Year(ngaydathang)>=" + nam + -4 + " AND" +
                    " Year(ngaydathang)<=" + nam + " group by Year(ngaydathang)";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.linkthongke, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response.equals("null")) {
                            Toast.makeText(getContext(), "không có!", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String Thoigian = jsonObject.getString("thoigian");
                                    int Tongtien = jsonObject.getInt("tongtien");
                                    listthongke.add(new Thongke(Thoigian, Tongtien));
//                                thongkeAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                nam.add("2021");
//                nam.add("2022");
//                nam.add("2023");
//                nam.add("2024");

                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    int nam2016 = 0;
                    int tt2016 = 0;
                    int nam2017 = 0;
                    int tt2017 = 0;
                    int nam2018 = 0;
                    int tt2018 = 0;
                    int nam2019 = Integer.parseInt(listthongke.get(0).getThoigian());
                    int tt2019 = listthongke.get(0).getTongtien();
                    int nam2020 = Integer.parseInt(listthongke.get(1).getThoigian());
                    int tt2020 = listthongke.get(1).getTongtien();
                    int nam2021 = 0;
                    int tt2021 = 0;
                    int nam2022 = 0;
                    int tt2022 = 0;
                    int nam2023 = 0;
                    int tt2023 = 0;

                    barEntries.add(new BarEntry(nam2016, tt2016));
                    barEntries.add(new BarEntry(nam2017, tt2017));
                    barEntries.add(new BarEntry(nam2018, tt2018));
                    barEntries.add(new BarEntry(nam2019, tt2019));
                    barEntries.add(new BarEntry(nam2020, tt2020));
                    barEntries.add(new BarEntry(nam2021, tt2021));
                    barEntries.add(new BarEntry(nam2022, tt2022));
                    barEntries.add(new BarEntry(nam2023, tt2023));
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Năm");
                    barDataSet.setColors(Color.BLUE);
                    barDataSet.setValueTextColor(Color.RED);
                    barDataSet.setValueTextSize(15f);
                    BarData Data = new BarData(barDataSet);
//                Data.setBarWidth(1f);

                    barChart.setFitBars(true);
                    barChart.setData(Data);
                    barChart.getDescription().setText("Biểu đồ doanh thu theo năm");
                    barChart.animateY(4000);

                    XAxis xAxis = barChart.getXAxis();
//                    xAxis.setCenterAxisLabels(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1);
                    xAxis.setGranularityEnabled(true);
                    barChart.getXAxis().setAxisMinimum(2016);
                    barChart.getXAxis().setAxisMaximum(2023);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("thongke", query);
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }
}
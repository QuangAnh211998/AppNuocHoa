package com.example.appnuochoa.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnuochoa.Javaclass.Timkiem;
import com.example.appnuochoa.R;

public class CategoryFragment extends Fragment {

    private Button btnnam,btnnu;
    private Button btnmuc1,btnmuc2,btnmuc3,btnmuc4;
    private LinearLayout timkiem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        btnnam = view.findViewById(R.id.nam);
        btnnu = view.findViewById(R.id.nu);
        btnmuc1 = view.findViewById(R.id.muc1);
        btnmuc2 = view.findViewById(R.id.muc2);
        btnmuc3 = view.findViewById(R.id.muc3);
        btnmuc4 = view.findViewById(R.id.muc4);

        timkiem = view.findViewById(R.id.seach);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Timkiem.class));
            }
        });

        EventButton();
        return view;
    }

    private void EventButton() {
        btnnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","nam");
                startActivity(intent);
            }
        });
        btnnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","nu");
                startActivity(intent);
            }
        });
        btnmuc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","muc1");
                startActivity(intent);
            }
        });
        btnmuc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","muc2");
                startActivity(intent);
            }
        });
        btnmuc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","muc3");
                startActivity(intent);
            }
        });
        btnmuc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadData.class);
                intent.putExtra("data","muc4");
                startActivity(intent);
            }
        });
    }
}

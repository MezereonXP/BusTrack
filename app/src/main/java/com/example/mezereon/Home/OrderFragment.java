package com.example.mezereon.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mezereon.R;


public class OrderFragment extends Fragment {

    private View viewInOrderFragment;
    private Button booked;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewInOrderFragment=inflater.inflate(R.layout.fragment_order, container, false);
        booked = (Button) viewInOrderFragment.findViewById(R.id.button);
        booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(booked.getText().toString()){
                    case "点击预订":
                        Toast.makeText(OrderFragment.this.getContext(),"成功预订",Toast.LENGTH_SHORT).show();
                        booked.setText("点击退订");
                        break;
                    case "点击退订":
                        Toast.makeText(OrderFragment.this.getContext(),"成功退订",Toast.LENGTH_SHORT).show();
                        booked.setText("点击预订");
                        break;
                }

            }
        });
        return viewInOrderFragment;

    }


}

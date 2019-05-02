package com.example.proxyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AsistenteFragment extends Fragment {
    TextView tvabono, tvdeuda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        Integer abono = bundle.getInt("abono");
        Integer precio = bundle.getInt("precio");

        Integer deuda = precio - abono;

        View v = inflater.inflate(R.layout.fragment_asistente, container, false);

        tvabono = v.findViewById(R.id.tv_saldo_favor);
        tvdeuda = v.findViewById(R.id.tv_saldo_deudor);

        tvabono.setText(String.valueOf(abono));
        tvdeuda.setText(String.valueOf(deuda));

        return v;
    }
}

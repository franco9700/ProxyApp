package com.example.proxyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;


public class AsistenteFragment extends Fragment {
    TextView tvabono, tvdeuda;
    Button btnelegir, btnmodificar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        Integer abono = bundle.getInt("abono", 0);
        Integer precio = bundle.getInt("precio", 0);

        Integer deuda = precio - abono;

        View v = inflater.inflate(R.layout.fragment_asistente, container, false);

        tvabono = v.findViewById(R.id.tv_saldo_favor);
        tvdeuda = v.findViewById(R.id.tv_saldo_deudor);

        tvabono.setText(String.valueOf(abono));
        tvdeuda.setText(String.valueOf(deuda));

        btnelegir = v.findViewById(R.id.elegir);
        btnmodificar = v.findViewById(R.id.modificar);

        btnelegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor, new elegirCarnetFragment()).commit();
            }
        });

        return v;
    }
}

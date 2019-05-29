package com.example.proxyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFragment extends Fragment {
    Button nuevo_carnet, nueva_visita, nuevo_taller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        nuevo_carnet = v.findViewById(R.id.nuevo_carnet);
        nueva_visita = v.findViewById(R.id.nueva_visita);
        nuevo_taller = v.findViewById(R.id.nuevo_taller);

        nuevo_carnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoCarnetFragment nuevoCarnetFrag = new nuevoCarnetFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor, nuevoCarnetFrag).commit();
            }
        });

        nuevo_taller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoTallerFragment nuevoTallerFrag = new nuevoTallerFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor, nuevoTallerFrag).commit();
            }
        });

        nueva_visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevaVisitaFragment nuevaVisitaFrag = new nuevaVisitaFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contenedor, nuevaVisitaFrag).commit();
            }
        });

        return v;
    }
}

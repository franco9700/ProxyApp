package com.example.proxyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class nuevaVisitaFragment extends Fragment {
    Button btn_visita;
    EditText nombre_visita;
    EditText cupo_visita;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nueva_visita, container, false);

        btn_visita = v.findViewById(R.id.btn_guardar_visita);
        nombre_visita = v.findViewById(R.id.new_nombre_visita);
        cupo_visita = v.findViewById(R.id.cupo_visita);

        btn_visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nombre = nombre_visita.getText().toString();
                final String valor = cupo_visita.getText().toString();

                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonReponse = new JSONObject(response);
                            boolean success = jsonReponse.getBoolean("success");

                            if (success){

                                AdminFragment nuevoAdminFrag = new AdminFragment();

                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.contenedor, nuevoAdminFrag).commit();



                                Toast toast1 =
                                        Toast.makeText(getContext(),
                                                "Visita creada correctamente", Toast.LENGTH_SHORT);

                                toast1.show();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                                builder.setMessage("Error al guardar visita: Intente de nuevo")
                                        .setNegativeButton("Ok", null)
                                        .create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                NuevoElementoAdminRequest carnetRequest = new NuevoElementoAdminRequest("visita", nombre, valor, respoListener);

                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(carnetRequest);

            }
        });

        return v;
    }

}

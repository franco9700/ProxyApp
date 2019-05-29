package com.example.proxyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class elegirCarnetFragment extends Fragment {
    Spinner carnetSpinner;
    ArrayList<String> nombres_carnets;
    ArrayList<String> precios_carnets;

    Spinner visitaSpinner;
    ArrayList<String> visitas;

    Spinner tallerSpinner;
    ArrayList<String> talleres;

    TextView tvtotal;

    Button btn_listo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        final Integer id = bundle.getInt("id", 0);
        final String email = bundle.getString("email");
        final String password = bundle.getString("password");

        View v = inflater.inflate(R.layout.fragment_elegir_carnet, container, false);

        visitas = new ArrayList<>();
        visitaSpinner = v.findViewById(R.id.visita_spinner);

        nombres_carnets = new ArrayList<>();
        precios_carnets = new ArrayList<>();
        carnetSpinner = v.findViewById(R.id.carnet_spinner);

        talleres = new ArrayList<>();
        tallerSpinner = v.findViewById(R.id.taller_spinner);

        tvtotal = v.findViewById(R.id.tv_total);

        btn_listo = v.findViewById(R.id.btn_listo);

        listar_visitas();
        listar_carnet();
        listar_talleres();

        carnetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String carnet = carnetSpinner.getSelectedItem().toString();

                RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"Carnet.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject=new JSONObject(response);
                                    JSONArray jsonArray=jsonObject.getJSONArray("arreglo");
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                        String nombre_carnet=jsonObject1.getString("nombre");
                                        String precio_carnet=jsonObject1.getString("precio");

                                        if (nombre_carnet.equals(carnet)){

                                            tvtotal.setText("$"+precio_carnet);

                                        }
                                    }
                                }catch (JSONException e){e.printStackTrace();}
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String carnet = carnetSpinner.getSelectedItem().toString();
                final String visita = visitaSpinner.getSelectedItem().toString();
                final String taller = tallerSpinner.getSelectedItem().toString();

                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonReponse = new JSONObject(response);
                            boolean success = jsonReponse.getBoolean("success");

                            if (success){
                                Response.Listener<String> responseListener = new Response.Listener<String>(){
                                    @Override
                                    public void onResponse (String response){
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success){
                                                String nombre = jsonResponse.getString("nombre");
                                                Integer id = jsonResponse.getInt("id");
                                                Integer abono = jsonResponse.getInt("abono");
                                                Integer precio = jsonResponse.getInt("precio");

                                                String tipo_usuario = jsonResponse.getString("tipo_usuario");

                                                Intent intent = new Intent(getActivity().getApplicationContext(), MenuLateral.class);
                                                intent.putExtra("nombre", nombre);
                                                intent.putExtra("id", id);
                                                intent.putExtra("abono", abono);
                                                intent.putExtra("precio", precio);
                                                intent.putExtra("tipo_usuario", tipo_usuario);
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);

                                                getActivity().getApplicationContext().startActivity(intent);



                                                Toast toast1 =
                                                        Toast.makeText(getContext(),
                                                                "Carnet elegido con exito", Toast.LENGTH_SHORT);

                                                toast1.show();

                                            }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                                                builder.setMessage("Error al iniciar sesión")
                                                        .setNegativeButton("Retry", null)
                                                        .create().show();

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);

                                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                queue.add(loginRequest);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                                builder.setMessage("Error al registrar carnet: Intente de nuevo")
                                        .setNegativeButton("Ok", null)
                                        .create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegistrarCarnetRequest carnetRequest = new RegistrarCarnetRequest(id, carnet, visita, taller, respoListener);

                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(carnetRequest);

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public void listar_carnet(){
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"Carnet.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("arreglo");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String nombre_carnet=jsonObject1.getString("nombre");
                                String precio_carnet=jsonObject1.getString("precio");
                                nombres_carnets.add(nombre_carnet);
                                precios_carnets.add(precio_carnet);
                            }
                            carnetSpinner.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nombres_carnets));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void listar_visitas(){
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"Visita.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("arreglo");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String visita=jsonObject1.getString("nombre");
                                visitas.add(visita);
                            }
                            visitaSpinner.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, visitas));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void listar_talleres(){
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"Taller.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("arreglo");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String visita=jsonObject1.getString("nombre");
                                talleres.add(visita);
                            }
                            tallerSpinner.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, talleres));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}

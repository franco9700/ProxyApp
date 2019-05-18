package com.example.proxyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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


public class modCarnetFragment extends Fragment{
    Spinner tallaSpinner;

    Spinner visitaSpinner;
    ArrayList<String> visitas;

    Spinner tallerSpinner;
    ArrayList<String> talleres;

    Button btn_mod;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        final Integer id = bundle.getInt("id", 0);
        final String email = bundle.getString("email");
        final String password = bundle.getString("password");

        View v = inflater.inflate(R.layout.fragment_mod_carnet, container, false);

        visitas = new ArrayList<>();
        visitaSpinner = v.findViewById(R.id.visita_mod);

        tallaSpinner = v.findViewById(R.id.talla_mod);

        talleres = new ArrayList<>();
        tallerSpinner = v.findViewById(R.id.taller_mod);

        btn_mod = v.findViewById(R.id.btn_actualizar);

        listar_visitas();
        listar_talleres();
        listar_tallas();

        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String talla = tallaSpinner.getSelectedItem().toString();
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
                                builder.setMessage("Error al actualizar carnet: Intente de nuevo")
                                        .setNegativeButton("Ok", null)
                                        .create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                ActualizarCarnetRequest carnetRequest = new ActualizarCarnetRequest(id, talla, visita, taller, respoListener);

                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(carnetRequest);

            }
        });

        return v;
    }

    public void listar_tallas(){

        String[] tallas = {"S - Hombre","M - Hombre","G - Hombre","XG - Hombre", "S - Mujer","M - Mujer","G - Mujer","XG - Mujer"};
        tallaSpinner.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, tallas));

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

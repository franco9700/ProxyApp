package com.example.proxyapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCarnetRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL=conexion.URL_WEB_SERVICES+"Registrar_Carnet.php";
    private Map<String,String> params;

    public RegistrarCarnetRequest(Integer id, String carnet, String visita, String taller, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params=new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("carnet", carnet);
        params.put("visita", visita);
        params.put("taller", taller);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


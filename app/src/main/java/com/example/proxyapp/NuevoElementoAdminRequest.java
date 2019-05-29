package com.example.proxyapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NuevoElementoAdminRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL=conexion.URL_WEB_SERVICES+"admin.php";
    private Map<String,String> params;

    public NuevoElementoAdminRequest(String tabla, String nombre, String valor, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params=new HashMap<>();
        params.put("valor", valor);
        params.put("nombre", nombre);
        params.put("tabla", tabla);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

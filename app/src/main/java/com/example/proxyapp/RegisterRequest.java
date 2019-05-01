package com.example.proxyapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://192.168.3.12/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String nombre, String num_control, String email, String telefono, String escuela, String semestre, String estado, String
                           municipio, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params=new HashMap<>();
        params.put("nombre", nombre);
        params.put("num_control", num_control);
        params.put("email", email);
        params.put("telefono", telefono);
        params.put("escuela", escuela);
        params.put("semestre", semestre);
        params.put("estado", estado);
        params.put("municipio", municipio);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

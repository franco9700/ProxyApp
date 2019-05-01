package com.example.proxyapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText etnombre, etnum_control, etemail, ettelefono, etescuela, etsemestre, etestado, etmunicipio, etpassword;
    Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etnombre = findViewById(R.id.txt_nombre);
        etnum_control = findViewById(R.id.txt_num_control);
        etemail = findViewById(R.id.txt_email);
        ettelefono = findViewById(R.id.txt_telefono);
        etescuela = findViewById(R.id.txt_escuela);
        etsemestre = findViewById(R.id.txt_semestre);
        etestado = findViewById(R.id.txt_estado);
        etmunicipio = findViewById(R.id.txt_municipio);
        etpassword = findViewById(R.id.txt_password);

        btn_reg = findViewById(R.id.btn_registrar);

        btn_reg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        final String nombre = etnombre.getText().toString();
        final String num_control = etnum_control.getText().toString();
        final String email = etemail.getText().toString();
        final String telefono = ettelefono.getText().toString();
        final String escuela = etescuela.getText().toString();
        final String semestre = etsemestre.getText().toString();
        final String estado = etestado.getText().toString();
        final String municipio = etmunicipio.getText().toString();
        final String password = etpassword.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonReponse = new JSONObject(response);
                    boolean success = jsonReponse.getBoolean("success");

                    if (success){
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        Registro.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                        builder.setMessage("Error al registrar usuario: Intente de nuevo")
                                .setNegativeButton("Retry", null)
                                .create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        RegisterRequest registerRequest = new RegisterRequest(nombre, num_control, email, telefono,
                escuela, semestre, estado, municipio, password, respoListener);

        RequestQueue queue = Volley.newRequestQueue(Registro.this);
        queue.add(registerRequest);

    }
}

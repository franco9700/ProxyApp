package com.example.proxyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Asistente extends AppCompatActivity {

    TextView tvnombre, tvnum_control, tvemail, tvpassword, tvtelefono, tvescuela, tvsemestre, tvestado, tvmunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente);

        tvnombre = findViewById(R.id.tv_nombre);
        tvnum_control = findViewById(R.id.tv_num_control);
        tvemail = findViewById(R.id.tv_email);
        tvpassword = findViewById(R.id.tv_password);
        tvtelefono = findViewById(R.id.tv_telefono);
        tvescuela = findViewById(R.id.tv_escuela);
        tvsemestre = findViewById(R.id.tv_semestre);
        tvestado = findViewById(R.id.tv_estado);
        tvmunicipio = findViewById(R.id.tv_municipio);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String num_control = intent.getStringExtra("num_control");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String telefono = intent.getStringExtra("telefono");
        String escuela = intent.getStringExtra("escuela");
        String semestre = intent.getStringExtra("semestre");
        String estado = intent.getStringExtra("estado");
        String municipio = intent.getStringExtra("municipio");

        tvnombre.setText(nombre);
        tvnum_control.setText(num_control);
        tvemail.setText(email);
        tvpassword.setText(password);
        tvtelefono.setText(telefono);
        tvsemestre.setText(semestre);
        tvescuela.setText(escuela);
        tvestado.setText(estado);
        tvmunicipio.setText(municipio);
    }
}

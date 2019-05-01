package com.example.proxyapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_registrar;
    Button btn_iniciar;
    EditText etemail;
    EditText etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_registrar = findViewById(R.id.tv_reg);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        etemail = findViewById(R.id.email_login);
        etpassword = findViewById(R.id.password_login);

        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etemail.getText().toString();
                final String password = etpassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse (String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                String nombre = jsonResponse.getString("nombre");
                                String num_control = jsonResponse.getString("num_control");
                                String telefono = jsonResponse.getString("telefono");
                                String escuela = jsonResponse.getString("escuela");
                                String semestre = jsonResponse.getString("semestre");
                                String estado = jsonResponse.getString("estado");
                                String municipio = jsonResponse.getString("municipio");

                                Intent intent = new Intent(MainActivity.this, MenuLateral.class);
                                intent.putExtra("nombre", nombre);
                                intent.putExtra("num_control", num_control);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                intent.putExtra("telefono", telefono);
                                intent.putExtra("escuela", escuela);
                                intent.putExtra("semestre", semestre);
                                intent.putExtra("estado", estado);
                                intent.putExtra("municipio", municipio);

                                MainActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

    }
}

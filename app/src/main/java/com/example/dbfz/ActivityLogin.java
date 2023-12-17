package com.example.dbfz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class ActivityLogin extends AppCompatActivity{
    EditText username, password,pers;
    Button btnlogin,btnBack,btnPers;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = (EditText) findViewById(R.id.nombreLogin);
        password = (EditText) findViewById(R.id.contraLogin);
        pers = (EditText) findViewById(R.id.nombrePersFav);
        btnlogin = (Button) findViewById(R.id.btnLogueo);
        btnBack = (Button) findViewById(R.id.btnRegistro);
        btnPers = (Button) findViewById(R.id.btnGuardarPers);

        DB = new DBHelper(this);
        String nombrePersonaje = loadPersonaje();
        pers.setText(nombrePersonaje);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.isEmpty()||pass.isEmpty())
                    Toast.makeText(ActivityLogin.this, "Ingrese todos los datos para iniciar sesion", Toast.LENGTH_LONG).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    Intent intent = new Intent(getApplicationContext(), PersonajesActivity.class);
                    if (checkuserpass) {
                        Toast.makeText(ActivityLogin.this, "Datos ingresados correctamente usuario", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }else {
                        Toast.makeText(ActivityLogin.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContra = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intentContra);
            }
        });
        btnPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String personaje = pers.getText().toString();
                if (!personaje.isEmpty()) {
                    savePersonaje(personaje);
                    Toast.makeText(ActivityLogin.this, "Nombre del personaje guardado: " + personaje, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityLogin.this, "Ingrese el nombre del personaje antes de guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePersonaje(String personaje) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", ActivityLogin.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("personaje", personaje);
        editor.apply();
    }

    private String loadPersonaje() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", ActivityLogin.MODE_PRIVATE);
        return sharedPreferences.getString("personaje", "");
    }
}


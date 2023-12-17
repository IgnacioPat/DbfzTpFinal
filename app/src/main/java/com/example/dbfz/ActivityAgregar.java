package com.example.dbfz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAgregar extends AppCompatActivity {

    Button add;
    FloatingActionButton volver;
    EditText Nombre,Descripcion,Disponibilidad,Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        Descripcion = (EditText) findViewById(R.id.addDescripcion);
        Disponibilidad = (EditText) findViewById(R.id.addDispon);
        Nombre = (EditText) findViewById(R.id.addNombre);
        Img = (EditText) findViewById(R.id.addImg);

        add = (Button) findViewById(R.id.btnAñadir);
        volver = (FloatingActionButton) findViewById(R.id.btnVolver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAgregar.this, PersonajesActivity.class);
                startActivity(intent);
                //finish(); crashea
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Nombre.getText().length() < 2 || Disponibilidad.getText().toString().isEmpty() ||
                        Descripcion.getText().toString().isEmpty() || Img.getText().toString().isEmpty()){
                    Snackbar.make(findViewById(android.R.id.content), "Completa todos los campos para añadir un personaje", Snackbar.LENGTH_SHORT).show();
                }else{
                    AgregarPers();
                    limpiarTxt();
                }


            }
        });

    }

        private void AgregarPers(){
            Map<String,Object> map = new HashMap<>();
            map.put("Nombre",Nombre.getText().toString());
            map.put("Disponibilidad",Disponibilidad.getText().toString());
            map.put("Descripcion",Descripcion.getText().toString());
            map.put("PersUrl",Img.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("Personaje").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Snackbar.make(findViewById(android.R.id.content), "Operación exitosa", Snackbar.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(findViewById(android.R.id.content), "La operación fallo", Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
        private void limpiarTxt(){
            Nombre.setText("");
            Disponibilidad.setText("");
            Descripcion.setText("");
            Img.setText("");
        }
}
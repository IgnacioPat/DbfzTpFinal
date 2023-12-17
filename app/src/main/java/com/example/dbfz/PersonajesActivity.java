package com.example.dbfz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class PersonajesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdaptadorPrincipal adaptadorPrincipal;
    FloatingActionButton add,atras;


    @Override
    protected void onStart() {
        super.onStart();
        adaptadorPrincipal.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorPrincipal.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);
        recyclerView = findViewById(R.id.Rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ModeloPrincipal> options =
                new FirebaseRecyclerOptions.Builder<ModeloPrincipal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Personaje"), ModeloPrincipal.class)
                        .build();

        adaptadorPrincipal = new AdaptadorPrincipal(options);
        recyclerView.setAdapter(adaptadorPrincipal);

        atras= (FloatingActionButton) findViewById(R.id.btnAtras);
        add= (FloatingActionButton) findViewById(R.id.btnAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonajesActivity.this, ActivityAgregar.class);
                startActivity(intent);
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonajesActivity.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

    }
   private void txtBusqueda(String txt){
        FirebaseRecyclerOptions<ModeloPrincipal> options =
                new FirebaseRecyclerOptions.Builder<ModeloPrincipal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Personaje").orderByChild("Nombre").startAt(txt).endAt(txt+"'"), ModeloPrincipal.class)
                        .build();

        adaptadorPrincipal = new AdaptadorPrincipal(options);
        adaptadorPrincipal.startListening();
        recyclerView.setAdapter(adaptadorPrincipal);

    }
}

// Button btnBuscar;
// EditText nombrePers;


//btnBuscar = (Button) findViewById(R.id.btnBuscar);
//nombrePers = (EditText) findViewById(R.id.nombrePers);


      /*  btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBusqueda(nombrePers.toString());
            }
        });
*/



   /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.busqueda,menu);
        MenuItem item = menu.findItem(R.id.busqueda);
        SearchView Searchview = (SearchView) item.getActionView();
        Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
            public boolean onQueryTextSubmit(String query) {
                txtBusqueda(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtBusqueda(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/
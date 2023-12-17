package com.example.dbfz;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorPrincipal extends FirebaseRecyclerAdapter<ModeloPrincipal,AdaptadorPrincipal.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorPrincipal(@NonNull FirebaseRecyclerOptions<ModeloPrincipal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull ModeloPrincipal model) {

        holder.Descripcion.setText(model.getDescripcion());
        holder.Disponibilidad.setText(model.getDisponibilidad());
        holder.Nombre.setText(model.getNombre());
        Glide.with(holder.img.getContext())
                .load(model.getPersUrl())
                .placeholder(R.drawable.gokucarachica)
                .circleCrop()
                .error(R.drawable.gokucarachica)
                .into(holder.img);


    holder.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                    .setContentHolder(new ViewHolder(R.layout.update_ventana))
                    .setExpanded(true, 2000)
                    .create();


            View view = dialogPlus.getHolderView();

            EditText Descripcion = view.findViewById(R.id.editDescripcion);
            EditText Disponibilidad = view.findViewById(R.id.editDispon);
            EditText Nombre = view.findViewById(R.id.editNombre);
            EditText PersUrl = view.findViewById(R.id.editImg);

            Button btnUpdate = view.findViewById(R.id.btnUpdate);

            Descripcion.setText(model.getDescripcion());
            Disponibilidad.setText(model.getDisponibilidad());
            Nombre.setText(model.getNombre());
            PersUrl.setText(model.getPersUrl());

            dialogPlus.show();


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("Nombre",Nombre.getText().toString());
                    map.put("Disponibilidad",Disponibilidad.getText().toString());
                    map.put("Descripcion",Descripcion.getText().toString());
                    map.put("PersUrl",PersUrl.getText().toString());


                    FirebaseDatabase.getInstance().getReference().child("Personaje")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Snackbar.make(view, "Operación exitosa", Snackbar.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(view, "La operación fallo", Snackbar.LENGTH_SHORT).show();
                                }
                            });

                }


            });
        }
    });

    holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.Nombre.getContext());
            builder.setTitle("Vas a eliminar este personaje de la lista");
            builder.setMessage("¿Borrar personaje?");
            builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("Personaje")
                            .child(getRef(position).getKey()).removeValue();
                    Snackbar.make(v, "Se elimino el personaje", Snackbar.LENGTH_SHORT).show();

                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(v, "No se borro el personaje", Snackbar.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.objeto_principal,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView Nombre,Descripcion,Disponibilidad;

        Button btnEdit,btnBorrar;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Descripcion = (TextView) itemView.findViewById(R.id.txtDesc);
            Disponibilidad = (TextView) itemView.findViewById(R.id.txtDispon);
            Nombre = (TextView) itemView.findViewById(R.id.txtNombre);
            img = (CircleImageView) itemView.findViewById(R.id.img1);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnBorrar = (Button)itemView.findViewById(R.id.btnBorrar);


        }
    }
}

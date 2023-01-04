package com.example.gopayments.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.gopayments.Cartao;
import com.example.gopayments.EnviarPagamento;
import com.example.gopayments.R;
import com.example.gopayments.adapter.ContatosAdapter;
import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityListaContatos extends AppCompatActivity {
    private RecyclerView recyclerViewContatos;
    private ContatosAdapter adapter;
    private ArrayList <Usuario> listaContatos = new ArrayList<>();
    private DatabaseReference usuariosRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
    private ValueEventListener valueEventListener;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);
        recyclerViewContatos = findViewById(R.id.listaContatos);
        adapter = new ContatosAdapter(listaContatos, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewContatos.setLayoutManager(layoutManager);
        recyclerViewContatos.setHasFixedSize(true);
        recyclerViewContatos.setAdapter(adapter);
        recyclerViewContatos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewContatos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Usuario usuarioSelecionado = listaContatos.get(position);
                                Intent i = new Intent(getApplicationContext(), EnviarPagamento.class);
                                i.putExtra("userSelecionado",usuarioSelecionado);
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        recuperaContatos();
    }


    @Override
    protected void onStop() {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListener);
    }

    public void recuperaContatos(){
        valueEventListener = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dados : snapshot.getChildren() ){
                    Usuario usuario = dados.getValue(Usuario.class);
                    String EmailRecebido = autenticacao.getCurrentUser().getEmail();
                   if (!EmailRecebido.equals(usuario.getEmail())){
                       listaContatos.add(usuario);
                   }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
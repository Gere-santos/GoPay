package com.example.gopayments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.gopayments.adapter.ExtratoAdapter;
import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.DataCustom;
import com.example.gopayments.helper.GeradorIUD;
import com.example.gopayments.model.Transacao;
import com.example.gopayments.model.TrasancaoEnviada;
import com.example.gopayments.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExtratoActivity<receitaTotal> extends AppCompatActivity {
    private RecyclerView recyclerViewExtrato;
    private ExtratoAdapter adapter;
    private ArrayList<Transacao> listaTransacoes = new ArrayList<>();
    private ArrayList<TrasancaoEnviada> listaTransacoesEnviadas = new ArrayList<>();
    private DatabaseReference trasacoesRef = ConfiguracaoFireBase.getFirebaseDatabase().child("transacoes")
            .child(GeradorIUD.geradorId())
            .child(DataCustom.mesAnoDataEscolhida(DataCustom.dataAtual()));
    private ValueEventListener valueEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        recyclerViewExtrato = findViewById(R.id.recyclerViewExtrato);
        adapter = new ExtratoAdapter(listaTransacoes, listaTransacoesEnviadas, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewExtrato.setLayoutManager(layoutManager);
        recyclerViewExtrato.setHasFixedSize(true);
        recyclerViewExtrato.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewExtrato.setLayoutManager(linearLayoutManager);
        recuperaValoresTransacoes();
    }

    public void recuperaValoresTransacoes(){
        valueEventListener = trasacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dados : snapshot.getChildren() ){
                    Transacao transacoes = dados.getValue(Transacao.class);
                    TrasancaoEnviada transacoesEnviada = dados.getValue(TrasancaoEnviada.class);
                    transacoes.setUid(dados.getKey());
                    listaTransacoes.add(transacoes);
                    listaTransacoesEnviadas.add(transacoesEnviada);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        trasacoesRef.removeEventListener(valueEventListener);
    }
}
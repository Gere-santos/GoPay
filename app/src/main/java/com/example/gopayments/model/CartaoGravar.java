package com.example.gopayments.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.GeradorIUD;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

public class CartaoGravar {
    private String numeroCartao;
    private String numeroValidade;
    private String numeroCvv;
    private String nomeTitular;
    private String numeroCpf;
    private String apelidoCartao;
    private String uid = GeradorIUD.geradorId();


    public CartaoGravar() {
    }

    public void SalvarCartao(){

        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("cartao")
                .child(this.uid)
                .push()
                .setValue(this);
    }
    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroValidade() {
        return numeroValidade;
    }

    public void setNumeroValidade(String numeroValidade) {
        this.numeroValidade = numeroValidade;
    }

    public String getNumeroCvv() {
        return numeroCvv;
    }

    public void setNumeroCvv(String numeroCvv) {
        this.numeroCvv = numeroCvv;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumeroCpf() {
        return numeroCpf;
    }

    public void setNumeroCpf(String numeroCpf) {
        this.numeroCpf = numeroCpf;
    }

    public String getApelidoCartao() {
        return apelidoCartao;
    }

    public void setApelidoCartao(String apelidoCartao) {
        this.apelidoCartao = apelidoCartao;
    }


}

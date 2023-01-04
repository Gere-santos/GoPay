package com.example.gopayments.model;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.DataCustom;
import com.example.gopayments.helper.GeradorIUD;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Transacao {
    private String recargaCelular;
    private String recargaBilhete;
    private String enviodePagamentos;
    private String Uid = GeradorIUD.geradorId();
    private String data;
    private String descricao;
    private String tipo;
    private String valor;



    public void Salvar(){
        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("transacoes")
                .child(this.Uid)
                .child(DataCustom.mesAnoDataEscolhida(DataCustom.dataAtual()))
                .push()
                .setValue(this);
    }

    public Transacao() {
    }

    @Exclude
    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getRecargaCelular() {
        return recargaCelular;
    }

    public void setRecargaCelular(String recargaCelular) {
        this.recargaCelular = recargaCelular;
    }

    public String getRecargaBilhete() {
        return recargaBilhete;
    }

    public void setRecargaBilhete(String recargaBilhete) {
        this.recargaBilhete = recargaBilhete;
    }

    public String getEnviodePagamentos() {
        return enviodePagamentos;
    }

    public void setEnviodePagamentos(String enviodePagamentos) {
        this.enviodePagamentos = enviodePagamentos;
    }
}

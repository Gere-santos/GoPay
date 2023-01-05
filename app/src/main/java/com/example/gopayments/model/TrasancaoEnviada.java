package com.example.gopayments.model;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.DataCustom;
import com.google.firebase.database.DatabaseReference;

public class TrasancaoEnviada {

    private String data;
    private String descricao;
    private String tipo;
    private String valor;
    private String UidRecebedor;

    public void SalvarEnvio(){

        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("transacoes")
                .child(this.UidRecebedor)
                .child(DataCustom.mesAnoDataEscolhida(DataCustom.dataAtual()))
                .push()
                .setValue(this);


    }

    public TrasancaoEnviada() {
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

    public String getUidRecebedor() {
        return UidRecebedor;
    }

    public void setUidRecebedor(String uidRecebedor) {
        UidRecebedor = uidRecebedor;
    }
}

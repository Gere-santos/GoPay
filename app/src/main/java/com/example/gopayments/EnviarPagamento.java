package com.example.gopayments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.DataCustom;
import com.example.gopayments.model.Transacao;
import com.example.gopayments.model.TrasancaoEnviada;
import com.example.gopayments.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EnviarPagamento extends AppCompatActivity {
    private TextView textViewNomeEnviar, textViewTelEnviar;
    private Usuario usuarioRecebedor;
    private EditText editTextValorPagamento;
    private Button buttonEnviarPagamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_pagamento);
        textViewNomeEnviar = findViewById(R.id.textViewNomeEnviar);
        textViewTelEnviar = findViewById(R.id.textViewTelEnviar);
        editTextValorPagamento = findViewById(R.id.editTextValorPagamento);
        buttonEnviarPagamento = findViewById(R.id.buttonRecargaTel);

buttonEnviarPagamento.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String textViewNomeEnviarStr = textViewNomeEnviar.getText().toString();
        String editTextValorPagamentoStr = editTextValorPagamento.getText().toString();
        if (!textViewNomeEnviarStr.isEmpty() && !editTextValorPagamentoStr.isEmpty()){
            Transacao transacoes = new Transacao();
            transacoes.setEnviodePagamentos(textViewNomeEnviarStr);
            transacoes.setData(DataCustom.dataeHoraAtual());
            transacoes.setDescricao("Envio de pagamento");
            transacoes.setTipo("envioPagamento");
            transacoes.setValor(editTextValorPagamentoStr);


           TrasancaoEnviada trasancaoEnviada = new TrasancaoEnviada();
            trasancaoEnviada.setUidRecebedor( usuarioRecebedor.getUid());
            trasancaoEnviada.setData(DataCustom.dataeHoraAtual());
            trasancaoEnviada.setDescricao("Recebimento de pagamento");
            trasancaoEnviada.setTipo("recebimentoPagamento");
            trasancaoEnviada.setValor(editTextValorPagamentoStr);
            transacoes.Salvar();
            trasancaoEnviada.SalvarEnvio();
            startActivity(new Intent(getApplicationContext(), ActivityMain.class));

        }else{
            Toast.makeText(getApplicationContext(), "Digite o valor a ser enviado!",
                    Toast.LENGTH_SHORT).show();
        }

    }
});
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            usuarioRecebedor = (Usuario) bundle.getSerializable("userSelecionado");

            textViewNomeEnviar.setText("Nome: " + usuarioRecebedor.getNome());
            textViewTelEnviar.setText("Telefone: " + usuarioRecebedor.getTelefone());
        }
    }
}




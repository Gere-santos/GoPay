package com.example.gopayments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopayments.model.CartaoGravar;

public class Cartao extends AppCompatActivity {
    private Button buttonSalvaCard;
    private TextView cartaoTitulo;
    private EditText editTextApelidoCard, editTextCpf, editTextNomett,
            editTextValidade, editTextCvv, editTextNcartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);

        buttonSalvaCard = findViewById(R.id.buttonSalvaCard);
        editTextApelidoCard = findViewById(R.id.editTextApelidoCard);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextNomett = findViewById(R.id.editTextNomett);
        editTextValidade = findViewById(R.id.editTextValidade);
        editTextCvv = findViewById(R.id.editTextCvv);
        editTextNcartao = findViewById(R.id.editTextNcartao);

        buttonSalvaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String editTextCpfStg = editTextCpf.getText().toString();
               String editTextNomettStg = editTextNomett.getText().toString();
               String editTextCvvStg = editTextCvv.getText().toString();
               String editTextNcartaoStg = editTextNcartao.getText().toString();
               String editTextApelidoCardStg = editTextApelidoCard.getText().toString();
               String editTextValidadeStg = editTextValidade.getText().toString();
                     if (editTextNcartaoStg.intern() == "1111111111111111"){
                         if (editTextValidadeStg.intern() == "1225"){
                             if (editTextCvvStg.intern() == "111" ){
                                 if (editTextNomettStg.length() > 0 ){
                                     if (editTextCpfStg.intern() == "11111111111"){
                                         CartaoGravar cartaoGravar = new CartaoGravar();
                                         cartaoGravar.setNumeroCartao(editTextNcartaoStg);
                                         cartaoGravar.setNumeroCvv(editTextCvvStg);
                                         cartaoGravar.setNumeroValidade(editTextValidadeStg);
                                         cartaoGravar.setNomeTitular(editTextNomettStg);
                                         cartaoGravar.setNumeroCpf(editTextCpfStg);
                                         cartaoGravar.setApelidoCartao(editTextApelidoCardStg);

                                         cartaoGravar.SalvarCartao();
                                         Toast.makeText(Cartao.this, "Cartão cadastrado com sucesso", Toast.LENGTH_LONG)
                                                 .show();
                                         finish();
                                         overridePendingTransition(1, android.R.anim.fade_out);


                                     }else{
                                         Toast.makeText(Cartao.this, "Número de CPF inválido", Toast.LENGTH_SHORT).show();
                                     }

                                 }else{
                                     Toast.makeText(Cartao.this, "Preencha o campo nome ", Toast.LENGTH_SHORT).show();
                                 }

                             }else{
                                 Toast.makeText(Cartao.this, "Número de CVV inválido", Toast.LENGTH_SHORT).show();
                             }

                         }else {
                             Toast.makeText(Cartao.this, "Número de validade inválido", Toast.LENGTH_SHORT).show();
                         }
                     }
                     else {
                         Toast.makeText(Cartao.this, "Número de cartão inválido", Toast.LENGTH_SHORT).show();
                     }
            }
        });
    }
}
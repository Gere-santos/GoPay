package com.example.gopayments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gopayments.helper.DataCustom;
import com.example.gopayments.model.Transacao;

public class RecargaBilhete extends AppCompatActivity {
    private EditText editTextNbilhete, editTextValorRecargaBilhete;
    private Button buttonRecargaBilhete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_bilhete);
        editTextNbilhete = findViewById(R.id.editTextNbilhete);
        editTextValorRecargaBilhete = findViewById(R.id.editTextValorRecargaBilhete);
        buttonRecargaBilhete = findViewById(R.id.buttonRecargaTel);

        buttonRecargaBilhete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String editTextValorRecargaBilheteStg = editTextValorRecargaBilhete.getText().toString();
            String editTextNbilheteStr = editTextNbilhete.getText().toString();

            if (!editTextValorRecargaBilheteStg.isEmpty() && !editTextNbilheteStr.isEmpty()){
                Transacao transacoes = new Transacao();
                transacoes.setValor(editTextValorRecargaBilheteStg);
                transacoes.setData(DataCustom.dataeHoraAtual());
                transacoes.setDescricao("Recarga de bilhete único");
                transacoes.setTipo("recargaBilhete");
                transacoes.Salvar();
                startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                finish();

            }else{
                Toast.makeText(getApplicationContext(), "Preencha todos os campos!",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}
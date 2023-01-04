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

public class RecargaCelular extends AppCompatActivity {
    private EditText editTextTelRecarga, editTextValorRecarga;
    private Button buttonRecargaTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga_celular);
        editTextTelRecarga = findViewById(R.id.editTextTelRecarga);
        buttonRecargaTel = findViewById(R.id.buttonRecargaTel);
        editTextValorRecarga = findViewById(R.id.editTextValorRecarga);

        buttonRecargaTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextTelRecargaStr = editTextTelRecarga.getText().toString();
                String editTextValorRecargaStr = editTextValorRecarga.getText().toString();
                if (!editTextTelRecargaStr.isEmpty() && !editTextValorRecargaStr.isEmpty()){
                    Transacao transacoes = new Transacao();
                    transacoes.setValor(editTextValorRecargaStr);
                    transacoes.setData(DataCustom.dataeHoraAtual());
                    transacoes.setDescricao("Recarga celular");
                    transacoes.setTipo("recargaCelular");
                    transacoes.Salvar();

                    startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha o campo telefone!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
      // editTextValorRecarga.addTextChangedListener(new MoneyTextWatcher(editTextValorRecarga));


    }
}
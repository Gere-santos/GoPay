package com.example.gopayments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.DataCustom;
import com.example.gopayments.helper.GeradorIUD;
import com.example.gopayments.helper.MascaraMonetaria;
import com.example.gopayments.model.ActivityListaContatos;
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
import java.util.List;

public class ActivityMain extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private TextView textBoasVindas, textViewSaldoConta;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Transacao> listaTransacoes = new ArrayList<>();
    private DatabaseReference usuariosRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
    private Double receitaGerada;
    private Double despesaGerada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textBoasVindas = findViewById(R.id.textBoasVindas);
        textViewSaldoConta = findViewById(R.id.textViewSaldoConta);
        recuperaValoresTransacoes();
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("GoPay");
        toolbar.setTitleTextColor(Color.WHITE);


}

public void verificaUserLogado(){
 autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

 if(autenticacao.getCurrentUser() == null){

     abrirTelaLogin();
 }

}


public void abrirTelaLogin(){
        startActivity(new Intent(this, AcitivityLogin.class));
}

public void cadastrarCartao(View view){
    startActivity(new Intent(this, Cartao.class));


}

    @Override
    protected void onStart() {
        super.onStart();
        verificaUserLogado();
        recuperarSaldo();
    }

    public void pagarPessoas(View view){
        startActivity(new Intent(this, ActivityListaContatos.class));
    }
    public void RecargaCel(View view){
        startActivity(new Intent(this, RecargaCelular.class));
    }

    public void RecarBilhete(View view){
        startActivity(new Intent(this, RecargaBilhete.class));
    }

    public void abrirExtrato(View view){
        startActivity(new Intent(this, ExtratoActivity.class));
    }

    public void abrirEditarPerfil(View view){
        startActivity(new Intent(this, CriarPerfilActivity.class));
    }

    public void recuperarSaldo() {
        if (user != null) {
            DatabaseReference usuariosRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios")
                    .child(user.getUid());
                usuariosRef.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    textBoasVindas.setText("Olá " + usuario.getNome().toUpperCase() + "sejá bem vindo!");
                    Double SaldoTratado = usuario.getReceitaTotal() - usuario.getDespesaTotal();
                  if(SaldoTratado > 0) {
                      textViewSaldoConta.setText("R$ " + MascaraMonetaria.adiconarMascara(SaldoTratado));
                  }else{
                      textViewSaldoConta.setText("R$ 00.00");
                  }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void recuperaValoresTransacoes() {
        if (user != null) {
            DatabaseReference trasacoesRef = ConfiguracaoFireBase.getFirebaseDatabase().child("transacoes").child(GeradorIUD.geradorId())
                    .child(DataCustom.mesAnoDataEscolhida(DataCustom.dataAtual()));
           trasacoesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listaTransacoes.clear();
                    for (DataSnapshot dados : snapshot.getChildren()) {
                        Transacao transacoes = dados.getValue(Transacao.class);
                        transacoes.setUid(dados.getKey());
                        listaTransacoes.add(transacoes);

                    }

                    Double cont = 0.00;
                    Double acumuladora = 0.00;
                    for (int i = 0; i < listaTransacoes.size(); i++) {
                        if (listaTransacoes.get(i).getDescricao() != "Recebimento de pagamento" &&
                                !listaTransacoes.get(i).getDescricao().equals("Recebimento de pagamento")) {
                            despesaGerada = Double.valueOf(listaTransacoes.get(i).getValor());
                            Double resultadoDespesa = acumuladora += despesaGerada;
                            AtualizarDespesa(resultadoDespesa);
                        } else {
                            receitaGerada = Double.valueOf(listaTransacoes.get(i).getValor());
                            Double resultadoReceita = cont += receitaGerada;
                            AtualizarReceita(resultadoReceita);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void AtualizarDespesa(Double valorRecebidoTotal){

        usuariosRef.child(user.getUid()).child("despesaTotal")
                .setValue(valorRecebidoTotal);

    }
    public void AtualizarReceita(Double valorRecebidoTotal){
        usuariosRef.child(user.getUid()).child("receitaTotal")
                .setValue(valorRecebidoTotal);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSair) {
            autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
            autenticacao.signOut();
            startActivity(new Intent(this, AcitivityLogin.class));
            finish();
            return true;
        }
        else if (id == R.id.menuConfiguracoes) {
            startActivity(new Intent(this, CriarPerfilActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

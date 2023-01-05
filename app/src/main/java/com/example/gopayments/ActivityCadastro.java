package com.example.gopayments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ActivityCadastro extends AppCompatActivity {
    private EditText editTextNome, editTextEmail, editTextPassword, editTextTelefone;
    private Button buttonCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCadastrar = findViewById(R.id.buttonLogar);
        editTextTelefone = findViewById(R.id.editTextTelefone);

    buttonCadastrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           String editTextTelefoneString = editTextTelefone.getText().toString();
           String editTextNomeString = editTextNome.getText().toString();
           String editTextEmailString = editTextEmail.getText().toString();
           String editTextPasswordString = editTextPassword.getText().toString();
           if(!editTextNomeString.isEmpty() && !editTextEmailString.isEmpty() &&
                   !editTextPasswordString.isEmpty() && !editTextTelefoneString.isEmpty()){
               usuario = new Usuario();
               usuario.setNome(editTextNomeString);
               usuario.setEmail(editTextEmailString);
               usuario.setSenha(editTextPasswordString);
               usuario.setTelefone(editTextTelefoneString);
               cadastrarUser();
           }else{
               Toast.makeText(ActivityCadastro.this, "Preencha todos os campos"
                       , Toast.LENGTH_SHORT).show();
           }

        }
    });
    }

public void cadastrarUser(){
    autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
    autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
    ).addOnCompleteListener(ActivityCadastro.this, new OnCompleteListener<AuthResult>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                gerarId();
               ConfiguracaoFireBase.atualizarNome(usuario.getNome());
                finish();

            }else{
                String excecao = "";
                try{
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e ){
                    excecao = "Digite uma senha mais forte!";
                }catch (FirebaseAuthInvalidCredentialsException e ){
                    excecao = "Digite uma E-mail válido!";
                }catch (FirebaseAuthUserCollisionException e){
                    excecao = "Essa conta já foi cadastrada!";
                }catch ( Exception e){
                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), excecao,
                        Toast.LENGTH_SHORT).show();
            }
        }
    });
}

public void gerarId(){

    autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
    if(autenticacao.getCurrentUser() != null){
        String UID = autenticacao.getUid();
        usuario.setUid(UID);
        usuario.Salvar();
    }
}


}

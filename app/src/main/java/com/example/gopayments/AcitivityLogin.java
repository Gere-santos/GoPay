package com.example.gopayments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class AcitivityLogin extends AppCompatActivity {
     private EditText editTextEmail, editTextPassword;
     private Button buttonLogar;
     private FirebaseAuth autenticacao;
     private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogar = findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String editTextEmailString = editTextEmail.getText().toString();
               String editTextPasswordString = editTextPassword.getText().toString();
               if(!editTextEmailString.isEmpty() && !editTextPasswordString.isEmpty()){
                   usuario = new Usuario();
                   usuario.setEmail(editTextEmailString);
                   usuario.setSenha(editTextPasswordString);
                   logarUser();
               }else{
                   Toast.makeText(AcitivityLogin.this, "Preencha todos os campos",
                           Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

public void logarUser(){
    autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
    autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(), ActivityMain.class));
                    }else {
                        String excecao = "";
                        try{
                            throw task.getException();
                        }catch (FirebaseAuthInvalidUserException e){
                            excecao = "Usuário não cadastrado!";
                        }
                        catch (FirebaseAuthInvalidCredentialsException e){
                            excecao = "E-mail ou senha incorreto!";
                        }
                        catch ( Exception e){
                            excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), excecao,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}
    public void abrirCadastro (View view){
        startActivity(new Intent(this, ActivityCadastro.class));
    }
}

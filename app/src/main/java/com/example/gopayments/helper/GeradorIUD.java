package com.example.gopayments.helper;

import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.model.CartaoGravar;
import com.example.gopayments.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class GeradorIUD {
    private static FirebaseAuth autenticacao;
    private Usuario usuario;
    private CartaoGravar cartaoGravar;

    public static String geradorId(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();

        if(autenticacao.getCurrentUser() != null){
            String uid = autenticacao.getUid();

            return uid;
        }

        return null;
    }
}

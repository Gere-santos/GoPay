package com.example.gopayments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.Permissao;
import com.example.gopayments.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class CriarPerfilActivity extends AppCompatActivity {

    private String [] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private ImageButton camera, galeria;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView profile_image;
    private StorageReference storageReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private EditText editTextNomePerfil, editTextTelefone, editTextApelido;
    private Button button;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil);
        Toolbar toolbar = findViewById(R.id.my_toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Configurações");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        camera = findViewById(R.id.imageButtonCamera);
        galeria = findViewById(R.id.imageButtonGaleria);
        profile_image = findViewById(R.id.profile_image);
        editTextNomePerfil = findViewById(R.id.editTextNomePerfil);
        button = findViewById(R.id.button);
        editTextTelefone = findViewById(R.id.editTextTelefonePerfil);
        editTextApelido = findViewById(R.id.editTextApelido);


        FirebaseUser firebaseUser = ConfiguracaoFireBase.getFirebaseUser();
        Uri url = firebaseUser.getPhotoUrl();

        if (url != null){
            Glide.with(CriarPerfilActivity.this)
            .load(url)
                    .into(profile_image);
        }else{
            profile_image.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        editTextNomePerfil.setText(firebaseUser.getDisplayName());
        storageReference = ConfiguracaoFireBase.getFirebaseStorage();

        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(i.resolveActivity(getPackageManager())!= null){
                startActivityForResult(i, SELECAO_CAMERA); }
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if(i.resolveActivity(getPackageManager())!= null){
                    startActivityForResult(i, SELECAO_GALERIA); }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextTelefoneString = editTextTelefone.getText().toString();
                String editTextApelidoString = editTextApelido.getText().toString();
                String editTextANomeString = editTextNomePerfil.getText().toString();

                if(!editTextApelidoString.isEmpty() && !editTextANomeString.isEmpty() &&
                         !editTextTelefoneString.isEmpty()){
                    usuario = new Usuario();
                    usuario.setNome(editTextANomeString);
                    ConfiguracaoFireBase.atualizarNome(usuario.getNome());
                    usuario.setTelefone(editTextTelefoneString);
                    usuario.setApelido(editTextApelidoString);
                    usuario.SalvarPerfil();
                    Toast.makeText(CriarPerfilActivity.this, "Alterações realizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(CriarPerfilActivity.this, "Preencha todos os campos"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {
                switch (requestCode){
                    case SELECAO_CAMERA:
                       imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }
                if (imagem != null){
                    profile_image.setImageBitmap(imagem);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();


                   final StorageReference imgRef = storageReference.child("imagens").child("perfil").child(user.getUid())
                            .child("perfil.jpg");
                    Log.i("", "");

                    UploadTask uploadTask = imgRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CriarPerfilActivity.this,
                                    "Erro ao fazer Upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CriarPerfilActivity.this,
                                    "Sucesso ao fazer Upload", Toast.LENGTH_SHORT).show();
                            imgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri url = task.getResult();
                                    atualizarFoto(url);
                                }
                            });
                        }
                    });
                }

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int pemissaoResultado : grantResults){
            if (pemissaoResultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }

    }
    private void alertaValidacaoPermissao (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void atualizarFoto(Uri url){
        ConfiguracaoFireBase.atualizarFotoUsuario(url);
    }
}
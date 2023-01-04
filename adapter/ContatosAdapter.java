package com.example.gopayments.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gopayments.R;
import com.example.gopayments.model.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder>{
     private List<Usuario> contatos;
     private Context context;
    public ContatosAdapter(List<Usuario> listaContatos, Context c) {
        this.contatos = listaContatos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contatos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Usuario usuario = contatos.get(position);
    holder.nome.setText(usuario.getNome());
    holder.telefone.setText(usuario.getTelefone());
    }

    @Override
    public int getItemCount() {

        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome, telefone;
        CircleImageView foto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            nome = itemView.findViewById(R.id.textNomeContato);
            telefone = itemView.findViewById(R.id.textTelefone);
        }
    }
}

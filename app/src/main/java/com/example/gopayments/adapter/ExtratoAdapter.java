package com.example.gopayments.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopayments.R;
import com.example.gopayments.config.ConfiguracaoFireBase;
import com.example.gopayments.helper.GeradorIUD;
import com.example.gopayments.helper.MascaraMonetaria;
import com.example.gopayments.model.Transacao;
import com.example.gopayments.model.TrasancaoEnviada;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExtratoAdapter extends RecyclerView.Adapter<ExtratoAdapter.MyViewHolder> {

    private List<Transacao> transacoes;
    private List<TrasancaoEnviada> transacoesEnviadas;
    private Context context;
    public ExtratoAdapter(ArrayList<Transacao> listaTransacoes, ArrayList<TrasancaoEnviada> transacoesEnviadas, Context c) {
        this.transacoes = listaTransacoes;
        this.transacoesEnviadas = transacoesEnviadas;
        this.context = c;
    }
    @NonNull
    @Override
    public ExtratoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_extrato, parent, false);
        return new ExtratoAdapter.MyViewHolder(itemLista);
    }
    @Override
    public void onBindViewHolder(@NonNull ExtratoAdapter.MyViewHolder holder, int position) {
        Transacao transacao = transacoes.get(position);
        TrasancaoEnviada trasancaoEnviada = transacoesEnviadas.get(position);
        holder.horario.setText(transacao.getData());
        holder.tipoTransacao.setText(transacao.getDescricao());
        holder.valor.setTextColor(context.getResources().getColor(R.color.colorRed));
        holder.valor.setText("R$ - " + MascaraMonetaria.adiconarMascara(Double.valueOf(transacao.getValor())));
        if(transacao.getDescricao() == "Recebimento de pagamento" ||
                transacao.getDescricao().equals("Recebimento de pagamento")){
            holder.valor.setTextColor(context.getResources().getColor(R.color.verdeprincipal));
            holder.valor.setText("R$ + " + MascaraMonetaria.adiconarMascara(Double.valueOf(transacao.getValor())));
            holder.horario.setText(trasancaoEnviada.getData());
        }
    }
    @Override
    public int getItemCount() {
        return transacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView horario, valor, tipoTransacao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.textAdapterValor);
            horario = itemView.findViewById(R.id.textAdapterHorario);
            tipoTransacao = itemView.findViewById(R.id.textAdapterTipo);
        }
    }
}

package br.com.ricardofelix.organizzeclone.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.model.UserMovementation;

public class MovimentationsAdapter extends RecyclerView.Adapter <MovimentationsAdapter.MyViewHolder> {

    List<UserMovementation> userMovementationList = new ArrayList<>();
    Context context;

    public MovimentationsAdapter(List<UserMovementation> movimentation,Context context){
        this.userMovementationList = movimentation;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movimentations,parent,false);


        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserMovementation movimentation = userMovementationList.get(position);

        holder.title.setText(movimentation.getDescription());
        holder.category.setText(movimentation.getCategory());
        holder.value.setText(String.valueOf(movimentation.getValue()));

        if(movimentation.getType().equals("d")){
            holder.value.setTextColor(context.getResources().getColor(R.color.colorPrimaryDarkDespesa));
            holder.value.setText("-"+movimentation.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return userMovementationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, value, category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textHomeTitle);
            value = itemView.findViewById(R.id.textHomeValue);
            category = itemView.findViewById(R.id.textHomeCategory);

        }
    }
}

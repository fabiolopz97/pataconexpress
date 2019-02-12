package com.pataconexpress.fastfood.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.models.Catalogo;

import java.util.List;

public class MyAdacterCatalogo extends RecyclerView.Adapter<MyAdacterCatalogo.ViewHolder> {
    private List<Catalogo> catalogos;
    private int layout;
    private OnItemClickListener listener;

    public MyAdacterCatalogo(List<Catalogo> catalogos, int layout, OnItemClickListener listener) {
        this.catalogos = catalogos;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(catalogos.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return catalogos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewGrid;

        public ViewHolder(View itemView){
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewGrid = (ImageView) itemView.findViewById(R.id.imageViewGrid);
            //itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        public void bind(final Catalogo catalogo, final OnItemClickListener listener) {
            this.textViewName.setText(catalogo.getNombre());
            this.imageViewGrid.setImageResource(catalogo.getImgBackground());
            //Picasso.with(activity.getContext()).load(catalogo.getImgBackground()).fit().into(this.imageViewGrid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(catalogo, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Catalogo catalogo, int position);
    }
}

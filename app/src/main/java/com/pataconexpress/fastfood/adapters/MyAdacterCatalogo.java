package com.pataconexpress.fastfood.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.models.CategoriaDTO;

import java.util.List;

public class MyAdacterCatalogo extends RecyclerView.Adapter<MyAdacterCatalogo.ViewHolder> {
    //private List<Catalogo> catalogos;
    private List<CategoriaDTO> categorias;
    private int layout;
    private OnItemClickListener listener;

    public MyAdacterCatalogo(List<CategoriaDTO> categorias, int layout, OnItemClickListener listener) {
        this.categorias = categorias;
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
        viewHolder.bind(categorias.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
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

        public void bind(final CategoriaDTO categoria, final OnItemClickListener listener) {
            this.textViewName.setText(categoria.getNombreCat());
            this.imageViewGrid.setImageResource(R.drawable.ic_menu_gallery);
            //Picasso.with(activity.getContext()).load(catalogo.getImgBackground()).fit().into(this.imageViewGrid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(categoria, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CategoriaDTO categoria, int position);
    }
}

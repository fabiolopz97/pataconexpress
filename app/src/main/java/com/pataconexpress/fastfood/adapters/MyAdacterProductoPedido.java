package com.pataconexpress.fastfood.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.models.ProductoPedido;

import java.util.List;

public class MyAdacterProductoPedido extends RecyclerView.Adapter<MyAdacterProductoPedido.ViewHolder> {
    private List<ProductoPedido> productosPedidos;
    private int layout;
    private MyAdacterProductoPedido.OnItemClickListener listener;

    public MyAdacterProductoPedido(List<ProductoPedido> productosPedidos, int layout, MyAdacterProductoPedido.OnItemClickListener listener) {
        this.productosPedidos = productosPedidos;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdacterProductoPedido.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        MyAdacterProductoPedido.ViewHolder viewHolder = new MyAdacterProductoPedido.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdacterProductoPedido.ViewHolder viewHolder, int i) {
        viewHolder.bind(productosPedidos.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return productosPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombre;
        public TextView textViewDescripcion;
        public TextView textViewTotal;
        public ImageView imageViewList;

        public ViewHolder(View itemView){
            super(itemView);
            this.textViewNombre = (TextView) itemView.findViewById(R.id.textViewNombreProd);
            this.textViewDescripcion = (TextView) itemView.findViewById(R.id.textViewDescripcionProd);
            this.textViewTotal = (TextView) itemView.findViewById(R.id.textViewTotalProd);
            this.imageViewList = (ImageView) itemView.findViewById(R.id.imageViewList);
            //itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        public void bind(final ProductoPedido productoPedido, final MyAdacterProductoPedido.OnItemClickListener listener) {
            this.textViewNombre.setText(productoPedido.getNombre());
            this.textViewDescripcion.setText(productoPedido.getDescripcion());
            this.textViewTotal.setText("Total: "+productoPedido.getValor());
            this.imageViewList.setImageResource(productoPedido.getImgBackground());
            //Picasso.with(activity.getContext()).load(catalogo.getImgBackground()).fit().into(this.imageViewGrid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(productoPedido, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ProductoPedido productoPedido, int position);
    }
}

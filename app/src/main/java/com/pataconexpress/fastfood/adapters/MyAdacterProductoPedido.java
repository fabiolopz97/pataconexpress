package com.pataconexpress.fastfood.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.models.ProductoPedido;

import java.util.List;

public class MyAdacterProductoPedido extends RecyclerView.Adapter<MyAdacterProductoPedido.ViewHolder> {
    private List<ProductoPedido> productosPedidos;
    private int layout;
    private Activity activity;
    private MyAdacterProductoPedido.OnItemClickListener listener;

    public MyAdacterProductoPedido(List<ProductoPedido> productosPedidos, int layout, Activity activity, MyAdacterProductoPedido.OnItemClickListener listener) {
        this.productosPedidos = productosPedidos;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdacterProductoPedido.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView textViewNombre;
        //public TextView textViewDescripcion;
        public TextView textViewMostrarContador;
        public TextView textViewPrecio;
        public TextView textViewTotal;
        public ImageView imageViewList;
        public ImageButton buttonIncrementar;
        public ImageButton buttonDecrementar;
        public int count;
        public int resultado;

        public ViewHolder(View itemView){
            super(itemView);
            this.textViewNombre = (TextView) itemView.findViewById(R.id.textViewNombreProd);
            //this.textViewDescripcion = (TextView) itemView.findViewById(R.id.textViewDescripcionProd);
            this.textViewPrecio = (TextView) itemView.findViewById(R.id.textViewPrecioProd);
            this.textViewTotal = (TextView) itemView.findViewById(R.id.textViewTotalProd);
            this.textViewMostrarContador = (TextView) itemView.findViewById(R.id.textViewMostrarContador);
            this.imageViewList = (ImageView) itemView.findViewById(R.id.imageViewList);
            this.buttonIncrementar = (ImageButton) itemView.findViewById(R.id.buttonIncrementar);
            this.buttonDecrementar = (ImageButton) itemView.findViewById(R.id.buttonDecrementar);
            count = 1;
            resultado = 0;
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final ProductoPedido productoPedido, final MyAdacterProductoPedido.OnItemClickListener listener) {
            this.textViewNombre.setText(productoPedido.getNombre());
            //this.textViewDescripcion.setText(productoPedido.getDescripcion());
            this.textViewPrecio.setText("Precio: $"+productoPedido.getValor());
            this.imageViewList.setImageResource(R.drawable.ic_menu_gallery);
            this.textViewTotal.setText("Total: $"+productoPedido.getValor());
            //Button incrementar contador
            this.buttonIncrementar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewMostrarContador.setText("" + (++count));
                    resultado = (int)productoPedido.getValor() * count;
                    textViewTotal.setText("Total: $"+resultado);
                }
            });
            //Button Decrementar contador
            this.buttonDecrementar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count > 1) {
                        textViewMostrarContador.setText("" + (--count));
                        resultado = (int)productoPedido.getValor() * count;
                        textViewTotal.setText("Total: $"+resultado);
                        productosPedidos.get(getAdapterPosition()).setTotalProducto(resultado);
                    }
                }
            });
            //Picasso.with(activity.getContext()).load(catalogo.getImgBackground()).fit().into(this.imageViewGrid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(productoPedido, getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Producto productoSelecionado = productosPedidos.get(this.getAdapterPosition());
            menu.setHeaderTitle(productoSelecionado.getNombre());
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_menu_delete, menu);
            menu.getItem(0).setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_product:
                    productosPedidos.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ProductoPedido productoPedido, int position);
    }
}

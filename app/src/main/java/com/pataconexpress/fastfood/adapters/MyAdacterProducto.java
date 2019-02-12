package com.pataconexpress.fastfood.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.activity.ProductoPedidoActivity;
import com.pataconexpress.fastfood.models.Producto;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdacterProducto extends RecyclerView.Adapter<MyAdacterProducto.ViewHolder> {
    private List<Producto> productos;
    private int layout;
    private Activity activity;
    private MyAdacterProducto.OnItemClickListener listener;

    public MyAdacterProducto(List<Producto> productos, int layout, Activity activity, MyAdacterProducto.OnItemClickListener listener) {
        this.productos = productos;
        this.layout = layout;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdacterProducto.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        MyAdacterProducto.ViewHolder viewHolder = new MyAdacterProducto.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(productos.get(i), listener);
    }


    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
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
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Producto producto, final MyAdacterProducto.OnItemClickListener listener) {
            this.textViewNombre.setText(producto.getNombre());
            this.textViewDescripcion.setText(producto.getDescripcion());
            this.textViewTotal.setText("Total: $"+producto.getValor());
            this.imageViewList.setImageResource(producto.getImgBackground());
            //Picasso.with(activity.getContext()).load(catalogo.getImgBackground()).fit().into(this.imageViewGrid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(producto, getAdapterPosition());
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Producto productoSelecionado = productos.get(this.getAdapterPosition());
            menu.setHeaderTitle(productoSelecionado.getNombre());
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            menu.getItem(0).setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.add_product:
                    Intent intent = new Intent(activity, ProductoPedidoActivity.class);
                    activity.startActivity(intent);
                    //productos.remove(getAdapterPosition());
                    //notifyItemRemoved(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Producto producto, int position);
    }
}

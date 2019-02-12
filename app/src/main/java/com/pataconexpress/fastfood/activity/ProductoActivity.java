package com.pataconexpress.fastfood.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pataconexpress.fastfood.adapters.MyAdacterProducto;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.R;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {
    // Atributos
    private Producto producto;
    private List<Producto> productos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        // Flecha de regreso
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Productos");

        productos = this.getAllProductos();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewListProducto);
        mLayoutManager = new LinearLayoutManager(ProductoActivity.this);
        mAdapter = new MyAdacterProducto(productos, R.layout.list_producto, ProductoActivity.this, new MyAdacterProducto.OnItemClickListener(){
            @Override
            public void onItemClick(Producto producto, int position) {
                Toast.makeText(ProductoActivity.this, "hola a", Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Producto> getAllProductos () {
        return new ArrayList<Producto>(){{
            add(new Producto("Nombre", "descripción", 100000, R.drawable.ic_launcher_background));
            add(new Producto("Nombre1", "descripción", 200000, R.drawable.ic_menu_camera));
            add(new Producto("Nombre2", "descripción", 200000, R.drawable.ic_menu_gallery));
            add(new Producto("Nombre3", "descripción", 100000, R.drawable.ic_menu_delete));
        }};
    }

    private void addProduct(int position) {
        productos.add(position, new Producto("hola", "descripción", 100000, R.drawable.ic_launcher_background));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }
    private void deleteProduct(int position) {
        productos.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}

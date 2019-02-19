package com.pataconexpress.fastfood.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.adapters.MyAdacterProducto;
import com.pataconexpress.fastfood.adapters.MyAdacterProductoPedido;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.models.ProductoPedido;
import com.pataconexpress.fastfood.utils.GsonImpl;

import java.util.ArrayList;
import java.util.List;

public class ProductoPedidoActivity extends AppCompatActivity {
    // Atributos
    private ProductoPedido productoPedido;
    private List<ProductoPedido> productosPedidos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_pedido);

        //Captura del id categoria
        Bundle bundle = getIntent().getExtras();

        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pedido");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Obtener valores
        Bundle seleccionados = getIntent().getExtras();
        if (seleccionados != null) {
            String rta = seleccionados.getString("seleccionados");
            productosPedidos = GsonImpl.listFromJsonV(rta, ProductoPedido.class);
            //Log.i("arreglo --> ", productosPedidos.get(0).getNombre());
            //productosPedidos = this.getAllProductosPedidos();
            //productosPedidos = new ArrayList<ProductoPedido>();
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewListProductoPedido);
            mLayoutManager = new LinearLayoutManager(ProductoPedidoActivity.this);
            mAdapter = new MyAdacterProductoPedido(productosPedidos, R.layout.list_producto_pedido, ProductoPedidoActivity.this, new MyAdacterProductoPedido.OnItemClickListener(){
                @Override
                public void onItemClick(ProductoPedido productoPedido, int position) {
                    //Toast.makeText(ProductoPedidoActivity.this, "hola a", Toast.LENGTH_LONG).show();
                }
            });
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(ProductoPedidoActivity.this, "El resultado esta vacío", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_product:
                this.addrequestProduct(0);
                return true;
            case R.id.cancel_product:
                return true;
            case R.id.check_product:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<ProductoPedido> getAllProductosPedidos () {
        return new ArrayList<ProductoPedido>(){{
            //add(new ProductoPedido("hola", "descripción", 100000, R.drawable.ic_menu_gallery, 0, 0,0));
            //add(new ProductoPedido("hola1", "descripción", 200000, R.drawable.ic_menu_gallery, 0, 0,0));
        }};
    }

    private void addrequestProduct(int position) {
        //productosPedidos.add(position, new ProductoPedido("hola", "descripción", 100000, R.drawable.ic_menu_gallery, 1, 2,3));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }
    private void deleterequestProduct(int position) {
        productosPedidos.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}

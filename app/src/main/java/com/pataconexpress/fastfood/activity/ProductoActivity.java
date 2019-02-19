package com.pataconexpress.fastfood.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pataconexpress.fastfood.adapters.MyAdacterProducto;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.utils.GsonImpl;
import com.pataconexpress.fastfood.utils.OkHttpImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ProductoActivity extends AppCompatActivity {
    // Atributos
    private Producto producto;
    private List<Producto> productos;
    private RecyclerView mRecyclerView;
    private MyAdacterProducto mAdapter;
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

        //Captura del id categoria
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int id = bundle.getInt("idCategoria");
            //Toast.makeText(ProductoActivity.this, ""+id, Toast.LENGTH_LONG).show();
            Request rq = new Request.Builder()
                    .url("http://" + getString(R.string.ip) + ":8080/" + getString(R.string.path) + "/api/productos/categoria/id/"+id)
                    .build();
            OkHttpImpl.newHttpCall(rq).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {


                    if(response.isSuccessful()){
                        final String rta = response.body().string();
                        productos = GsonImpl.listFromJsonV(rta, Producto.class);
                        Log.i("respuesta ->> ",rta);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    //List<Producto> pto = productos;
                                    //--Ingreso de datos en el recycleview
                                    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewListProducto);
                                    mLayoutManager = new LinearLayoutManager(ProductoActivity.this);
                                    //mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                                    mAdapter = new MyAdacterProducto(productos, R.layout.list_producto, ProductoActivity.this, new MyAdacterProducto.OnItemClickListener(){
                                        @Override
                                        public void onItemClick(Producto producto, int position) {
                                        }
                                    });
                                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);

                            }
                        });
                    }
                }
            });

        } else {
            Toast.makeText(ProductoActivity.this, "El resultado esta vacío", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.send_product:
               List<Producto>seleccion = mAdapter.getProductosSeleccionados();
               if(!seleccion.isEmpty()){
                 //  Log.i("seleccionados",seleccion.get(0).getNombre());
                   Intent intent = new Intent(this,ProductoPedidoActivity.class);
                   intent.putExtra("seleccionados",GsonImpl.objectToJSon(seleccion));
                   startActivity(intent);
                   // envio los datos al antivity pedido
               }else{
                   Snackbar.make(findViewById(R.id.recyclerViewListProducto),"Productos no seleccionado(s)",
                           Snackbar.LENGTH_SHORT)
                           .show();
               }

                //Aqui el codigo donde mandas al activity Pedido

                return true;
            case R.id.cancel_product:
                return true;
            case R.id.check_product:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

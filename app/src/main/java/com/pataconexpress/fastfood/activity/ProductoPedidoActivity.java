package com.pataconexpress.fastfood.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.adapters.MyAdacterProducto;
import com.pataconexpress.fastfood.adapters.MyAdacterProductoPedido;
import com.pataconexpress.fastfood.adapters.MyAdapterDetallePedido;
import com.pataconexpress.fastfood.models.DetallePedido;
import com.pataconexpress.fastfood.models.Pedido;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.models.ProductoPedido;
import com.pataconexpress.fastfood.utils.GsonImpl;
import com.pataconexpress.fastfood.utils.OkHttpImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductoPedidoActivity extends AppCompatActivity {
    // Atributos
    private ProductoPedido productoPedido;
    private List<DetallePedido>detalles;
    private Pedido pedido;
    private List<Producto> ProdSeleccionados;
    private RecyclerView mRecyclerView;
    private MyAdapterDetallePedido mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int counter = 0;
    private TextView totalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_pedido);
        totalPedido = findViewById(R.id.textViewRegistrarTotal);

        //Captura del id categoria
        Bundle bundle = getIntent().getExtras();
        detalles = new ArrayList<>();
        pedido = new Pedido();
        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pedido");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Obtener valores
        Bundle seleccionados = getIntent().getExtras();
        if (seleccionados != null) {

            String rta = seleccionados.getString("seleccionados");
            ProdSeleccionados = GsonImpl.listFromJsonV(rta, Producto.class);
            for(int i =0;i<ProdSeleccionados.size();i++){
                DetallePedido dt= new DetallePedido();
                dt.setPedidosIdpedido(pedido);
                dt.setProdutosIdprodutos(ProdSeleccionados.get(i));
                detalles.add(dt);
            }
            //Log.i("arreglo --> ", productosPedidos.get(0).getNombre());
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewListProductoPedido);
            mLayoutManager = new LinearLayoutManager(ProductoPedidoActivity.this);
            mAdapter = new MyAdapterDetallePedido(detalles, R.layout.list_producto_pedido, ProductoPedidoActivity.this, new MyAdapterDetallePedido.OnItemClickListener(){
                @Override
                public void onItemClick(Producto productoPedido, int position) {
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
                //Aqui va el dialogo para capturar los datos adicionales del cliente(nombre,identificacion,direccion,contacto)
                double res = Double.parseDouble(totalPedido.getText().toString());
                pedido.setNombreCliente("alejandro");
                pedido.setContacto("2810435");
                pedido.setDireccionCliente("Cr 31#13-111");
                pedido.setPrecioTotal(res);
                pedido.setIdCliente(1102864480);
                pedido.setDetallePedidoList(detalles);
                Log.i("Mensaje","todo bien hasta aqui");
                Log.i("detallePedido",pedido.getDetallePedidoList().get(0).getProdutosIdprodutos().getNombre());
                registrarPedido(pedido);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registrarPedido(Pedido pedido) {
        String json = GsonImpl.objectToJSon(pedido);
        RequestBody body = RequestBody.create(OkHttpImpl.JSON, json);
        OkHttpImpl.newHttpCall(OkHttpImpl.getPostRequest("http://" + getString(R.string.ip) + ":8080/" + getString(R.string.path) + "/api/pedidos/create", body))
                //OkHttpImpl.newHttpCall(OkHttpImpl.getPostRequest("http://192.168.1.13:8080/PataconeraExpressBackend/api/productos/create", body))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(findViewById(R.id.recyclerViewListProductoPedido),e.getMessage(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                //Toast.makeText(ProductoPedidoActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final  String rta = response.body().string();
                        Log.i("mensaje respuesta",rta);
                        if(response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Toast.makeText(getActivity().getApplicationContext(),rta,Toast.LENGTH_LONG).show();
                                    Snackbar.make(findViewById(R.id.recyclerViewListProductoPedido),rta,
                                            Snackbar.LENGTH_SHORT)
                                            .show();
                                    //Intent inten = new Intent(ProductoPedidoActivity.this,MainActivity.class);
                                   // startActivity(inten);
                                }
                            });
                        }else runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(findViewById(R.id.recyclerViewListProductoPedido),rta,
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                                // Toast.makeText(getActivity().getApplicationContext(), rta, Toast.LENGTH_LONG).show();
                            }
                        });



                    }
                });



    }

    private void addrequestProduct(int position) {
        //productosPedidos.add(position, new ProductoPedido("hola", "descripción", 100000, R.drawable.ic_menu_gallery, 1, 2,3));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }
}

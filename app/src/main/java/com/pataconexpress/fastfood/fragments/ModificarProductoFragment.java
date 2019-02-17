package com.pataconexpress.fastfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.models.CategoriaDTO;
import com.pataconexpress.fastfood.models.Producto;
import com.pataconexpress.fastfood.utils.GsonImpl;
import com.pataconexpress.fastfood.utils.OkHttpImpl;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarProductoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarProductoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //atributos
    private Button buttonModificar;
    private ImageButton imageButtonBuscar;
    private Button buttonCancelar;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private Spinner spinnerCategoria;
    private EditText editTextPrecio;
    private String[] categoria = {"Seleccione"};
    private List<CategoriaDTO> categorias;
    private Producto p;

    private OnFragmentInteractionListener mListener;

    public ModificarProductoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarProductoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarProductoFragment newInstance(String param1, String param2) {
        ModificarProductoFragment fragment = new ModificarProductoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modificar_producto, container, false);
        // Capturar los datos
        buttonModificar = (Button) view.findViewById(R.id.buttonModificar);
        imageButtonBuscar = (ImageButton) view.findViewById(R.id.imageButtonBuscarMod);
        buttonCancelar = (Button) view.findViewById(R.id.buttonCancelarMod);
        editTextNombre = (EditText) view.findViewById(R.id.editTextNombreMod);
        editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcionMod);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoriaMod);
        editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecioMod);

        Request rq = new Request.Builder()
                .url("http://"+getString(R.string.ip)+":8080/PataconeraExpress/api/categorias/")
                //.url("http://192.168.1.13:8080/PataconeraExpressBackend/api/categorias/")
                .build();
        OkHttpImpl.newHttpCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String rta = response.body().string();
                    //Log.i("respuesta",rta);
                    //map json to obejct
                    categorias = GsonImpl.listFromJsonV(rta, CategoriaDTO.class);
                    //Log.i("Mapeo:",categorias.get(0).getNombreCat());

                    final List<CategoriaDTO> cats = categorias;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinnerCategoria.setAdapter(new ArrayAdapter<CategoriaDTO>(getContext(),
                                    android.R.layout.simple_spinner_dropdown_item,cats));
                        }
                    });
                }
            }
        });
        imageButtonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarProducto(v);
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        //Evento Click en registrar
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarProducto(v);


            }
        });

        //Enviar los datos al spinner
       // spinnerCategoria.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,categoria));
        return view;
    }

    private void modificarProducto(View v) {
       String nombre = editTextNombre.getText().toString().trim();
        String desc = editTextDescripcion.getText().toString().trim();
        double precio = Double.parseDouble(editTextPrecio.getText().toString().trim());
        CategoriaDTO categoria = (CategoriaDTO)spinnerCategoria.getSelectedItem();

        Producto productoModificado = new Producto(p.getIdProducto(),nombre,desc,precio);
        productoModificado.setCategoriasIdcategoria(categoria);
        String json = GsonImpl.objectToJSon(productoModificado);
        //Request
        RequestBody body = RequestBody.create(OkHttpImpl.JSON, json);
        Log.i("JSON",json);
        //llamado a la api
        OkHttpImpl.newHttpCall(OkHttpImpl.getPuttRequest("http://"+getString(R.string.ip)+":8080/PataconeraExpress/api/productos/edit/"
                +productoModificado.getIdProducto(), body))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call,final IOException e) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final  String rta = response.body().string();
                        Log.i("mensaje respuesta",rta);
                        if(response.isSuccessful()) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    clear();
                                    Toast.makeText(getActivity().getApplicationContext(),rta,Toast.LENGTH_LONG).show();
                                }
                            });
                        }else getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), rta, Toast.LENGTH_LONG).show();
                            }
                        });



                    }
                });

    }

    private void buscarProducto(View v) {
        String nombre = editTextNombre.getText().toString();
        if(nombre != null){
            Request rq = new Request.Builder()
                    .url("http://"+getString(R.string.ip)+":8080/PataconeraExpress/api/productos/nombre/"+nombre)
                    //.url("http://192.168.1.4:8080/PataconeraExpressBackend/api/productos/nombre/"+nombre)
                    .build();

            OkHttpImpl.newHttpCall(rq).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String rta = response.body().string();
                    if(response.isSuccessful()) {

                        Log.i("respuesta",rta);
                        p =  GsonImpl.fromJsonToObject(Producto.class,rta);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editTextNombre.setText(p.getNombre());
                                editTextDescripcion.setText(p.getDescripcion());
                                int count = spinnerCategoria.getAdapter().getCount();
                                for(int i = 0; i<count;i++){
                                    CategoriaDTO catefgoria = (CategoriaDTO) spinnerCategoria.getItemAtPosition(i);
                                    if(catefgoria.getIdcategoria()== p.getCategoriasIdcategoria().getIdcategoria()){
                                        spinnerCategoria.setSelection(i);
                                    }
                                }
                                editTextPrecio.setText(String.valueOf(p.getValor()));
                            }
                        });
                    }else{
                        Log.i("respuesta",rta);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),"ha ocurrido", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
        }else{
            Snackbar.make(getView(),"no ha llenado el parametro de busqueda nombre", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
    private void clear(){
        editTextNombre.setText("");
        editTextDescripcion.setText("");
        editTextPrecio.setText("");

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.pataconexpress.fastfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultarProductoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultarProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarProductoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //atributos
    private Button buttonConsultar;
    private Button buttonCancelar;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private Spinner spinnerCategoria;
    private EditText editTextPrecio;
    private String[] categoria = {"Seleccione"};
    private List<CategoriaDTO> categorias;
    private OnFragmentInteractionListener mListener;

    public ConsultarProductoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarProductoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarProductoFragment newInstance(String param1, String param2) {
        ConsultarProductoFragment fragment = new ConsultarProductoFragment();
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
        View view = inflater.inflate(R.layout.fragment_consultar_producto, container, false);
        // Capturar los datos
        buttonConsultar = (Button) view.findViewById(R.id.buttonConsultar);
        buttonCancelar = (Button) view.findViewById(R.id.buttonCancelarCons);
        editTextNombre = (EditText) view.findViewById(R.id.editTextNombreCons);
        editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcionCons);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoriaCons);
        editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecioCons);

        Request rq = new Request.Builder()
                .url("http://192.168.1.4:8080/PataconeraExpress/api/categorias/")
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

        //Evento Click en registrar
        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarProductos(v);
            }
        });

        //Enviar los datos al spinner
        //spinnerCategoria.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,categoria));


        return view;
    }

    private void consultarProductos(View v) {
        String nombreProd;
        String descProd;
        double precioProd;
        CategoriaDTO catProd;
        nombreProd = editTextNombre.getText().toString();
        descProd = editTextDescripcion.getText().toString();
        precioProd = Double.parseDouble(editTextPrecio.getText().toString());
        catProd = (CategoriaDTO) spinnerCategoria.getSelectedItem();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("192.168.1.4")
                .port(8080)
                .addPathSegments("PataconeraExpress/api/productos/search")
                .addQueryParameter("nombre",nombreProd)
                .addQueryParameter("precio",String.valueOf(precioProd))
                .addQueryParameter("idCat",String.valueOf(catProd.getIdcategoria()))
                .build();

        Request rq = new Request.Builder()
                .url(url)
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
                    Log.i("respuesta",rta);

                    //mapear lista de productos
                    List<Producto>productos = GsonImpl.listFromJsonV(rta,Producto.class);
                    if(productos!=null){
                        //aqui debe ir lo de listar los productos en la otra pestaña
                    }else{
                        //Toas messsage
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "La busqueda no arrojo resultados", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                    /**
                     * FABIO tu continuas aqui.
                     * Debes coger el arreglo y mandarlo a la otra parte que es la lista productos para mostrarlos y ya
                     *
                     */
                }
            }
        });






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

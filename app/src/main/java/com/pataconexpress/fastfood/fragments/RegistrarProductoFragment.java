package com.pataconexpress.fastfood.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrarProductoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrarProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarProductoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //lista de categorias
    private List<CategoriaDTO> categorias;
    //atributos
    private Button buttonRegistrar;
    private Button buttonCancelar;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private Spinner spinnerCategoria;
    private EditText editTextPrecio;
    private String[] categoria = {"Seleccione"};

    private OnFragmentInteractionListener mListener;

    public RegistrarProductoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarProductoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarProductoFragment newInstance(String param1, String param2) {
        RegistrarProductoFragment fragment = new RegistrarProductoFragment();
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
        View view = inflater.inflate(R.layout.fragment_registrar_producto, container, false);
        // Capturar los datos
        buttonRegistrar = (Button) view.findViewById(R.id.buttonRegistrarReg);
        buttonCancelar = (Button) view.findViewById(R.id.buttonCancelarReg);
        editTextNombre = (EditText) view.findViewById(R.id.editTextNombreReg);
        editTextDescripcion = (EditText) view.findViewById(R.id.editTextDescripcionReg);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoriaReg);
        editTextPrecio = (EditText) view.findViewById(R.id.editTextPrecioReg);

        //Evento Click en registrar
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProducto(v);
            }
        });
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

       // Log.i("Ip",getString(R.string.ip));
        Request rq = new Request.Builder()
                .url("http://" + getString(R.string.ip) + ":8080/" + getString(R.string.path) + "/api/categorias/")
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
                    categorias = GsonImpl.listFromJsonV(rta,CategoriaDTO.class);
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
        return view;
    }

    private void registrarProducto(View v) {
        String nombreProd;
                String descProd;
        double precioProd;
        CategoriaDTO catProd;
        nombreProd = editTextNombre.getText().toString();
        descProd = editTextDescripcion.getText().toString();
        precioProd = Double.parseDouble(editTextPrecio.getText().toString());
        catProd = (CategoriaDTO) spinnerCategoria.getSelectedItem();
        Producto nuevoProd = new Producto();
        nuevoProd.setNombre(nombreProd);
        nuevoProd.setDescripcion(descProd);
        nuevoProd.setValor(precioProd);
        nuevoProd.setCategoriasIdcategoria(catProd);
        String json = GsonImpl.objectToJSon(nuevoProd);
        RequestBody body = RequestBody.create(OkHttpImpl.JSON, json);
        Log.i("JSON",json);

        OkHttpImpl.newHttpCall(OkHttpImpl.getPostRequest("http://" + getString(R.string.ip) + ":8080/" + getString(R.string.path) + "/api/productos/create", body))
        //OkHttpImpl.newHttpCall(OkHttpImpl.getPostRequest("http://192.168.1.13:8080/PataconeraExpressBackend/api/productos/create", body))
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
                                  // Toast.makeText(getActivity().getApplicationContext(),rta,Toast.LENGTH_LONG).show();
                                   Snackbar.make(getView(),rta,
                                           Snackbar.LENGTH_SHORT)
                                           .show();
                               }
                           });
                       }else getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Snackbar.make(getView(),rta,
                                       Snackbar.LENGTH_SHORT)
                                       .show();
                              // Toast.makeText(getActivity().getApplicationContext(), rta, Toast.LENGTH_LONG).show();
                           }
                       });



                   }
        });
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

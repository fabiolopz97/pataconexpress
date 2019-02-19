package com.pataconexpress.fastfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pataconexpress.fastfood.R;
import com.pataconexpress.fastfood.activity.ProductoActivity;
import com.pataconexpress.fastfood.adapters.MyAdacterCatalogo;
import com.pataconexpress.fastfood.models.Catalogo;
import com.pataconexpress.fastfood.models.CategoriaDTO;
import com.pataconexpress.fastfood.utils.GsonImpl;
import com.pataconexpress.fastfood.utils.OkHttpImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatalogoProductoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CatalogoProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogoProductoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Atributos
    private Catalogo catalogo;
    RecyclerView mRecyclerView;
   // private List<Catalogo> catalogos;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int counter = 0;
    private List<CategoriaDTO> categorias;

    private OnFragmentInteractionListener mListener;

    public CatalogoProductoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogoProductoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogoProductoFragment newInstance(String param1, String param2) {
        CatalogoProductoFragment fragment = new CatalogoProductoFragment();
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
        final View view = inflater.inflate(R.layout.fragment_catalogo_producto, container, false);

        //Consulta realizada para traer las categorias
        Request rq = new Request.Builder()
                .url("http://" + getString(R.string.ip) + ":8080/" + getString(R.string.path) + "/api/categorias/")
                .build();
        OkHttpImpl.newHttpCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String rta = response.body().string();

                    //Log.i("respuesta -->> ",rta);

                    categorias = GsonImpl.listFromJsonV(rta, CategoriaDTO.class);
                    final List<CategoriaDTO> cats = categorias;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //--Ingreso de datos en el recycleview
                                    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewGridCatalogoProducto);
                                    mLayoutManager = new LinearLayoutManager(getContext());
                                    mAdapter = new MyAdacterCatalogo(cats, R.layout.grid_catalogo,  new MyAdacterCatalogo.OnItemClickListener(){
                                        @Override
                                        public void onItemClick(CategoriaDTO categoria, int position) {
                                            //Toast.makeText(getContext(), ("resultado: "+getId(position)), Toast.LENGTH_LONG).show();
                                            Intent intentProducto = new Intent(getContext(), ProductoActivity.class);
                                            intentProducto.putExtra("idCategoria", getId(position));
                                            startActivity(intentProducto);
                                        }
                                    });
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.setHasFixedSize(true);
                                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                }
                            });
                        }
                    });
                }
            }
        });
        return view;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()){
            case R.id.add_name:
                this.addName(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return true;
    }

    private List<Catalogo> getAllCatalogos () {
        return new ArrayList<Catalogo>(){{
            add(new Catalogo("holaaaa", R.drawable.ic_menu_delete));
            add(new Catalogo("hola", R.drawable.ic_menu_camera));
            add(new Catalogo("hola", R.drawable.ic_menu_gallery));
        }};
    }

    /*private void addCatalogo(int position) {
        catalogos.add(position, new Catalogo("hola", R.drawable.ic_launcher_background));
        mAdapter.notifyItemInserted(position);
        mLayoutManager.scrollToPosition(position);
    }
    private void deleteCatalogo(int position) {
        catalogos.remove(position);
        mAdapter.notifyItemRemoved(position);
    }*/
    private int getId(int position) {
        return categorias.get(position).getIdcategoria();
    }


}

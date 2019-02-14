package com.pataconexpress.fastfood.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpImpl {
    private static OkHttpClient cliente;//para enviar peticiones http

    private OkHttpImpl(){
    }
    public static OkHttpClient getCliente(){
        if(cliente == null){
            cliente = new OkHttpClient();
        }
        return cliente;
    }
    public static Call newHttpCall(Request rq){
        return getCliente().newCall(rq);
    }
  /*  public static Response[] newHttpCall(Request rq)throws IOException{
        final Response[] state = new Response[1];
        getCliente().newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                state[0] = null;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                state[0]=response;
            }
        });
        return state;
    }*/


}

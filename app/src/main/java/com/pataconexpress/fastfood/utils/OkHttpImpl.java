package com.pataconexpress.fastfood.utils;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpImpl {
    private static OkHttpClient cliente;//para enviar peticiones http
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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

    public static Request getRequest(String url){
        return new Request.Builder().url(url).build();
    }
    public static Request getPostRequest(String url, RequestBody rqBody){
        return new Request.Builder().
                url(url)
                .post(rqBody)
                .build();
    }
    public static Request getPuttRequest(String url, RequestBody rqBody){
        return new Request.Builder().
                url(url)
                .put(rqBody)
                .build();
    }
   /* public Response postRequest(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        //newHttpCall(body).


    }*/

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

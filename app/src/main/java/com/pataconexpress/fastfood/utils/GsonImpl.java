package com.pataconexpress.fastfood.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public final class GsonImpl {

    private static Gson map;
    private GsonImpl(){

    }
    /**
     * Obtiene una instancia reutilizable  de un objeto Gson
     * @return Objeto Gson
     */
    public static Gson getMapper(){
        if(map==null){
            map = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
        return map;
    }

    /**
     *
     * @param targetClass
     * @param json Array jon a convertir
     * @param <T>  clase objetivo de la conversion
     * @return  un objeto de la clase pasada por targetClass
     */
    public static <T> T fromJsonToObject(Class<T> targetClass,String json){


        return getMapper().fromJson(json,targetClass);
    }

    /**
     *
     * @param json
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> listFromJsonV(String json, Class<T> target){
        Type token = TypeToken.getParameterized(List.class, target).getType();

        return getMapper().fromJson(json,token);
    }

    /**
     *
     * @param clazz
     * @param json
     * @param <T>
     * @return
     */
    public static final <T> List<T> listFromJsonV(final Class<T[]> clazz, final String json)
    {
        final T[] jsonToObject = getMapper().fromJson(json, clazz);

        return Arrays.asList(jsonToObject);
    }

    public static String objectToJSon(Object o){

        return getMapper().toJson(o);
    }
}

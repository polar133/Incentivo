package com.monkeycoders.incentavo.incentivo.network;

import java.util.HashMap;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface  AppApi {
    
    /*
    Declaration of services wich connect with external servises such as bank's api and fake data
    */
    
    //Seguridad
    @GET("")
    HashMap<String, Object> getLogin(@Query("login") String login, @Query("password") String password);


    //EndPoints Banco
    @Headers("Content-Type: application/json")
    @POST("/cliente/productos")
    HashMap<String, Object> postSaldo(@Body HashMap<String, Object> credentials);

    @Headers("Content-Type: application/json")
    @POST("/operaciones/cargo")
    HashMap<String, Object> postCargo(@Body HashMap<String, Object> credentials);





    //EndPoints Fake
    @Headers("Content-Type: application/json")
    @POST("/users/login")
    HashMap<String, Object> postLogin(@Body HashMap<String, Object> credentials);

    @Headers("Content-Type: application/json")
    @GET("/children/{id}.json")
    HashMap<String, Object> getChildren(@Path(value = "id", encode = false) String id);

    @Headers("Content-Type: application/json")
    @GET("/items/{id}.json")
    HashMap<String, Object> getProduct(@Path(value = "id", encode = false) String id);

    @Headers("Content-Type: application/json")
    @GET("/items.json")
    HashMap<String, Object> getCatalog();

    // socket
    @Headers("Content-Type: application/json")
    @GET("/")
    HashMap<String, Object> openDashboard();

}

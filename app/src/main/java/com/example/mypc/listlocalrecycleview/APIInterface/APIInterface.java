package com.example.mypc.listlocalrecycleview.APIInterface;

import com.example.mypc.listlocalrecycleview.Config.Config;
import com.example.mypc.listlocalrecycleview.Model.Contact;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MyPC on 30/06/2016.
 */
public interface APIInterface {
    @GET(Config.URL_COnTACT)
    Call<Contact> getList();
}

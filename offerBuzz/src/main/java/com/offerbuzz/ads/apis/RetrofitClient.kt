package com.offerbuzz.ads.apis

import com.offerbuzz.ads.`interface`.OfferWallService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val service: OfferWallService by lazy {
        Retrofit.Builder()
            .baseUrl(Apis.APP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OfferWallService::class.java)
    }
}
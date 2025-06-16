package com.offerbuzz.ads.apis

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface OfferWallService {
    @FormUrlEncoded
    @POST("/api/offerwall/v1/api/sdkIniti")
    suspend fun sdkIniti(
        @Field("appId") appId: String,
        @Field("googleAdId") googleAdId: String,
        @Field("deviceId") deviceId: String,
        @Field("userId") userId: String
    ): Response<SdkInitiResponse>
}

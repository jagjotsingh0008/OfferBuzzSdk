package com.offerbuzz.ads.`interface`


interface InitializeCallback {
    fun onSuccess(message: String?)
    fun onFailure(error: String?)
}
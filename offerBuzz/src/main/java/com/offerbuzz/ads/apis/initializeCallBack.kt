package com.offerbuzz.ads.apis


interface InitializeCallback {
    fun onSuccess(message: String?)
    fun onFailure(error: String?)
}
package com.offerbuzz.ads.apis

interface StartOfferCallback {
    /** Called once the Offer Activity was successfully launched */
    fun onSuccess()

    /**
     * Called if we couldn’t launch, e.g. because the SDK wasn’t initialized yet.
     * @param reason a human-readable error message
     */
    fun onError(reason: String)
}
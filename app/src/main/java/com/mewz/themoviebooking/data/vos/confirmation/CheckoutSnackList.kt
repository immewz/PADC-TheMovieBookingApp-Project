package com.mewz.themoviebooking.data.vos.confirmation

import java.io.Serializable

data class CheckoutSnackList(
    val snackList: MutableList<CheckoutSnack>?
): Serializable

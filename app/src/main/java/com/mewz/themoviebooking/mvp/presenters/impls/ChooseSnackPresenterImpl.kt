package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.ChooseSnackPresenter
import com.mewz.themoviebooking.mvp.views.ChooseSnackView

class ChooseSnackPresenterImpl: AbstractBasePresenter<ChooseSnackView>(), ChooseSnackPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapPlusSnack(snackId: Int) {
        mView.showSnackPlus(snackId)
    }

    override fun onTapMinusSnack(snackId: Int) {
        mView.showSnackMinus(snackId)
    }

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapBottomSheet() {
        mView.showBottomSheetSnack()
    }

    override fun onTapNextScreen() {
        mView.navigateToCheckoutScreen()
    }


}
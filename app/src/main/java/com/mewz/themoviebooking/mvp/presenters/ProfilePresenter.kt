package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.ProfileView

interface ProfilePresenter: BasePresenter<ProfileView> {
    fun onTapLogout()
}
package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.CitiesViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.LocationView

interface LocationPresenter: BasePresenter<LocationView>, CitiesViewHolderDelegate {
}
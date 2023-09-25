package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.MovieViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.MovieView

interface MoviePresenter: BasePresenter<MovieView>, MovieViewHolderDelegate{
}
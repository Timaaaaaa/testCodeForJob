package com.thousand.aidynnury.main.presentation.activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainActivityView: MvpView {

    fun showProgressBar(show: Boolean)
}
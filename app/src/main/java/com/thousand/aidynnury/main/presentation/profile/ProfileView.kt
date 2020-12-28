package com.thousand.aidynnury.main.presentation.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.*

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileView: MvpView {

    fun showLessonData(dataList: List<Question>)
    fun openLessonDetailFrag(id : Int)
    fun showDialog(infoNet: InfoNet)
}
package com.thousand.aidynnury.main.presentation.info.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem

@StateStrategyType(OneExecutionStateStrategy::class)
interface InfoDetailsView: MvpView {

    fun showLessonData(dataList: List<Info?>)
    fun openLessonDetailFrag(id : Int)
    fun showInfoData(info : Info)
}
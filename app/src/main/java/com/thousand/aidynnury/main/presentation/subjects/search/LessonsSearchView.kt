package com.thousand.aidynnury.main.presentation.subjects.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Lesson

@StateStrategyType(OneExecutionStateStrategy::class)
interface LessonsSearchView: MvpView {

    fun showLessonData(dataList: MutableList<Lesson>)
    fun openLessonDetailFrag(lesson: Lesson)
    fun openDialog(lesson : Lesson)
}
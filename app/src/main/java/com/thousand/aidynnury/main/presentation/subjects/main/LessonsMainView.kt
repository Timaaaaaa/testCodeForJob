package com.thousand.aidynnury.main.presentation.subjects.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.Version

@StateStrategyType(OneExecutionStateStrategy::class)
interface LessonsMainView: MvpView {

    fun showLessonData(dataList: MutableList<Lesson>)
    fun openLessonDetailFrag(lesson : Lesson)
    fun openDialog(lesson : Lesson)
    fun showDialogAlert(title : String, body : String)
    fun getUpdate(update : Version)
}
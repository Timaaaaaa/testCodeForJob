package com.thousand.aidynnury.main.presentation.subjects.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem

@StateStrategyType(OneExecutionStateStrategy::class)
interface LessonsDetailsView: MvpView {


    fun setLessonData(lesson : LessonItem)
    fun setLessonHomeWorkData(lesson : LessonItem)
}
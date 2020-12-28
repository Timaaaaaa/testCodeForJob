package com.thousand.aidynnury.main.presentation.subjects.lesson

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.CourseLessons
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem

@StateStrategyType(OneExecutionStateStrategy::class)
interface LessonsView: MvpView {

    fun showLessonData(dataList: MutableList<CourseLessons>)
    fun openLessonDetailFrag(lesson : CourseLessons)
}
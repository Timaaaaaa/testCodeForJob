package com.thousand.aidynnury.main.presentation.faq

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.entity.Question

@StateStrategyType(OneExecutionStateStrategy::class)
interface FaqView: MvpView {

    fun showLessonData(dataList: List<Question>?)
}
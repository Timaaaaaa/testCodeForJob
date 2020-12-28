package com.thousand.aidynnury.main.presentation.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.entity.Question

@StateStrategyType(OneExecutionStateStrategy::class)
interface AuthView: MvpView {
    fun showLessonData(dataList: List<Question>)
    fun openProfileFrag()
    fun showMessInvalid(mess : String)
    fun setPolicy(policyText : String)
}
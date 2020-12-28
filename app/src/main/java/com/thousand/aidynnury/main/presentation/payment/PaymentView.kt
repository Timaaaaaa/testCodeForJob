package com.thousand.aidynnury.main.presentation.payment

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.*

@StateStrategyType(OneExecutionStateStrategy::class)
interface PaymentView: MvpView {

    fun showLessonData(dataList: List<Question>)
    fun openLessonDetailFrag(id : Int)
    fun showDialog(feedBack: FeedBack)
    fun openMainPage(url : String)
    fun showMessInvalid(mess : String)
    fun editTextPayment(infoNet : InfoNet)
}
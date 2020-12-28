package com.thousand.aidynnury.main.presentation.payment

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.helperapp.global.utils.AppConstants
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class PaymentPresenter(
    private var userInteractor: UserInteractor
): BasePresenter<PaymentView>() {

    val TAG = "LessonsPresenter"
    var lessons = listOf(
        Question(id =1, ask = "жугуруду уйрену",answer = "27 қаңтар, 2020 жыл"),
        Question(id =2, ask = "секируди уйрену",answer = "25 қаңтар, 2020 жыл"),
        Question(id =3, ask = "улгеруди уйрену",answer = "24 қаңтар, 2020 жыл"),
        Question(id =4, ask = "билеуди уйрену",answer = "23 қаңтар, 2020 жыл")
    )

    fun getLessonData(){
        viewState.showLessonData(lessons)
    }

    fun onLessonItemClick(lesson : Question){
            lesson.id?.let { viewState.openLessonDetailFrag(it) }
        lesson.isSelected = !lesson.isSelected
        viewState.showLessonData(lessons)
    }

    fun getLessonByu(phone : String, name:String,email : String,androidId:String, id : Int){
        userInteractor.getCourseBuy(phone = phone, name = name,email=email,deviceId = androidId, id= id).subscribe({

                    it.get("paybox_url")?.asString?.let { it1 ->
                        Log.i(TAG, it1)
                        viewState.openMainPage(
                            it1
                        )
                    }


        },{
            val message = ApiModelHelper.getErrorMessage(it)
            message.let { it1 -> viewState.showMessInvalid(it1) }
        }).connect()
    }

    fun getInfo(){
        userInteractor.getInfo().subscribe({
            when(it.code()){
                200->{
                    Log.i(TAG, it.body()?.email.toString())
                    it.body()?.let { it1 ->viewState.editTextPayment(it1)  }
                }
                else->{

                }
            }
        },{

        }).connect()
    }

}
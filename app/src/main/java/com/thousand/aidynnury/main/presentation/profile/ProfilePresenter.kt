package com.thousand.aidynnury.main.presentation.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class ProfilePresenter(
    private var userInteractor: UserInteractor
): BasePresenter<ProfileView>() {

    val TAG = "LessonsPresenter"
    var lessons : List<Question>? = listOf()

    fun getLessonData(){
        lessons?.let { viewState.showLessonData(it) }
    }

    fun onLessonItemClick(lesson : Question){
            lesson.id?.let { viewState.openLessonDetailFrag(it) }
        lesson.isSelected = !lesson.isSelected
        lessons?.let { viewState.showLessonData(it) }
    }

    fun getFeedBack(){
        userInteractor.getFeedBack().subscribe({
            when(it.code()){
                200->{
                    Log.i(TAG, it.body()?.email.toString())
                    it.body()?.let {  }
                }
                else->{

                }
            }
        },{

        }).connect()
    }

    fun getInfo(){
        userInteractor.getInfo().subscribe({
            when(it.code()){
                200->{
                    Log.i(TAG, it.body()?.email.toString())
                    it.body()?.let { it1 -> viewState.showDialog(it1) }
                }
                else->{

                }
            }
        },{

        }).connect()
    }

}
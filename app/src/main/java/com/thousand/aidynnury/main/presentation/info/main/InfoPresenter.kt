package com.thousand.aidynnury.main.presentation.info.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class InfoPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<InfoView>() {

    val TAG = "LessonsPresenter"

    fun getPostCatsData(){
        userInteractor.getPostCats().subscribe({
            when(it.code()){
                200->{
                    val buttons = it.body()
                    if (buttons != null && buttons.isNotEmpty()) {
                        viewState.setButtons(buttons)
                    }
                }
                else->{}
            }
        },{

        }).connect()
    }

    fun getPostCatsData(variant : Int){
        userInteractor.getPosts(token = LocalStorage.getAccessToken(), page = 1 ).subscribe({
            when(it.code()){
                200->{
                    val buttons = it.body()?.let { it1 -> ApiModelHelper.getArray(it1,Info::class.java) }
                    if (buttons != null && buttons.isNotEmpty()) {
                        Log.i(TAG, buttons[0].images?.get(0).toString())
                        viewState.showLessonData(buttons)
                    }
                }
                else->{}
            }
        },{

        }).connect()
    }

    fun onLessonItemClick(lesson : Info){
            lesson.id?.let { viewState.openLessonDetailFrag(lesson) }
    }

}
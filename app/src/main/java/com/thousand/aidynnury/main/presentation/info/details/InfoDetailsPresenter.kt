package com.thousand.aidynnury.main.presentation.info.details

import com.arellomobile.mvp.InjectViewState
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class InfoDetailsPresenter(
    private var userInteractor: UserInteractor
): BasePresenter<InfoDetailsView>() {

    val TAG = "LessonsPresenter"
    var lessons : MutableList<Info>? = mutableListOf()

    fun getLessonData(){
        lessons?.let { viewState.showLessonData(it) }
    }

    fun onLessonItemClick(lesson : Info){
        lesson.id?.let { viewState.openLessonDetailFrag(it) }
    }

    fun setInfoData(info : Int){

        info.let { userInteractor.getPostById(post_id = it).subscribe({

            it.body()?.let { it1 -> viewState.showInfoData(it1) }
        },{

        }).connect(
        ) }

    }
}
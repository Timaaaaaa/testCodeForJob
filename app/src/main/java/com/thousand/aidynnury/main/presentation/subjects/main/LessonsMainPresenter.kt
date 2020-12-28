package com.thousand.aidynnury.main.presentation.subjects.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.helperapp.global.utils.AppConstants
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.main.interactor.UserInteractor


@Suppress("NAME_SHADOWING")
@InjectViewState
class LessonsMainPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<LessonsMainView>() {

    val TAG = "MainActivityPresenter"

    var lessons : MutableList<Lesson>? = mutableListOf()
    fun getLessonData(page : Int){
        if(page == AppConstants.MY_LESSONS){
            getMyCourses()
            return
        }
        userInteractor.getCategory(token = LocalStorage.getAccessToken(), page = page).subscribe({
            it.body()?.let {it1 ->
                val lessonss = ApiModelHelper.getArray(it1,Lesson::class.java)
                if(!lessonss.isNullOrEmpty()){
                    lessonss.forEach { it2 ->

                        lessons?.add(it2)
                    }
                }
                lessonss.clear()
                if(!lessons.isNullOrEmpty()){
                    Log.i(TAG, "lessons.isNullOrEmpty()")
                    lessons?.let { it1 -> viewState.showLessonData(it1) }
                }
            }

        },{

        }).connect()
    }

    fun getLessonDataRefresh(page : Int){
        if(page == AppConstants.MY_LESSONS){
            getMyCourses()
            return
        }
        userInteractor.getCategory(token = LocalStorage.getAccessToken(), page = page).subscribe({
            it.body()?.let {it1 ->
                val lessonss = ApiModelHelper.getArray(it1,Lesson::class.java)
                lessons?.clear()
                if(!lessonss.isNullOrEmpty()){
                    lessonss.forEach { it2 ->
                        Log.i(TAG, ""+it2.title)
                        lessons?.add(it2)
                    }
                }
                lessonss.clear()
                if(!lessons.isNullOrEmpty()){

                    Log.i(TAG, "lessons.isNullOrEmpty()")
                    lessons?.let { it1 -> viewState.showLessonData(it1) }
                }
            }

        },{

        }).connect()
    }

    private fun getMyCourses(){
        userInteractor.getMyCourses(token = LocalStorage.getAccessToken(), page = 1).subscribe({
            when(it.code()){
                200->{
                    it.body()?.let {it1 ->
                        val lessonss = ApiModelHelper.getArray(it1,Lesson::class.java)
                        lessons?.clear()
                        if(!lessonss.isNullOrEmpty()){
                            lessonss.forEach { it2 ->
                                Log.i(TAG, ""+it2.title)
                                lessons?.add(it2)
                            }
                        }
                        lessonss.clear()
                        if(!lessons.isNullOrEmpty()){

                            Log.i(TAG, "lessons.isNullOrEmpty()")
                            lessons?.let { it1 -> viewState.showLessonData(it1) }
                        }
                    }
                }
                else->{

                }
            }
        },{

        }).connect()
    }

    fun loginByToken(){
        if(LocalStorage.getName() != "1"){
            userInteractor.loginByToken(token = LocalStorage.getAccessToken()).subscribe({
                Log.i(TAG, it.body().toString())
                if(it.body().toString() == "success"){

                }else{
                    viewState.showDialogAlert("Хабарландыру", "Бір құрылғыдан кіріңіз!")
                }
            },{

            }).connect()

        }
    }

    fun version(){

        userInteractor.version().subscribe({
            it.body()?.let { it1 -> viewState.getUpdate(it1) }
        },{

        }).connect()
    }

    fun onLessonClick(lesson : Lesson){
        lesson.let { viewState.openLessonDetailFrag(it) }
    }

}
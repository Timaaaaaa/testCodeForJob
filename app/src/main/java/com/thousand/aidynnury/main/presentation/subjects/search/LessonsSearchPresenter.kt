package com.thousand.aidynnury.main.presentation.subjects.search

import android.util.Log
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.interactor.UserInteractor
import com.thousand.aidynnury.main.presentation.activity.MainActivityView


@Suppress("NAME_SHADOWING")
@InjectViewState
class LessonsSearchPresenter(
    private var userInteractor: UserInteractor
): BasePresenter<LessonsSearchView>() {

    val TAG = "SearchActivityPresenter"
    private var lessons : MutableList<Lesson>? = mutableListOf()

    private var findLessons  = mutableListOf<Lesson>()

    fun getLessonData(page : Int){
        userInteractor.getCategory(token = LocalStorage.getAccessToken(), page = page).subscribe({
            it.body()?.let {it1 ->
                val lessonss = ApiModelHelper.getArray(it1,Lesson::class.java)
                if(!lessonss.isNullOrEmpty()){
                    lessonss.forEach { it2 ->
                        Log.i(TAG, ""+it2.title)
                        lessons?.add(it2)
                    }
                }
                if(!lessons.isNullOrEmpty()){
                    Log.i(TAG, "lessons.isNullOrEmpty()")
                    lessons?.let { it1 -> viewState.showLessonData(it1) }
                }
            }

        },{

        }).connect()
    }

    fun onLessonClick(lesson : Lesson){
        if(!lesson.isSelected){
            viewState.openDialog(lesson)
        }else{
            lesson.let { viewState.openLessonDetailFrag(it) }
        }
    }

    fun onSerchTextChanged(text : String){
        findLessons.clear()
        lessons?.forEach { it ->
            it.title?.let{it2->
            if(it2.contains(text)){
                findLessons.add(it)
            }
            }
        }
        viewState.showLessonData(findLessons)
    }

}
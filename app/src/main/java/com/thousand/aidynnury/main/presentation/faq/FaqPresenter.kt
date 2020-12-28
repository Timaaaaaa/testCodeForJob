package com.thousand.aidynnury.main.presentation.faq

import android.util.Log
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.main.interactor.UserInteractor
import com.thousand.aidynnury.main.presentation.activity.MainActivityView


@InjectViewState
class FaqPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<FaqView>() {

    val TAG = "LessonsPresenter"
    private var questions: MutableList<Question>? = mutableListOf()

    fun getLessonData(){
        userInteractor.getQuestion().subscribe(
            {
                questions = it?.body()
                if(!questions.isNullOrEmpty()){
                    viewState.showLessonData(questions)
                }
        },{

        }).connect()

    }

    fun onLessonItemClick(lesson : Question){
        lesson.isSelected = !lesson.isSelected
        viewState.showLessonData(questions)
    }

}
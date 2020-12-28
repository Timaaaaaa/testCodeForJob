package com.thousand.aidynnury.main.presentation.subjects.lesson

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.helperapp.global.utils.AppConstants
import com.thousand.aidynnury.entity.CourseLessons
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class LessonsPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<LessonsView>() {

    val TAG = "LessonsPresenter"
    var lessons : MutableList<CourseLessons> = mutableListOf<CourseLessons>()
    var Mylessons : MutableList<Lesson> = mutableListOf<Lesson>()
    var page = 1

    private var findLessons  = mutableListOf<CourseLessons>()

    fun getLessonData(id:Int){
        /*userInteractor.getLessons(token = LocalStorage.getAccessToken(), page = 1,id = id).subscribe({
            when(it.code()){
                200->{
                    Log.i(TAG, "getLessonData")
                    var lessonss = it.body()?.let { it1 -> ApiModelHelper.getArray(it1, LessonItem::class.java) }
                    Log.i(TAG, lessonss.isNullOrEmpty().toString())
                    if(!lessonss.isNullOrEmpty()){
                        lessonss.forEach{lesson->
                            Log.i(TAG, lesson.title.toString())
                            lessons.add(lesson)
                            Log.i(TAG, lessons[0].title.toString())
                        }
                    }
                    Log.i(TAG, lessons.isNullOrEmpty().toString())
                    if(!lessons.isNullOrEmpty()){
                        viewState.showLessonData(lessons)
                    }
                }
                else->{

                }
            }
        },{

        }).connect()*/
    }

    fun getLessonData(lesson : Lesson){
        lesson.id?.let { userInteractor.getLessonByCourseId(course_id = it).subscribe({

            var listLessons = it.body()
            lessons.clear()
            if(!listLessons.isNullOrEmpty()){
                viewState.showLessonData(lessons)

                listLessons.forEach{
                    it.allowed = lesson.isSelected
                    lessons.add(it)

                    Log.i(TAG, it.title.toString())
                }
            }
            if(!lessons.isNullOrEmpty()){
                viewState.showLessonData(lessons)
            }

        },{

        }).connect()
        }
    }

    fun getLessonDataMore(id:Int){/*
        page++
        userInteractor.getLessons(token = LocalStorage.getAccessToken(), page = page,id = id).subscribe({
            when(it.code()){
                200->{
                    Log.i(TAG, "getLessonData")
                    var lessonss = it.body()?.let { it1 -> ApiModelHelper.getArray(it1, LessonItem::class.java) }
                    Log.i(TAG, lessonss.isNullOrEmpty().toString())
                    if(!lessonss.isNullOrEmpty()){
                        lessonss.forEach{lesson->
                            Log.i(TAG, lesson.title.toString())
                            lessons.add(lesson)
                            Log.i(TAG, lessons[0].title.toString())
                        }
                    }
                    Log.i(TAG, lessons.isNullOrEmpty().toString())
                    if(!lessons.isNullOrEmpty()){
                        viewState.showLessonData(lessons)
                    }
                }
                else->{

                }
            }
        },{

        }).connect()*/
    }

    fun onLessonItemClick(lesson : CourseLessons){
        if(lesson.allowed){
            lesson.id?.let { viewState.openLessonDetailFrag(lesson) }
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
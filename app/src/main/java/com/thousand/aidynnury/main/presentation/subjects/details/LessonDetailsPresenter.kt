package com.thousand.aidynnury.main.presentation.subjects.details

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class LessonDetailsPresenter(
    private val userInteractor: UserInteractor
):BasePresenter<LessonsDetailsView>() {

    val TAG = "LessonsDetailsPresenter"

    var lessons = listOf<LessonItem>()
    private var courseLessonItem : LessonItem? = null

    fun onLessonItemClick(lesson : LessonItem){

    }

    fun setData(lessonId : Int){
        userInteractor.getLessonById(lesson_id = lessonId,token = LocalStorage.getAccessToken()).subscribe({

            it.body()?.let { it1 ->
                Log.i(TAG, it1.video_url.toString())

                courseLessonItem = it1
            }

            courseLessonItem?.let { it1 -> viewState.setLessonData(it1) }
            Log.i(TAG, courseLessonItem?.video_url.toString())
        },{

        }).connect()
    }
    fun setHomeWork(lesson : LessonItem){
        viewState.setLessonHomeWorkData(lesson)
    }
    fun makeLink(youTubePlayer : YouTubePlayer){
        Log.i(TAG, "fun makeLink(youTubePlayer : YouTubePlayer)")
        courseLessonItem?.video_url?.let { youTubePlayer.cueVideo(it, 0f) }
    }
}
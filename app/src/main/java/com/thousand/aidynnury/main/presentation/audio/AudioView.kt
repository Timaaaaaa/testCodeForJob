package com.thousand.aidynnury.main.presentation.audio

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem

@StateStrategyType(OneExecutionStateStrategy::class)
interface AudioView: MvpView {

    fun pauseMediaAudio()

    fun playMediaAudio()

    fun onCreateMediaPlayer(url : String)

    fun releaseMediaPlayer()

}
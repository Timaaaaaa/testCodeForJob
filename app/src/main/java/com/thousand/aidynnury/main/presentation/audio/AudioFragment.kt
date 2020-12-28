package com.thousand.aidynnury.main.presentation.audio

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.LocalServerSocket
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.di.ServiceProperties.SERVER_URL_IMAGE
import com.thousand.aidynnury.global.extension.*
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import kotlinx.android.synthetic.main.fragment_audio.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AudioFragment : BaseFragment(), AudioView {

    override val layoutRes: Int = R.layout.fragment_audio

    companion object {
        var TAG = "AudioFragment"
        val LESSON = "Lesson_AUDIO"
        val VARIANT = "VARIANT_AUDIO"

       fun newInstance(variant: Int,lesson : LessonItem) : AudioFragment{
           val fragment = AudioFragment()
           val args = Bundle()
           args.putParcelable(LESSON, lesson)
           args.putInt(VARIANT, variant)
           fragment.arguments = args
           Log.i(LessonDetailsFragment.TAG, "newInstance")
           return fragment
       }
    }

    @InjectPresenter
    lateinit var presenter: AudioPresenter

    @ProvidePresenter
    fun providePresenter(): AudioPresenter {
        return getKoin().getOrCreateScope(MainScope.AUDIO_SCOPE, named(MainScope.AUDIO_SCOPE)).get()
    }

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    var lessonItem : LessonItem? = null
    var playBoolean : Boolean = true
    var playIndex : Int = 0

    override fun setUp(savedInstanceState: Bundle?) {
        btnBackHeader.visibility = View.VISIBLE

        arguments?.apply {
            if(getInt(VARIANT) == 0){
                lessonItem = getParcelable(LESSON)
                imgAudioPoster.setCircleImageUrl(SERVER_URL_IMAGE + lessonItem?.video_fon)
                txtNameOfAudio.text = lessonItem?.title
                txtAudioDesc.text = LocalStorage.getAuthor()
                txtAudioCount.text = String.format(getString(R.string.label_audio_count), lessonItem?.audios?.size)
                lessonItem?.audios?.get(playIndex)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
            }else{
                lessonItem = getParcelable(LESSON)
                imgAudioPoster.setCircleImageUrl(SERVER_URL_IMAGE + lessonItem?.video_fon)
                txtNameOfAudio.text = lessonItem?.title
                txtAudioDesc.text = LocalStorage.getAuthor()
                txtAudioCount.text = String.format(getString(R.string.label_audio_count), lessonItem?.homework_audios?.size)
                lessonItem?.homework_audios?.get(playIndex)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
            }

        }

        imgBtnPlayPause.setOnClickListener{
            playBoolean = if(playBoolean){
                pauseMediaAudio()
                false
            }else{
                playMediaAudio()
                true
            }
        }

        imgBtnMinus15.setOnClickListener {
            arguments?.apply {

                if(getInt(VARIANT) == 0){
                    if(playIndex > 0){
                        lessonItem?.audios?.get(playIndex-1)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
                        playIndex--
                    }else{
                        context?.apply {
                            Toast.makeText(this,"Алгашкы аудиода тур", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    if(playIndex > 0){
                        lessonItem?.homework_audios?.get(playIndex-1)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
                        playIndex--
                    }else{
                        context?.apply {
                            Toast.makeText(this,"Алгашкы аудиода тур", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }




        }
        imgBtnPlus15.setOnClickListener {
            lessonItem?.apply {
                arguments?.apply {
                    if(getInt(VARIANT)==0){
                        if(playIndex < audios.size){
                            lessonItem?.audios?.get(playIndex+1)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
                            playIndex++
                        }else{
                            context?.apply {
                                Toast.makeText(this,"Сонгы аудиода тур", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        if(playIndex < homework_audios.size){
                            lessonItem?.homework_audios?.get(playIndex+1)?.let { it1 -> onCreateMediaPlayer(SERVER_URL_IMAGE + it1) }
                            playIndex++
                        }else{
                            context?.apply {
                                Toast.makeText(this,"Сонгы аудиода тур", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }
        }

        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        seekBarAudio?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer?.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initializeSeekBar() {
        mediaPlayer?.apply {
            seekBarAudio?.max = seconds
            runnable = Runnable {
                seekBarAudio?.progress = currentSeconds

                tv_pass?.text = "${(currentSeconds/60).addZeroOnFirst()}:${(currentSeconds%60).addZeroOnFirst()}"
                tv_due?.text =   "${((seconds - currentSeconds)/60).addZeroOnFirst()}:${((seconds - currentSeconds)%60).addZeroOnFirst()}"


                if(isPlaying ){
                    showProgressBar(false)
                }
                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.AUDIO_SCOPE)?.close()
        releaseMediaPlayer()
        super.onDestroy()
    }

    override fun releaseMediaPlayer(){
        mediaPlayer?.apply {
            stop()
            reset()
            release()
            handler.removeCallbacks(runnable)
        }
    }

    override fun onCreateMediaPlayer(url : String){
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer.create(context, Uri.parse(url))
        mediaPlayer?.start()
        initializeSeekBar()
        imgBtnPlayPauseChange.setImageResource(R.drawable.icon_28)
        mediaPlayer?.setOnCompletionListener {}
    }

    override fun pauseMediaAudio(){
        mediaPlayer?.apply {
            if(isPlaying){
                pause()
                imgBtnPlayPauseChange.setImageResource(R.drawable.icon_26)
            }
        }
    }

    override fun playMediaAudio() {
        mediaPlayer?.apply {
            start()
            imgBtnPlayPauseChange.setImageResource(R.drawable.icon_28)
        }
    }

}

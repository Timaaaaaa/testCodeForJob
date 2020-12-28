package com.thousand.aidynnury.main.presentation.subjects.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.audio.AudioFragment
import com.thousand.aidynnury.main.presentation.subjects.details.Homework.HomeworkFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LessonDetailsFragment : BaseFragment(), LessonsDetailsView {

    override val layoutRes: Int = R.layout.fragment_lesson_details

    companion object {

        val TAG = "LessonsDetailsFragment"
        val LESSON = "Lesson_DETAILS"

        fun newInstance(lessonId : Int) : LessonDetailsFragment{
            val fragment = LessonDetailsFragment()
            val args = Bundle()
            args.putInt(LESSON, lessonId)
            fragment.arguments = args
            Log.i(TAG, "newInstance $lessonId")
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: LessonDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): LessonDetailsPresenter {
        return getKoin().getOrCreateScope(MainScope.LESSON_DETAILS_SCOPE, named(MainScope.LESSON_DETAILS_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LESSON_DETAILS_SCOPE)?.close()
        super.onDestroy()
    }

    var lessonItem : LessonItem? = null

    override fun setUp(savedInstanceState: Bundle?) {
        setTitle()
        setOnClickListener()
        arguments?.apply {
            getInt(LESSON).let {
                presenter.setData(it)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setOnClickListener(){
        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        btnHomeLesson.setOnClickListener {
            lessonItem?.let { it1 -> HomeworkFragment.newInstance(it1) }?.let { it2 ->
                activity?.supportFragmentManager?.addFragmentWithBackStack(
                    R.id.container_fragment,
                    it2,
                    HomeworkFragment.TAG
                )
            }
        }

    }

    private fun setTitle(){
        btnBackHeader.visibility = View.VISIBLE
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setLessonData(lesson : LessonItem){
        Log.i(TAG, lesson.toString())
        lessonItem = lesson
        txtLessonTitle.text = lesson.title
        webViewHomeWork.settings.javaScriptEnabled = true
        webViewHomeWork.webChromeClient = object : WebChromeClient(){
            override fun onShowCustomView(fullScreenContent: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(fullScreenContent, callback)
                Log.d(TAG, "onShowCustomView")
                fullScreenContent?.let {fullScreenView ->
                    activity?.videoFrame?.removeAllViews()
                    activity?.videoFrame?.visibility = View.VISIBLE
                    activity?.videoFrame?.addView(fullScreenView)
                }
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                Log.d(TAG, "onShowCustomView")
                activity?.videoFrame?.visibility = View.GONE
                activity?.videoFrame?.removeAllViews()
            }
        }
        context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorDarkBlue) }?.let { it2 ->
            webViewHomeWork.setBackgroundColor(
                it2
            )
        }
        webViewHomeWork.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" name=\"viewport\">\n" +
                "\n" +
                "\n" +
                "    <style>\n" +
                "      iframe{\n" +
                "        width: 100%;\n" +
                "        min-height: 150px;\n" +
                "      }\n" +
                "      img{\n" +
                "        display: block;\n" +
                "        max-width: 100%;\n" +
                "        height: auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>"+lesson.description+"</body>\n" +
                "</html>", "text/html", "UTF-8", "")
        if(!lesson.audios.isNullOrEmpty()){
            constAudio.visibility = View.VISIBLE
            txtAudioTitle.text = lesson.title
            txtAudioAuthor.text = LocalStorage.getAuthor()
            constAudio.setOnClickListener {
                activity?.supportFragmentManager?.addFragmentWithBackStack(
                    R.id.container_fragment,
                    AudioFragment.newInstance(0,lesson),
                    AudioFragment.TAG
                )
            }
        }
        if(lesson.homework == null){
            btnHomeLesson.visibility = View.GONE
        }
    }

    override fun setLessonHomeWorkData(lesson : LessonItem){

    }
}

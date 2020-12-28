package com.thousand.aidynnury.main.presentation.subjects.details.Homework

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.content.ContextCompat
import com.thousand.aidynnury.R

import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.presentation.audio.AudioFragment
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_homework.*
import kotlinx.android.synthetic.main.header_toolbar.*

class HomeworkFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_homework


    companion object {
        val TAG = "HomeworkFragment"
        val LESSON = "Lesson_HOMEWORK"
        fun newInstance(lessonItem: LessonItem) : HomeworkFragment {
            val fragment = HomeworkFragment()
            val args = Bundle()
            args.putParcelable(LESSON, lessonItem)
            fragment.arguments = args
            Log.i(TAG, "newInstance")
            return fragment
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setUp(savedInstanceState: Bundle?) {

        btnBackHeader.visibility = View.VISIBLE
        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        txtTitleHeader.visibility = View.VISIBLE
        txtTitleHeader.text = "Үй тапсырмасы"
        arguments?.apply {
            var lesson = getParcelable<LessonItem>(LESSON)
            Log.d(TAG, lesson?.homework_audios?.size.toString())
            if(!lesson?.homework_audios.isNullOrEmpty()){
                Log.d(TAG, "lesson?.audios.isNullOrEmpty")
                constAudio.visibility = View.VISIBLE
                txtAudioTitle.text = lesson?.title
                txtAudioAuthor.text = LocalStorage.getAuthor()
                constAudio.setOnClickListener {
                    lesson?.let { it1 -> AudioFragment.newInstance(1,it1) }?.let { it2 ->
                        activity?.supportFragmentManager?.addFragmentWithBackStack(
                            R.id.container_fragment,
                            it2,
                            AudioFragment.TAG
                        )
                    }
                }
            }
            webViewHomeWork1.settings.javaScriptEnabled = true
            webViewHomeWork1.webChromeClient = object : WebChromeClient(){
                override fun onShowCustomView(fullScreenContent: View?, callback: CustomViewCallback?) {
                    super.onShowCustomView(fullScreenContent, callback)
                    Log.d(LessonDetailsFragment.TAG, "onShowCustomView")
                    fullScreenContent?.let {fullScreenView ->
                        activity?.videoFrame?.removeAllViews()
                        activity?.videoFrame?.visibility = View.VISIBLE
                        activity?.videoFrame?.addView(fullScreenView)
                    }
                }

                override fun onHideCustomView() {
                    super.onHideCustomView()
                    Log.d(LessonDetailsFragment.TAG, "onShowCustomView")
                    activity?.videoFrame?.visibility = View.GONE
                    activity?.videoFrame?.removeAllViews()
                }
            }
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorDarkBlue) }?.let { it2 ->
                webViewHomeWork1.setBackgroundColor(
                    it2
                )
            }
            webViewHomeWork1.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
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
                    "<body>"+lesson?.homework+"</body>\n" +
                    "</html>", "text/html", "UTF-8", "")
        }
    }

}

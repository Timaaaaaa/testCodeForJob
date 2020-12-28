package com.thousand.aidynnury.main.presentation.subjects.lesson

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.LessonItemAdapter
import com.thousand.aidynnury.entity.CourseLessons
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.payment.PaymentFragment
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import kotlinx.android.synthetic.main.fragment_lessons.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LessonsFragment : BaseFragment(), LessonsView {

    override val layoutRes: Int = R.layout.fragment_lessons

    companion object {
        val TAG = "LessonsFragment"
            val ID = "SUBJECT_ID"
        fun newInstance(lesson: Lesson) : LessonsFragment{
            val fragment = LessonsFragment()
            val args = Bundle()
            args.putParcelable(ID, lesson)
            fragment.arguments = args
            Log.i(TAG, "newInstance")
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: LessonsPresenter

    @ProvidePresenter
    fun providePresenter(): LessonsPresenter {
        return getKoin().getOrCreateScope(MainScope.LESSONS_SCOPE, named(MainScope.LESSONS_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LESSONS_SCOPE)?.close()
        super.onDestroy()
    }

    private val lessonAdapter = LessonItemAdapter{ it?.let { it1 -> presenter.onLessonItemClick(it1) } }

    var lesson : Lesson? = null

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerLessons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = lessonAdapter
        }
        setTitle()
        setOnClickListener()
        arguments?.apply{
            getParcelable<Lesson>(ID)?.let {
                lesson = it
                presenter.getLessonData(it)
                lesson?.apply {
                    if(isSelected){
                        btnBuyLesson.visibility = View.GONE
                        txtCoursePrice.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setOnClickListener(){
        edtSearchHeader.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?){}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onSerchTextChanged(edtSearchHeader.text.toString())
            }
        })

        txt1.setOnClickListener {
            constraintInfoDetails.visibility = View.VISIBLE
            txtCourseTitle.text = lesson?.title
            txtCourseAuthor.text = lesson?.author
            txtCoursePrice.text = String.format(getString(R.string.dialog_lesson_price),lesson?.price)
            webViewDescription.settings.javaScriptEnabled = true
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorDarkBlueForItem) }?.let { it2 ->
                webViewDescription.setBackgroundColor(
                    it2
                )
            }
            webViewDescription.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
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
                    "<body>"+lesson?.description+"</body>\n" +
                    "</html>", "text/html", "UTF-8", "")

            btnBuyLesson.setOnClickListener {
                lesson?.let { it1 -> PaymentFragment.newInstance(it1) }?.let { it2 ->
                    activity?.supportFragmentManager?.addFragmentWithBackStack(
                        R.id.container_fragment,
                        it2,
                        PaymentFragment.TAG
                    )
                }
            }
        }

        imgCancel.setOnClickListener {
            constraintInfoDetails.visibility = View.GONE
        }

        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        txtShowMore.setOnClickListener {
            arguments?.apply{
                getParcelable<Lesson>(ID)?.let {
                    presenter.getLessonData(it)
                    it.id?.let { it1 -> presenter.getLessonDataMore(it1) }
                }
            }
        }
    }

    private fun setTitle(){
        btnBackHeader.visibility = View.VISIBLE
        edtSearchHeader.visibility = View.VISIBLE
    }

    override fun showLessonData(dataList: MutableList<CourseLessons>){
        lessonAdapter.setData(dataList)
    }

    override fun openLessonDetailFrag(lesson: CourseLessons) {
        lesson.id?.let { LessonDetailsFragment.newInstance(it) }?.let {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.container_fragment,
                it,
                LessonDetailsFragment.TAG
            )
        }
    }
}

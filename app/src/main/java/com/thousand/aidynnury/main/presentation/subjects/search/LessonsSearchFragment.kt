package com.thousand.aidynnury.main.presentation.subjects.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.LessonAdapter
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.subjects.lesson.LessonsFragment
import kotlinx.android.synthetic.main.fragment_lessons_main.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LessonsSearchFragment : BaseFragment(), LessonsSearchView {

    override val layoutRes: Int = R.layout.fragment_lessons_main

    companion object {

        val TAG = "LessonsSearchFragment"

        fun newInstance() : LessonsSearchFragment{
            return LessonsSearchFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: LessonsSearchPresenter

    @ProvidePresenter
    fun providePresenter(): LessonsSearchPresenter {
        return getKoin().getOrCreateScope(MainScope.LESSON_SEARCH_SCOPE, named(MainScope.LESSON_SEARCH_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LESSON_SEARCH_SCOPE)?.close()
        super.onDestroy()
    }

    private val commentAdapter = LessonAdapter{ it?.let { it1 -> presenter.onLessonClick(it1) } }

    override fun setUp(savedInstanceState: Bundle?) {

        recyclerLesson.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }

        setTitle()
        setOcClickListener()
        presenter.getLessonData(1)
    }

    private fun setOcClickListener(){


        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        edtSearchHeader.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?){}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onSerchTextChanged(edtSearchHeader.text.toString())
            }
        })

        edtSearchHeader.requestFocus()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun setTitle(){
        edtSearchHeader.visibility = View.VISIBLE
        imgSearchHeader.visibility = View.VISIBLE
        btnBackHeader.visibility = View.VISIBLE
    }

    override fun showLessonData(dataList: MutableList<Lesson>)= commentAdapter.setData(dataList)

    override fun openLessonDetailFrag(lesson: Lesson) {
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.container_fragment,
            LessonsFragment.newInstance(lesson),
            LessonsFragment.TAG
        )
    }

    override fun openDialog(lesson : Lesson){
        /*val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_lesson_condition, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)
        alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        view.findViewById<TextView>(R.id.txtLessonTitle).text = lesson.title
        view.findViewById<TextView>(R.id.txtLessonPrice).text = String.format(getString(R.string.dialog_lesson_price),lesson.price)
        view.findViewById<TextView>(R.id.txtLessonDescription).text = lesson.description

        view.findViewById<Button>(R.id.btnBuyLesson).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()*/
    }
}

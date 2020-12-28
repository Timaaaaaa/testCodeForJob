package com.thousand.aidynnury.main.presentation.subjects.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.helperapp.global.utils.AppConstants
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.LessonAdapter
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.Version
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.extension.replaceFragment
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.global.utils.MyFirebaseMessagingService
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.auth.AuthFragment
import com.thousand.aidynnury.main.presentation.payment.PaymentFragment
import com.thousand.aidynnury.main.presentation.subjects.lesson.LessonsFragment
import com.thousand.aidynnury.main.presentation.subjects.search.LessonsSearchFragment
import kotlinx.android.synthetic.main.fragment_lessons_main.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LessonsMainFragment : BaseFragment(), LessonsMainView {

    override val layoutRes: Int = R.layout.fragment_lessons_main

    companion object {

        val TAG = "LessonsMainFragment"
        val VARAINT_OF_SHOW = "VARAINT_OF_SHOW"

        fun newInstance(variant : Int) : LessonsMainFragment{
            val fragment = LessonsMainFragment()
            val args = Bundle()
            args.putInt(VARAINT_OF_SHOW, variant)
            fragment.arguments = args
            Log.i(LessonsFragment.TAG, "newInstance")
            return fragment
        }

    }

    @InjectPresenter
    lateinit var presenter: LessonsMainPresenter

    @ProvidePresenter
    fun providePresenter(): LessonsMainPresenter {
        return getKoin().getOrCreateScope(MainScope.LESSON_MAIN_SCOPE, named(MainScope.LESSON_MAIN_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LESSON_MAIN_SCOPE)?.close()
        super.onDestroy()
    }

    private val commentAdapter = LessonAdapter{ it?.let { it1 -> presenter.onLessonClick(it1) } }

    override fun setUp(savedInstanceState: Bundle?) {

        txtTitleHeader.visibility = View.VISIBLE
        txtTitleHeader.text = "Сабақтар"

        recyclerLesson.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }

        setTitle()
        setOnClickListener()
        arguments?.apply {
            if(getInt(VARAINT_OF_SHOW) == AppConstants.MY_LESSONS){
                edtSearchHeader.visibility = View.GONE
                txtSearchHeader.visibility = View.GONE
                imgSearchHeader.visibility = View.GONE
                btnSearchHeader.visibility = View.GONE
                imgSearchHeader1.visibility = View.GONE
                txtTitleHeader.visibility = View.VISIBLE
                txtTitleHeader.text = "Менің сабақтарым"
                btnBackHeader.visibility = View.VISIBLE
                btnBackHeader.setOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }
                swipe_refresh_layout.setOnRefreshListener {

                    presenter.getLessonDataRefresh(AppConstants.MY_LESSONS)
                    Log.i(TAG,"swipe_refresh_layout")


                }
                presenter.getLessonData(AppConstants.MY_LESSONS)}
            else{presenter.getLessonData(1)}
        }

        presenter.loginByToken()
        presenter.version()

    }

    private fun setOnClickListener(){
        swipe_refresh_layout.setOnRefreshListener {

            presenter.getLessonDataRefresh(1)
            Log.i(TAG,"swipe_refresh_layout")


        }
        imgSearchHeader1.visibility = View.GONE


    }

    private fun setTitle(){
        txtSearchHeader.visibility = View.GONE
        imgSearchHeader1.visibility = View.GONE
    }



    override fun showLessonData(dataList: MutableList<Lesson>){
        Log.i(TAG, "" + dataList[0].title)
        commentAdapter.setData(dataList)
        swipe_refresh_layout.isRefreshing = false
    }

    override fun openLessonDetailFrag(lesson: Lesson) {
        if(lesson.author != null){
            LocalStorage.setAuthor(lesson.author!!)
        }
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.container_fragment,
                LessonsFragment.newInstance(lesson),
                LessonsFragment.TAG
            )
    }

    override fun openDialog(lesson : Lesson){
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_lesson_condition, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)
        alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        view.findViewById<TextView>(R.id.txtLessonTitle).text = lesson.title
        view.findViewById<TextView>(R.id.txtLessonPrice).text = String.format(getString(R.string.dialog_lesson_price),lesson.price)
        view.findViewById<TextView>(R.id.txtLessonDescription).text = lesson.description

        view.findViewById<Button>(R.id.btnBuyLesson).setOnClickListener {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.container_fragment,
                PaymentFragment.newInstance(lesson),
                PaymentFragment.TAG
            )
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun showDialogAlert(title : String, body : String){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(title)
        builder?.setMessage(body)
        builder?.setCancelable(false)
        builder?.setPositiveButton("Шығу") { _,_ ->
            LocalStorage.deleteStorageDATA()
            activity?.supportFragmentManager?.replaceFragment(
                R.id.container_fragment,
                AuthFragment.newInstance(),
                AuthFragment.TAG
            )

        }


        builder?.show()
    }

    override fun getUpdate(update : Version){
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_exit, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)
        view.findViewById<TextView>(R.id.txtOrderText).text = "Қосымша Play market-та жаңартылды."
        view.findViewById<TextView>(R.id.txtOrderCancelText).text = "Жанарту"
        view.findViewById<TextView>(R.id.btnCancel).text = "Жанарту"
        view.findViewById<TextView>(R.id.btnCancel).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=com.thousand.aidynnury")
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.btnClose).setOnClickListener {
            alertDialog.dismiss()
        }
        update.android?.apply {
            Log.i("update","" + this)
            if(LocalStorage.getUpdate() < this){
                alertDialog.show()
            }
        }

    }

}

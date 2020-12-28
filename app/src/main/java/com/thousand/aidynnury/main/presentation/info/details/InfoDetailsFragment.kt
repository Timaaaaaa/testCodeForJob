package com.thousand.aidynnury.main.presentation.info.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.PhotoPagerAdapter
import com.thousand.aidynnury.common.adapter.VideoPagerAdapter
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.utils.DateHelper
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_info_details.*
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class InfoDetailsFragment : BaseFragment(), InfoDetailsView {

    override val layoutRes: Int = R.layout.fragment_info_details

    companion object {

        const val TAG = "InfoFragment"
        const val INFO_OBJECT = "INFO_OBJECT"

        fun newInstance(info : Info) : InfoDetailsFragment {
            val fragment = InfoDetailsFragment()
            val args = Bundle()
            args.putParcelable(INFO_OBJECT, info)
            fragment.arguments = args
            return fragment
        }

    }

    @InjectPresenter
    lateinit var presenter: InfoDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): InfoDetailsPresenter {
        return getKoin().getOrCreateScope(MainScope.INFO_DETAILS_SCOPE, named(MainScope.INFO_DETAILS_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.INFO_DETAILS_SCOPE)?.close()
        super.onDestroy()
    }

    var next : Int? = null
    /*var prev : Int? = null
    var timer: TimerTask? = null*/
    var info: Info?= null

    /*var currentPage = 0*/
    private var photoPagerAdapter = PhotoPagerAdapter()
    private var videoPagerAdapter = VideoPagerAdapter()

    override fun setUp(savedInstanceState: Bundle?) {
        setTitle()
        setOnClickListener()
        presenter.getLessonData()
        arguments?.apply {
            getParcelable<Info>(INFO_OBJECT)?.let {
                info = it
                it.id?.let { it1 -> presenter.setInfoData(it1) }
            }
        }

        /*info?.images?.let { photoPagerAdapter.setData(it) }
        imgInfo.adapter = photoPagerAdapter
        circle.setViewPager(imgInfo)

        info?.apply {
            if(videos != null){
                videoInfo.visibility = View.VISIBLE
                info?.videos?.let { videoPagerAdapter.setData(it) }
                videoInfo.adapter = videoPagerAdapter
            }
        }*/


        /*val handler = Handler()

        val update = Runnable {
            if (currentPage == info?.images?.size) {
                currentPage = 0
            }

            if (imgInfo != null)
                imgInfo.setCurrentItem(currentPage++, true)
        }

        timer = object : TimerTask() {

            override fun run() {
                handler.post(update)
            }
        }

        Timer().schedule(timer, 100, 3000)*/
    }

    private fun setOnClickListener(){
        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setTitle(){
        btnBackHeader.visibility = View.VISIBLE
    }

    override fun showInfoData(info : Info){
        /*txtInfoTitle.text = info.title
        txtLessonDescription.text = info.description
        txtInfoDate.text = DateHelper.convertOneFormatToAnother(info.created_at?.substringBefore(" ").toString(),
            "yyyy-MM-dd","dd LLLL, yyyy")*/
        webViewInfoDetails.settings.javaScriptEnabled = true
        webViewInfoDetails.webChromeClient = object : WebChromeClient(){
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
            webViewInfoDetails.setBackgroundColor(
                it2
            )
        }
        txtInfoTitle.text = info.title
        webViewInfoDetails.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
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
                "<body>"+info.description+"</body>\n" +
                "</html>", "text/html", "UTF-8", "")
    }

    override fun showLessonData(dataList: List<Info?>){

    }

    override fun openLessonDetailFrag(id: Int) {

    }
}

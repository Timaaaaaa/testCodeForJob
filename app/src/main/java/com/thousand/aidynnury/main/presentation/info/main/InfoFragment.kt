package com.thousand.aidynnury.main.presentation.info.main

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.InfoAdapter
import com.thousand.aidynnury.entity.Button
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.info.details.InfoDetailsFragment
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import com.thousand.aidynnury.main.presentation.subjects.main.LessonsMainFragment
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.btnLeft
import kotlinx.android.synthetic.main.fragment_info.btnRight
import kotlinx.android.synthetic.main.fragment_info.viewLeft
import kotlinx.android.synthetic.main.fragment_info.viewRight
import kotlinx.android.synthetic.main.fragment_lessons_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class InfoFragment : BaseFragment(), InfoView {

    override val layoutRes: Int = R.layout.fragment_info

    companion object {

        val TAG = "InfoFragment"

        fun newInstance() : InfoFragment {
            return InfoFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    @ProvidePresenter
    fun providePresenter(): InfoPresenter {
        return getKoin().getOrCreateScope(MainScope.INFO_SCOPE, named(MainScope.INFO_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.INFO_SCOPE)?.close()
        super.onDestroy()
    }

    private val commentAdapter = InfoAdapter{ it?.let { it1 -> presenter.onLessonItemClick(it1) } }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerInfo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }

        setOnClickListener()
        presenter.getPostCatsData()
        presenter.getPostCatsData(1)
    }

    private fun setOnClickListener(){
        context?.let{
            viewLeft.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGold))
            viewRight.setBackgroundColor(ContextCompat.getColor(it, R.color.colorWhite))
            btnLeft.setTextColor(ContextCompat.getColor(it, R.color.colorGold))
            btnRight.setTextColor(ContextCompat.getColor(it, R.color.colorWhite))
        }
        swipe_refresh_layoutt.setOnRefreshListener {

            presenter.getPostCatsData(1)
            Log.i(LessonsMainFragment.TAG,"swipe_refresh_layout")


        }
        btnLeft.setOnClickListener {
            presenter.getPostCatsData(1)
            context?.let{
                viewLeft.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGold))
                viewRight.setBackgroundColor(ContextCompat.getColor(it, R.color.colorWhite))
                btnLeft.setTextColor(ContextCompat.getColor(it, R.color.colorGold))
                btnRight.setTextColor(ContextCompat.getColor(it, R.color.colorWhite))
            }
            swipe_refresh_layoutt.setOnRefreshListener {

                presenter.getPostCatsData(1)
                Log.i(LessonsMainFragment.TAG,"swipe_refresh_layout")


            }
        }
        btnRight.setOnClickListener {
            presenter.getPostCatsData(2)
            context?.let{
                viewLeft.setBackgroundColor(ContextCompat.getColor(it, R.color.colorWhite))
                viewRight.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGold))
                btnLeft.setTextColor(ContextCompat.getColor(it, R.color.colorWhite))
                btnRight.setTextColor(ContextCompat.getColor(it, R.color.colorGold))
            }
            swipe_refresh_layoutt.setOnRefreshListener {

                presenter.getPostCatsData(2)
                Log.i(LessonsMainFragment.TAG,"swipe_refresh_layout")


            }
        }
    }


    override fun showLessonData(dataList: List<Info?>){
        commentAdapter.setData(dataList)

        swipe_refresh_layoutt.isRefreshing = false}

    override fun openLessonDetailFrag(id: Info) {
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.container_fragment,
            InfoDetailsFragment.newInstance(id),
            InfoDetailsFragment.TAG
        )
    }

    override fun setButtons(buttons : MutableList<Button>?){
        btnLeft.text = buttons?.get(0)?.name
        btnRight.text = buttons?.get(1)?.name
    }
}

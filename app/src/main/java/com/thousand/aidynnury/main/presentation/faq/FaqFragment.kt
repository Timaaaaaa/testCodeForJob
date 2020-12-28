package com.thousand.aidynnury.main.presentation.faq

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.aidynnury.R
import com.thousand.aidynnury.common.adapter.QuestionAdapter
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_faq.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FaqFragment : BaseFragment(), FaqView {

    override val layoutRes: Int = R.layout.fragment_faq

    companion object {

        val TAG = "InfoFragment"

        fun newInstance() : FaqFragment{
            return FaqFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: FaqPresenter

    @ProvidePresenter
    fun providePresenter(): FaqPresenter {
        return getKoin().getOrCreateScope(MainScope.FAQ_SCOPE, named(MainScope.FAQ_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.FAQ_SCOPE)?.close()
        super.onDestroy()
    }

    private val commentAdapter = QuestionAdapter{ it.let { it1 -> presenter.onLessonItemClick(it1) } }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerQuestion.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }

        presenter.getLessonData()
        swipe_refresh_layouttt.setOnRefreshListener {

            presenter.getLessonData()
            Log.i(TAG, "swipe_refresh_layouttt")

        }
    }

    override fun showLessonData(dataList: List<Question>?){
        swipe_refresh_layouttt.isRefreshing = false
        dataList?.let {
            commentAdapter.setData(
                it
            )
        }
    }


}

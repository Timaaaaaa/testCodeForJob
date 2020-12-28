package com.thousand.aidynnury.main.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.iid.FirebaseInstanceId
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.main.presentation.payment.PaymentFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_policy.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class Policy : BaseFragment()  {

    override val layoutRes: Int = R.layout.fragment_policy

    companion object{

        const val TAG = "Policy"
        const val PolicyText = "PolicyText"

        fun newInstance(policyText : String): Policy{
            val fragment = Policy()
            val args = Bundle()
            args.putString(PolicyText, policyText)
            fragment.arguments = args
            return fragment
        }
    }



    override fun setUp(savedInstanceState: Bundle?) {
        Log.i("setUpPolicy","qweqwqeqw")
        var policyText = arguments?.getString(PolicyText)
        web_view.settings.javaScriptEnabled = true
        web_view.settings.javaScriptCanOpenWindowsAutomatically = true
        web_view.loadData("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                policyText +
                "</body>\n" +
                "</html>" , "text/html", null)

        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.apply {
                    progress_bar.visibility = View.GONE
                }

            }
        }

        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 0) {
                    progress_bar.visibility = View.VISIBLE
                } else if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

}




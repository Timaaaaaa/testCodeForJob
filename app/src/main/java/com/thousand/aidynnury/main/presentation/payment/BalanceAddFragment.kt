package com.thousand.aidynnury.main.presentation.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.helperapp.global.utils.AppConstants
import com.thousand.aidynnury.R
import kotlinx.android.synthetic.main.fragment_balance_add.*
import kotlinx.android.synthetic.main.header_toolbar.*

class BalanceAddFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_balance_add, container, false)



        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBackHeader.visibility = View.VISIBLE

        btnBackHeader.setOnClickListener {
            activity?.onBackPressed()
        }

        arguments?.apply{
            webViewFinance.settings.javaScriptEnabled = true
            webViewFinance.webViewClient = object : WebViewClient(){
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    Log.i(TAG, url)
                    return true
                }
            }
            webViewFinance.loadUrl(/*getString(AppConstants.ARGS_AMOUNT_FINANCE)*/getString(AppConstants.ARGS_AMOUNT_FINANCE).toString())
        }

    }



    companion object {
        var TAG =  "BalanceAddFragment"

        fun newInstance(url : String) : BalanceAddFragment{
            val financeFragment = BalanceAddFragment()
            val args = Bundle()
            args.putString(AppConstants.ARGS_AMOUNT_FINANCE, url)
            financeFragment.arguments = args
            Log.i(TAG, "newInstance")
            return financeFragment
        }
    }
}

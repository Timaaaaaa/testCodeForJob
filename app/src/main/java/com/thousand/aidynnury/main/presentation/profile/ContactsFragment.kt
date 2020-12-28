package com.thousand.aidynnury.main.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.InfoNet
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.presentation.audio.AudioFragment
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsFragment
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import kotlinx.android.synthetic.main.header_toolbar.*

class ContactsFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_contacts


    companion object {
        val TAG = "HomeworkFragment"
        val CONTACTS = "CONTACTS"
        fun newInstance(lessonItem: InfoNet) : ContactsFragment {
            val fragment = ContactsFragment()
            val args = Bundle()
            args.putParcelable(CONTACTS, lessonItem)
            fragment.arguments = args
            Log.i(TAG, "newInstance")
            return fragment
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun setUp(savedInstanceState: Bundle?) {

        btnBackHeader.visibility = View.VISIBLE
        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        arguments?.apply {
            textEmail.text = "Email : " + getParcelable<InfoNet>(CONTACTS)?.email.toString()
            textPhone.text = "Телефон : " + getParcelable<InfoNet>(CONTACTS)?.phone.toString()
        }
    }

}

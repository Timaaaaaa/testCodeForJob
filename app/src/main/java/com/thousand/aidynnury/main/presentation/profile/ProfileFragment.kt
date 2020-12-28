package com.thousand.aidynnury.main.presentation.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.helperapp.global.utils.AppConstants
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.FeedBack
import com.thousand.aidynnury.entity.InfoNet
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.extension.replaceFragment
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.auth.AuthFragment
import com.thousand.aidynnury.main.presentation.subjects.lesson.LessonsFragment
import com.thousand.aidynnury.main.presentation.subjects.main.LessonsMainFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ProfileFragment : BaseFragment(), ProfileView {

    override val layoutRes: Int = R.layout.fragment_profile

    companion object {

        val TAG = "InfoFragment"

        fun newInstance() : ProfileFragment{
            return ProfileFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return getKoin().getOrCreateScope(MainScope.PROFILE_SCOPE, named(MainScope.PROFILE_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.PROFILE_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        setOnClickListener()
        presenter.getLessonData()
        setUserData()
    }

    private fun setOnClickListener(){
        btnMyLessons.setOnClickListener{
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.container_fragment,
                LessonsMainFragment.newInstance(AppConstants.MY_LESSONS),
                LessonsMainFragment.TAG
            )
        }
        btnBuyLesson.setOnClickListener{
            LocalStorage.deleteStorageDATA()
            activity?.supportFragmentManager?.replaceFragment(
                R.id.container_fragment,
                AuthFragment.newInstance(),
                AuthFragment.TAG
            )
        }
        btnConnection.setOnClickListener {
            presenter.getInfo()
        }
        imgtelega.setOnClickListener {
            openDialogTelega()
        }
        imginsta.setOnClickListener {
            openDialog()
        }

        imgyou.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCN9ExjJ_c84dWAtCQ_PB6qQ"))
            startActivity(intent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(){
        txtUserNameLetter.text = LocalStorage.getName()[0].toString()
        txtUserName.text = LocalStorage.getName()
        txtUserPhone.text = "8${LocalStorage.getPhone()}"
    }

    override fun showLessonData(dataList: List<Question>){}

    override fun openLessonDetailFrag(id: Int) {

    }

    @SuppressLint("SetTextI18n")
    override fun showDialog(infoNet: InfoNet){
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.container_fragment,
            ContactsFragment.newInstance(infoNet),
            ContactsFragment.TAG
        )
    }
    private fun openDialog(){
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_insta_accaunts, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)

        view.findViewById<Button>(R.id.btnQory).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/aidyn_nury_qory?igshid=1q7tokrkk5dmu"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnNatije).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/aidyn_nury_natijeleri?igshid=r9kq44azlvhy"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnAidyn_nury).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/aidyn_nury?igshid=ju0j8hhvfwpx"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnaaa).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/aidyn_nury_asxana"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun openDialogTelega(){
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_insta_accaunts, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)

        view.findViewById<TextView>(R.id.txtInsta).text = "Telegram акаунттар"

        view.findViewById<Button>(R.id.btnQory).text = "aidyn_nury_1977"
        view.findViewById<Button>(R.id.btnQory).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/aidyn_nury_1977"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnNatije).text = "aidyn_nury_sabak"
        view.findViewById<Button>(R.id.btnNatije).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/aidyn_nury_sabak"))
            startActivity(intent)
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnAidyn_nury).visibility = View.GONE
        view.findViewById<Button>(R.id.btnaaa).visibility = View.GONE

        alertDialog.show()
    }
}

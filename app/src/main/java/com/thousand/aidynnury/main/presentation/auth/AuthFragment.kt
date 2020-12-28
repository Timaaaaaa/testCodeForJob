package com.thousand.aidynnury.main.presentation.auth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.extension.replaceFragment
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception
import kotlin.concurrent.fixedRateTimer

class AuthFragment : BaseFragment(), AuthView {

    override val layoutRes: Int = R.layout.fragment_auth

    companion object {

        const val TAG = "InfoFragment"

        fun newInstance() : AuthFragment{
            return AuthFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun providePresenter(): AuthPresenter {
        return getKoin().getOrCreateScope(MainScope.AUTH_SCOPE, named(MainScope.AUTH_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.AUTH_SCOPE)?.close()
        super.onDestroy()
    }

    var phone = ""
    var policyText = ""

    override fun setUp(savedInstanceState: Bundle?) {

        val str = SpannableStringBuilder("Мен құпиялылық саясаты мен пайдалану шарттарын оқығанымды және түсінгенімді растаймын")
        str.setSpan(

            ForegroundColorSpan(Color.YELLOW),
            3,
            46,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        txtPolicy.text = str

        setOnClickListener()
        presenter.getLessonData()

        presenter.getInfo()
        MaskedTextChangedListener.installOn(
            edtPhoneAuth,
            getString(R.string.format_phone_number),
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    phone = if (maskFilled) {
                        extractedValue
                    } else {
                        "8"
                    }
                }
            }
        )

        imgtelegaa.setOnClickListener {
            openDialogTelega()
        }

        imginstaa.setOnClickListener {
           openDialog()
        }

        imgyoua.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCN9ExjJ_c84dWAtCQ_PB6qQ"))
            startActivity(intent)
        }
    }

    @SuppressLint("HardwareIds")
    private fun setOnClickListener(){
        checkAgreement.isChecked = true
        btnSignInAuth.setOnClickListener {
            if(checkAgreement.isChecked){
                try{

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (activity?.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_DENIED){
                            //permission was not enabled
                            val permission = arrayOf(Manifest.permission.READ_PHONE_STATE)
                            //show popup to request permission
                            requestPermissions(permission, 1000)
                        }
                        else{
                            //permission already granted


                            var androidID : String? = ""
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                androidID = Settings.Secure.getString(
                                    context?.getContentResolver(),
                                    Settings.Secure.ANDROID_ID);}
                            else{
                                androidID = activity?.let { it1 -> getIMEI(it1) }
                            }
                            if (androidID != null) {
                                presenter.auth(phone = phone,password = edtPasswordAuth.text.toString(), device_id = androidID)
                            }
                        }
                    }
                    else{


                        var androidID : String? = ""
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            androidID = Settings.Secure.getString(
                                context?.getContentResolver(),
                                Settings.Secure.ANDROID_ID);}
                        else{
                            androidID = activity?.let { it1 -> getIMEI(it1) }
                        }
                        Log.i("DEVICE_ID", androidID)
                        if (androidID != null) {
                            presenter.auth(phone = phone,password = edtPasswordAuth.text.toString(), device_id = androidID)
                        }
                    }

                }catch (e : Exception){
                    showMessInvalid(e.message.toString())
                    showMessInvalid(e.toString())
                }
            }else{
                showMessInvalid("Келісімге қол қойыңыз!")
            }




        }

    }

    @SuppressLint("MissingPermission")
    fun getIMEI(activity: Activity): String {
        val telephonyManager = activity
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.deviceId
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        try{
            when(requestCode){
                1000 -> {
                    if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                        var androidID : String? = ""
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            androidID = Settings.Secure.getString(
                                context?.getContentResolver(),
                                Settings.Secure.ANDROID_ID);}
                        else{
                            androidID = activity?.let { it1 -> getIMEI(it1) }
                        }
                        if (androidID != null) {
                            presenter.auth(phone = phone,password = edtPasswordAuth.text.toString(), device_id = androidID)
                        }
                    }
                    else{
                        //permission from popup was denied
                        context?.apply{
                            Toast.makeText(this, "Разрешение отклонено вами!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }catch (e : Exception){
            showMessInvalid(e.message.toString())
            showMessInvalid(e.toString())
        }


    }

    override fun showLessonData(dataList: List<Question>){}

    override fun openProfileFrag() {
        activity?.supportFragmentManager?.replaceFragment(
            R.id.container_fragment,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
    }

    override fun showMessInvalid(mess : String){
        context?.apply {
            Toast.makeText(this,mess, Toast.LENGTH_SHORT).show()
        }
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



    override fun setPolicy(policyText : String){
        this.policyText = policyText
        Log.i("policymoment","setPolicy")
        txtPolicy.setOnClickListener {
            Log.i("policymoment","setOnClickListener")
            fragmentManager?.addFragmentWithBackStack(
                R.id.container_fragment,
                Policy.newInstance(policyText),
                Policy.TAG
            )
        }
    }
}

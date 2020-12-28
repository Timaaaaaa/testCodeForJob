package com.thousand.aidynnury.main.presentation.payment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.FeedBack
import com.thousand.aidynnury.entity.InfoNet
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.Question
import com.thousand.aidynnury.global.base.BaseFragment
import com.thousand.aidynnury.global.extension.addFragmentWithBackStack
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.subjects.main.LessonsMainFragment
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.header_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PaymentFragment : BaseFragment(), PaymentView {

    override val layoutRes: Int = R.layout.fragment_payment

    companion object {

        val TAG = "InfoFragment"
        val LESSON_OBJECT = "LESSON_OBJECT_PAYMENT"

        fun newInstance(lesson: Lesson) : PaymentFragment{
            val fragment = PaymentFragment()
            val args = Bundle()
            args.putParcelable(LESSON_OBJECT, lesson)
            fragment.arguments = args
            return fragment
        }

    }

    @InjectPresenter
    lateinit var presenter: PaymentPresenter

    @ProvidePresenter
    fun providePresenter(): PaymentPresenter {
        return getKoin().getOrCreateScope(MainScope.PAYMENT_SCOPE, named(MainScope.PAYMENT_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.PAYMENT_SCOPE)?.close()
        super.onDestroy()
    }


    var phone = ""

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.getInfo()
        setTitle()
        setOnClickListener()
    }

    override fun openMainPage(url : String){
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.container_fragment,
            BalanceAddFragment.newInstance(url),
            LessonsMainFragment.TAG
        )
    }

    private fun setTitle(){
        if(LocalStorage.getName()!="1"){
            edtUserPhone.visibility = View.GONE
            edtUserEmail.visibility = View.GONE
            edtUserName.visibility = View.GONE
        }
        btnBackHeader.visibility = View.VISIBLE

        MaskedTextChangedListener.installOn(
            edtUserPhone,
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
    }

    @SuppressLint("HardwareIds")
    private fun setOnClickListener() {
        btnBackHeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        //radioBtnCard.isChecked = true
        radioBtnCash.isChecked = true

        checkboxPayType.setOnCheckedChangeListener { _, checkedId ->

                when(checkedId){
                    R.id.radioBtnCash->{
                        Log.i("Type", "cash")
                        advise.visibility = View.VISIBLE
                        edtUserName.visibility = View.GONE
                        edtUserEmail.visibility = View.GONE
                        edtUserPhone.visibility = View.GONE
                        btnBuyLessonPayment.visibility = View.GONE
                    }
                    R.id.radioBtnCard->{
                        Log.i("Type", "card")


                        advise.visibility = View.GONE
                        edtUserName.visibility = View.VISIBLE
                        edtUserEmail.visibility = View.VISIBLE
                        edtUserPhone.visibility = View.VISIBLE
                        btnBuyLessonPayment.visibility = View.VISIBLE
                        if(LocalStorage.getName()!="1"){
                            edtUserName.visibility = View.GONE
                            edtUserEmail.visibility = View.GONE
                            edtUserPhone.visibility = View.GONE
                            btnBuyLessonPayment.visibility = View.VISIBLE
                        }
                    }
                }

        }

        btnBuyLessonPayment.setOnClickListener{
            val androidId : String  = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
            if(LocalStorage.getName()!="1"){
                arguments?.apply {

                    getParcelable<Lesson>(
                        LESSON_OBJECT)?.id?.let { it1 -> presenter.getLessonByu(name = LocalStorage.getName(), phone = LocalStorage.getPhone(),email = LocalStorage.getMail(), id = it1,androidId =androidId) }
                }
            }else{
                if(TextUtils.isEmpty(edtUserName.text.toString()) && TextUtils.isEmpty(edtUserEmail.text.toString()) && TextUtils.isEmpty(edtUserPhone.text.toString()) && phone !="8"){
                    context?.apply {
                        Toast.makeText(this,"Барлык акпаратты енгизиниз", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    arguments?.apply {
                        Log.i(TAG, phone)
                        getParcelable<Lesson>(
                            LESSON_OBJECT)?.id?.let { it1 -> presenter.getLessonByu(name = edtUserName.text.toString(), phone = phone,email = edtUserEmail.text.toString(), id = it1,androidId =androidId) }
                    }
                }
            }


        }
    }



    override fun showLessonData(dataList: List<Question>){}

    override fun openLessonDetailFrag(id: Int) {

    }

    override fun showDialog(feedBack: FeedBack){
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_lesson_condition, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)
        alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        view.findViewById<TextView>(R.id.txtLessonTitle).text = "Email : " + feedBack.email.toString()
        view.findViewById<TextView>(R.id.txtLessonPrice).text = "Телефон : " + feedBack.phone.toString()
        view.findViewById<TextView>(R.id.txtLessonDescription).visibility = View.GONE

        view.findViewById<Button>(R.id.btnBuyLesson).text = "Конырау шалу"
        view.findViewById<Button>(R.id.btnBuyLesson).setOnClickListener {

            /*val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+feedBack.phone))
            startActivity(intent)*/
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun showMessInvalid(mess : String){
        Toast.makeText(context,mess,Toast.LENGTH_SHORT).show()
    }

    override fun editTextPayment(infoNet : InfoNet){
        arguments?.apply {
            text3.text = infoNet.kaspi_description

            text4.text = String.format("Whatsapp: %s", infoNet.whatsapp)

            text5.text = String.format("Каспи: %s", infoNet.kaspi)
        }
    }
}

package com.thousand.aidynnury.main.presentation.auth

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.helperapp.global.utils.AppConstants
import com.example.helperapp.global.utils.ErrorHandler
import com.google.gson.Gson
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.entity.*
import com.thousand.aidynnury.global.presentation.BasePresenter
import com.thousand.aidynnury.global.service.ApiModelHelper
import com.thousand.aidynnury.main.interactor.UserInteractor


@InjectViewState
class AuthPresenter(
    private val userInteractor: UserInteractor,private val errorHandler: ErrorHandler
): BasePresenter<AuthView>() {

    val TAG = "AuthPresenter"
    var lessons = listOf(
        Question(id =1, ask = "жугуруду уйрену",answer = "27 қаңтар, 2020 жыл")
    )

    fun getLessonData(){
        viewState.showLessonData(lessons)
    }

    fun auth(phone : String , password : String, device_id : String){
        userInteractor.auth(email = phone, password = password, deviceId = device_id).subscribe({

            Log.i(TAG, it.toString())

                    val user: User? = it
                    if(user!= null){
                        LocalStorage.setName(user.name.toString())
                        LocalStorage.setPhone(user.phone.toString())
                        LocalStorage.setAccessToken(user.token.toString())
                        Log.i(TAG, user.name.toString())
                        viewState.openProfileFrag()
                    }
            }
        ,{
                it.printStackTrace()

                val message = ApiModelHelper.getErrorMessage(it)
                message?.let { it1 -> viewState.showMessInvalid(it1) }
        }).connect()
    }

    fun onLessonItemClick(lesson : Question){
    }

    fun getInfo(){
        Log.i("policymoment","setPolicy300")
        userInteractor.getInfo().subscribe({
            Log.i("policymoment","setPolicy200")
            when(it.code()){

                200->{
                    Log.i("policymoment","setPolicy100")
                    Log.i(TAG, it.body()?.email.toString())
                    it.body()?.let { it1 -> it1.privacy_policy?.let { it2 -> viewState.setPolicy(it2) } }
                }
                else->{

                }
            }
        },{

        }).connect()
    }

}
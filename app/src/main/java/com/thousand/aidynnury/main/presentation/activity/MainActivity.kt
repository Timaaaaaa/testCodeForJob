package com.thousand.aidynnury.main.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.aidynnury.global.utils.LocalStorage
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.global.extension.replaceFragment
import com.thousand.aidynnury.global.extension.visible
import com.thousand.aidynnury.global.utils.MyFirebaseMessagingService
import com.thousand.aidynnury.main.di.MainScope
import com.thousand.aidynnury.main.presentation.auth.AuthFragment
import com.thousand.aidynnury.main.presentation.faq.FaqFragment
import com.thousand.aidynnury.main.presentation.info.details.InfoDetailsFragment
import com.thousand.aidynnury.main.presentation.info.main.InfoFragment
import com.thousand.aidynnury.main.presentation.profile.ProfileFragment
import com.thousand.aidynnury.main.presentation.subjects.main.LessonsMainFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), MainActivityView {

    private val TAG = "MainActivity"

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter(): MainActivityPresenter {
        return getKoin().getOrCreateScope(MainScope.MAIN_ACTIVITY_SCOPE, named(MainScope.MAIN_ACTIVITY_SCOPE)).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main)

        setUp()
        supportFragmentManager.replaceFragment(
            R.id.container_fragment,
            LessonsMainFragment.newInstance(1),
            LessonsMainFragment.TAG
        )
        onNewIntent(intent) 
    }

    private fun setUp(){

        FirebaseMessaging.getInstance().subscribeToTopic("newPost")
            .addOnCompleteListener {
            }

        FirebaseMessaging.getInstance().subscribeToTopic("newCourse")
            .addOnCompleteListener {
            }

        FirebaseMessaging.getInstance().subscribeToTopic("newQuestionAnswer")
            .addOnCompleteListener {
            }



        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_subjects->{
                    supportFragmentManager.replaceFragment(
                        R.id.container_fragment,
                        LessonsMainFragment.newInstance(1),
                        LessonsMainFragment.TAG
                    )
                }
                R.id.navigation_info->{
                    supportFragmentManager.replaceFragment(
                        R.id.container_fragment,
                        InfoFragment.newInstance(),
                        InfoFragment.TAG
                    )
                }
                R.id.navigation_FAQ->{
                    supportFragmentManager.replaceFragment(
                        R.id.container_fragment,
                        FaqFragment.newInstance(),
                        FaqFragment.TAG
                    )
                }
                R.id.navigation_profile->{
                    if(LocalStorage.getName() == "1"){
                        supportFragmentManager.replaceFragment(
                            R.id.container_fragment,
                            AuthFragment.newInstance(),
                            AuthFragment.TAG
                        )
                    }
                    else{
                        supportFragmentManager.replaceFragment(
                            R.id.container_fragment,
                            ProfileFragment.newInstance(),
                            ProfileFragment.TAG
                        )
                    }

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onNewIntent(intent: Intent?) {
        Log.i("TAGG", "intent")

            intent?.getStringExtra("questionAnswer")?.let {type->
                supportFragmentManager.replaceFragment(
                    R.id.container_fragment,
                    FaqFragment.newInstance(),
                    FaqFragment.TAG
                )
            }


        intent?.getStringExtra("type")?.let {type->
            intent.getStringExtra("id")?.let {id->
                Log.i("TAGG", "$id $type")
                supportFragmentManager.replaceFragment(
                    R.id.container_fragment,
                    InfoDetailsFragment.newInstance(Info(id = Integer.parseInt(id))),
                    InfoDetailsFragment.TAG
                )
            }
        }

        super.onNewIntent(intent)

    }

    override fun showProgressBar(show: Boolean) = progressBar.visible(show)

}

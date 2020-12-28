package com.thousand.aidynnury.main.di

import com.thousand.aidynnury.main.presentation.activity.MainActivityPresenter
import com.thousand.aidynnury.main.presentation.audio.AudioPresenter
import com.thousand.aidynnury.main.presentation.auth.AuthPresenter
import com.thousand.aidynnury.main.presentation.faq.FaqPresenter
import com.thousand.aidynnury.main.presentation.info.details.InfoDetailsPresenter
import com.thousand.aidynnury.main.presentation.info.main.InfoPresenter
import com.thousand.aidynnury.main.presentation.payment.PaymentPresenter
import com.thousand.aidynnury.main.presentation.profile.ProfilePresenter
import com.thousand.aidynnury.main.presentation.subjects.details.LessonDetailsPresenter
import com.thousand.aidynnury.main.presentation.subjects.lesson.LessonsPresenter
import com.thousand.aidynnury.main.presentation.subjects.main.LessonsMainPresenter
import com.thousand.aidynnury.main.presentation.subjects.search.LessonsSearchPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    scope(named(MainScope.MAIN_ACTIVITY_SCOPE)) {
        scoped { MainActivityPresenter() }
    }

    scope(named(MainScope.LESSON_MAIN_SCOPE)) {
        scoped { LessonsMainPresenter(get()) }
    }

    scope(named(MainScope.LESSON_SEARCH_SCOPE)) {
        scoped { LessonsSearchPresenter(get()) }
    }

    scope(named(MainScope.LESSONS_SCOPE)) {
        scoped { LessonsPresenter(get()) }
    }

    scope(named(MainScope.LESSON_DETAILS_SCOPE)) {
        scoped { LessonDetailsPresenter(get()) }
    }

    scope(named(MainScope.INFO_SCOPE)) {
        scoped { InfoPresenter(get()) }
    }

    scope(named(MainScope.FAQ_SCOPE)) {
        scoped { FaqPresenter(get()) }
    }

    scope(named(MainScope.INFO_DETAILS_SCOPE)) {
        scoped { InfoDetailsPresenter(get()) }
    }

    scope(named(MainScope.AUTH_SCOPE)) {
        scoped { AuthPresenter(get(),get()) }
    }

    scope(named(MainScope.PROFILE_SCOPE)) {
        scoped { ProfilePresenter(get()) }
    }

    scope(named(MainScope.PAYMENT_SCOPE)) {
        scoped { PaymentPresenter(get()) }
    }

    scope(named(MainScope.AUDIO_SCOPE)) {
        scoped { AudioPresenter() }
    }
}


object MainScope {
    const val MAIN_ACTIVITY_SCOPE = "MainActivityScope"
    const val LESSON_MAIN_SCOPE = "LessonMainScope"
    const val LESSON_SEARCH_SCOPE = "LessonSearchScope"
    const val LESSONS_SCOPE = "LessonsScope"
    const val LESSON_DETAILS_SCOPE = "LessonDetailsScope"
    const val INFO_DETAILS_SCOPE = "InfoDetailsScope"
    const val INFO_SCOPE = "InfoScope"
    const val FAQ_SCOPE = "FaqScope"
    const val AUTH_SCOPE = "AuthScope"
    const val PROFILE_SCOPE = "ProfileScope"
    const val PAYMENT_SCOPE = "PaymentScope"
    const val AUDIO_SCOPE = "AudioScope"

}

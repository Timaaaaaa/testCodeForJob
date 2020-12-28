package com.thousand.aidynnury.main.interactor

import com.google.gson.JsonObject
import com.thousand.aidynnury.entity.*
import com.thousand.aidynnury.global.system.SchedulersProvider
import com.thousand.aidynnury.main.repository.UserRepository
import io.reactivex.Single
import retrofit2.Response

class UserInteractor(
    private val userRepository: UserRepository,
    private val schedulersProvider: SchedulersProvider
){

    fun auth(email : String , password : String,deviceId: String): Single<User> =
        userRepository.auth(email = email, password = password,deviceId=deviceId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCategory(token : String, page : Int): Single<Response<JsonObject>> =
        userRepository.getCategory(token = token , page = page)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyCourses(token : String, page : Int): Single<Response<JsonObject>> =
        userRepository.getMyCourses(token = token , page = page)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getLessons(token : String, page : Int, id : Int): Single<Response<JsonObject>> =
        userRepository.getLessons(token = token , page = page, id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getPosts(token : String, page : Int ): Single<Response<JsonObject>> =
        userRepository.getPosts(token = token , page = page )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getQuestion(): Single<Response<MutableList<Question>>> =
        userRepository.getQuestion()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getPostCats(): Single<Response<MutableList<Button>>> =
        userRepository.getPostCats()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getFeedBack(): Single<Response<FeedBack>> =
        userRepository.getFeedBack()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun version(): Single<Response<Version>> =
        userRepository.version()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCourseBuy(phone : String, name : String,email : String,deviceId : String, id : Int): Single<JsonObject> =
        userRepository.getCourseBuy(phone = phone , name= name, deviceId= deviceId, id = id,email=email)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun loginByToken(token : String): Single<Response<String>> =
        userRepository.loginByToken(token = token )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getInfo(): Single<Response<InfoNet>> =
        userRepository.getInfo( )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getPostById(post_id : Int): Single<Response<Info>> =
        userRepository.getPost(post_id = post_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getLessonById(token: String,lesson_id : Int): Single<Response<LessonItem>> =
        userRepository.getLessonById(token = token,lesson_id = lesson_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getLessonByCourseId(course_id : Int): Single<Response<List<CourseLessons>>> =
        userRepository.getLessonByCourseId(course_id = course_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
}
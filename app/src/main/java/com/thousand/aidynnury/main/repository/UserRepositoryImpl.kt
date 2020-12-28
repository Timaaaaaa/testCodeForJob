package com.thousand.aidynnury.main.repository


import com.google.gson.JsonObject
import com.thousand.aidynnury.entity.*
import com.thousand.aidynnury.global.service.ServerService
import io.reactivex.Single
import retrofit2.Response

class UserRepositoryImpl(
    private val serverService: ServerService
): UserRepository {

   override fun auth(email : String, password : String,deviceId: String): Single<User> =
        serverService.auth(email = email, password = password,deviceId=deviceId)


    override fun getCategory(token : String, page : Int): Single<Response<JsonObject>> =
        serverService.getCategory(token = token, page = page)

    override fun getMyCourses(token : String, page : Int): Single<Response<JsonObject>> =
        serverService.getMyCourses(token = token, page = page)

    override fun getLessons(token : String, page : Int, id : Int): Single<Response<JsonObject>> =
        serverService.getLessons(token = token, page = page, id = id)

    override fun getPosts(token : String, page : Int ): Single<Response<JsonObject>> =
        serverService.getPosts(token = token, page = page )

    override fun getQuestion( ): Single<Response<MutableList<Question>>> =
        serverService.getQuestion( )

    override fun getPostCats( ): Single<Response<MutableList<Button>>> =
        serverService.getPostCats( )

    override fun getFeedBack( ): Single<Response<FeedBack>> =
        serverService.getFeedBack()

    override fun version(): Single<Response<Version>> =
        serverService.version()

    override fun getCourseBuy(phone : String, name : String, email : String,deviceId : String, id : Int): Single<JsonObject> =
        serverService.getCourseBuy(phone = phone, name = name, email=email,device_id = deviceId, id = id)

    override fun loginByToken(token : String): Single<Response<String>> =
        serverService.loginByToken(token = token)

    override fun getInfo( ): Single<Response<InfoNet>> =
        serverService.getInfo()

    override fun getPost(post_id : Int ): Single<Response<Info>> =
        serverService.getPostById(post_id = post_id)

    override fun getLessonById(token: String, lesson_id : Int ): Single<Response<LessonItem>> =
        serverService.getLessonById(lesson_id = lesson_id,token = token)

    override fun getLessonByCourseId(course_id : Int ): Single<Response<List<CourseLessons>>> =
        serverService.getLessonByCourseId(course_id = course_id)
}
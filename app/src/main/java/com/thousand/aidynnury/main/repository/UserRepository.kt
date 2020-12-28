package com.thousand.aidynnury.main.repository


import com.google.gson.JsonObject
import com.thousand.aidynnury.entity.*
import io.reactivex.Single
import retrofit2.Response

interface UserRepository{
    fun auth(email : String, password : String,deviceId: String): Single<User>

    fun getCategory(token : String, page : Int): Single<Response<JsonObject>>

    fun getLessons(token : String, page : Int, id : Int): Single<Response<JsonObject>>

    fun getPosts(token : String, page : Int ): Single<Response<JsonObject>>

    fun getQuestion( ): Single<Response<MutableList<Question>>>

    fun getPostCats( ): Single<Response<MutableList<Button>>>

    fun getMyCourses(token : String, page : Int): Single<Response<JsonObject>>

    fun getFeedBack( ): Single<Response<FeedBack>>

    fun version(): Single<Response<Version>>

    fun getCourseBuy(phone : String, name : String, email : String,deviceId : String, id : Int): Single<JsonObject>

    fun loginByToken(token : String): Single<Response<String>>

    fun getInfo( ): Single<Response<InfoNet>>

    fun getPost(post_id : Int ): Single<Response<Info>>

    fun getLessonById(token: String,lesson_id : Int ): Single<Response<LessonItem>>

    fun getLessonByCourseId(course_id : Int ): Single<Response<List<CourseLessons>>>
}
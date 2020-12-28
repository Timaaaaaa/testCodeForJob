package com.thousand.aidynnury.global.service

import com.google.gson.JsonObject
import com.thousand.aidynnury.entity.*
import com.thousand.aidynnury.global.service.Endpoints
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ServerService {

    @FormUrlEncoded
    @POST(Endpoints.AUTH)
    fun auth(
        @Field("phone") email: String,
        @Field("password") password: String,
        @Field("device_id") deviceId: String
    ): Single<User>

    @GET(Endpoints.COURSES)
    fun getCategory(
        @Header("token") token: String,
        @Query("page") page : Int
    ): Single<Response<JsonObject>>

    @GET(Endpoints.MY_COURSES)
    fun getMyCourses(
        @Header("token") token: String,
        @Query("page") page : Int
    ): Single<Response<JsonObject>>

    @GET(Endpoints.LESSONS)
    fun getLessons(
        @Header("token") token: String,
        @Query("page") page : Int,
        @Query("course_id") id : Int
    ): Single<Response<JsonObject>>

    @GET(Endpoints.POSTS)
    fun getPosts(
        @Header("token") token: String,
        @Query("page") page : Int
    ): Single<Response<JsonObject>>

    @GET(Endpoints.QUESTION_ANSWER)
    fun getQuestion(): Single<Response<MutableList<Question>>>

    @GET(Endpoints.POST_CATS)
    fun getPostCats(): Single<Response<MutableList<Button>>>

    @GET(Endpoints.FEEDBACK)
    fun getFeedBack(): Single<Response<FeedBack>>

    @FormUrlEncoded
    @POST(Endpoints.COURSE_BUY)
    fun getCourseBuy(
        @Field("phone") phone: String,
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("device_id") device_id : String,
        @Field("course_id") id : Int
    ): Single<JsonObject>

    @FormUrlEncoded
    @POST(Endpoints.LOGIN_BY_TOKEN)
    fun loginByToken(
        @Field("token") token: String
    ): Single<Response<String>>


    @GET(Endpoints.GET_INFO)
    fun getInfo(
    ): Single<Response<InfoNet>>

    @GET(Endpoints.GET_POST)
    fun getPostById(
        @Query("post_id") post_id : Int
    ): Single<Response<Info>>

    @GET(Endpoints.GET_LESSON_BY_ID)
    fun getLessonById(

        @Header("token") token: String,
        @Query("lesson_id") lesson_id : Int
    ): Single<Response<LessonItem>>

    @GET(Endpoints.GET_LESSONS_BY_COURSE_ID)
    fun getLessonByCourseId(
        @Query("course_id") course_id : Int
    ): Single<Response<List<CourseLessons>>>

    @GET(Endpoints.VERSION)
    fun version(): Single<Response<Version>>
}
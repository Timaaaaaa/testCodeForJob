package com.thousand.aidynnury.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.CourseLessons
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.entity.LessonItem
import com.thousand.aidynnury.global.extension.setImageUrl
import kotlinx.android.synthetic.main.item_lesson.view.*
import kotlinx.android.synthetic.main.item_subject.view.*

class LessonItemAdapter(val OnAdressItemClickListener: (CourseLessons?) -> Unit): RecyclerView.Adapter<LessonItemAdapter.MyViewHolder>(){

    private var dataList: List<CourseLessons?> = listOf()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    fun setData(dataList: List<CourseLessons?>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceAsColor")
        fun bind(courseLesson: CourseLessons?) {
            context?.apply {
                itemView.apply {
                    courseLesson?.apply {
                        txtLessonNameItem.text = title
                        Log.i("LessonItemAdapter", "http://194.4.58.92/$video_fon")
                        imgMain.setImageUrl("http://194.4.58.92/$video_fon")
                        if(allowed){
                            imgBlock.visibility = View.GONE
                        }
                    }
                    setOnClickListener { OnAdressItemClickListener.invoke(courseLesson) }
                }
            }
        }

    }

}

package com.thousand.aidynnury.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.extension.setImageUrl
import kotlinx.android.synthetic.main.item_subject.view.*

class LessonAdapter(val OnAdressItemClickListener: (Lesson?) -> Unit): RecyclerView.Adapter<LessonAdapter.MyViewHolder>(){

    private var dataList: MutableList<Lesson> = mutableListOf()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    fun setData(dataList: MutableList<Lesson>){
        Log.i("ADAPTERL", dataList.toString())
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceAsColor")
        fun bind(toAddress: Lesson?) {
            context?.apply {
                itemView.apply {
                    toAddress?.apply {
                        Log.i("ADAPTERL", title)
                        txtLessonIndex.text = String.format(getString(R.string.label_subjects_lesson),index)
                        txtLessonAuthor.text = author
                        imgItem.setImageUrl("http://194.4.58.92/$image")
                        imgKorona.setImageDrawable(getDrawable(R.drawable.icon_08))
                        viewSelection.setBackgroundResource(R.color.colorGreyDefault)
                        txtLessonName.text = title
                        if (isSelected) {
                            imgKorona.setImageDrawable(getDrawable(R.drawable.icon_01))
                            viewSelection.setBackgroundResource(R.color.colorGold)
                        }
                    }
                    setOnClickListener { OnAdressItemClickListener.invoke(toAddress) }
                }
            }
        }
    }

}

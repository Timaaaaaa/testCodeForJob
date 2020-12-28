package com.thousand.aidynnury.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.Info
import com.thousand.aidynnury.entity.Lesson
import com.thousand.aidynnury.global.di.ServiceProperties.SERVER_URL_IMAGE
import com.thousand.aidynnury.global.extension.setImageUrl
import com.thousand.aidynnury.global.utils.DateHelper
import kotlinx.android.synthetic.main.item_info.view.*
import kotlinx.android.synthetic.main.item_subject.view.*

class InfoAdapter(val OnAdressItemClickListener: (Info?) -> Unit): RecyclerView.Adapter<InfoAdapter.MyViewHolder>(){

    private var dataList: List<Info?> = listOf()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    fun setData(dataList: List<Info?>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(toAddress: Info?) {
            context?.apply {
                itemView.apply {
                    toAddress?.apply {
                        txtInfoName.text = title
                        imgMain.setImageUrl(SERVER_URL_IMAGE + images?.get(0).toString())
                        txtInfoDate.text = "$show"
                    }
                    setOnClickListener { OnAdressItemClickListener.invoke(toAddress) }
                }
            }
        }
    }

}

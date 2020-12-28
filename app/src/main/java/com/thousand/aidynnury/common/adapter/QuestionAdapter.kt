package com.thousand.aidynnury.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thousand.aidynnury.R
import com.thousand.aidynnury.entity.Question
import kotlinx.android.synthetic.main.item_question.view.*

class QuestionAdapter(val OnVideoItemClickListener: (Question) -> Unit): RecyclerView.Adapter<QuestionAdapter.MyViewHolder>(){

    private var dataList: List<Question?> = listOf()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    fun setData(dataList: List<Question>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(question: Question?) {
            context?.apply {
                itemView.apply {
                    question?.apply {
                        txtQuestion.text = ask
                        if(isSelected){
                            imgQuestionStatus.setImageResource(R.drawable.icon_1)
                            txtAnswer.visibility = View.VISIBLE
                        }else{
                            imgQuestionStatus.setImageResource(R.drawable.icon_11)
                            txtAnswer.visibility = View.GONE
                        }
                        txtAnswer.text = answer
                    }

                    setOnClickListener { question?.let { it1 -> OnVideoItemClickListener.invoke(it1) } }
                }
            }
        }
    }

}
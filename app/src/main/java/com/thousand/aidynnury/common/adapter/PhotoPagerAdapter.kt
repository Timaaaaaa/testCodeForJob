package com.thousand.aidynnury.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.thousand.aidynnury.R
import com.thousand.aidynnury.global.di.ServiceProperties.SERVER_URL_IMAGE
import com.thousand.aidynnury.global.extension.setImageUrl

class PhotoPagerAdapter() : PagerAdapter() {

    var images : List<String> = listOf()

    fun setData(currentImages : List<String>){
        images = currentImages
        notifyDataSetChanged()
    }






    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.pager_item, null)
        val imageView : ImageView = view.findViewById(R.id.image)
        imageView.setImageUrl(SERVER_URL_IMAGE + images[position].toString())
        container.addView(view)
        return view
    }

    /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    /*
        Returns the count of the total pages
        */
    override fun getCount(): Int {
        return images.size
    }

    /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }
}
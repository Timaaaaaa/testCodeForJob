package com.thousand.aidynnury.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.thousand.aidynnury.R
import com.thousand.aidynnury.global.di.ServiceProperties.SERVER_URL_IMAGE
import com.thousand.aidynnury.global.extension.setImageUrl
import kotlinx.android.synthetic.main.fragment_lesson_details.*

class VideoPagerAdapter() : PagerAdapter() {

    var videos : List<String> = listOf()

    fun setData(currentImages : List<String>){
        videos = currentImages
        notifyDataSetChanged()
    }






    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.pager_video_item, null)
        val third_party_player_view_w : com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView = view.findViewById(R.id.third_party_player_view_w)
        third_party_player_view_w.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                videos[position].replace("https://youtu.be/", "")?.let { it1 ->
                    youTubePlayer.cueVideo(it1, 0f)
                }
            }

        })
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
        return videos.size
    }

    /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }
}
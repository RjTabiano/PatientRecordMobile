package com.example.patientrecord.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.patientrecord.R

class ImagePagerAdapter(private val context: Context) : PagerAdapter() {

    private val images = arrayOf(
        R.drawable.consultation,
        R.drawable.pediatrics,
        R.drawable.obgyne
    )

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_image, container, false)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(images[position])
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
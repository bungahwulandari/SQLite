package com.example.sqlite

import android.graphics.Typeface
import android.widget.TextView

object AppType {
    fun title(tv: TextView) {
        tv.textSize = 22f
        tv.setTypeface(null, Typeface.BOLD)
        tv.setTextColor(AppColors.TEXT)
    }
}

package com.example.sqlite

import android.graphics.Typeface
import android.widget.Button

object AppTheme {
    fun primaryButton(btn: Button) {
        btn.setBackgroundColor(AppColors.PRIMARY)
        btn.setTextColor(AppColors.WHITE)
        btn.textSize = 16f
        btn.setTypeface(null, Typeface.BOLD)
        btn.setPadding(40, 20, 40, 20)
    }
}

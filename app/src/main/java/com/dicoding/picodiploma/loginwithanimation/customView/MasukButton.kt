package com.dicoding.picodiploma.loginwithanimation.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.loginwithanimation.R

class MasukButton : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable

    private var txtColor: Int = 0

    // Konstruktor dari MyButton
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    // Metode onDraw() digunakan untuk mengcustom button ketika enable dan disable
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Mengubah background dari Button
        background = if(isEnabled) enabledBackground else disabledBackground

        // Mengubah warna text pada button
        setTextColor(txtColor)

    }

    // pemanggilan Resource harus dilakukan saat kelas MyButton diinisialisasi, jadi harus dikeluarkan dari metode onDraw
    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        //enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        //disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}

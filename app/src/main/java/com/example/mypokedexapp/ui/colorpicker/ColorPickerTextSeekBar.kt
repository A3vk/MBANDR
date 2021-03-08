package com.example.mypokedexapp.ui.colorpicker

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatSeekBar
import com.example.mypokedexapp.R

class ColorPickerTextSeekBar: AppCompatSeekBar {
    private val textPaint: Paint = Paint(Paint.LINEAR_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG);
    private var textRect: Rect = Rect()
    private var text: String? = null

    constructor(context: Context) : super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        var textColor = 0
        var textSize = 0f

        if(attrs != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerTextSeekBar)
            try {
                textColor = typedArray.getColor(R.styleable.ColorPickerTextSeekBar_android_textColor, 0xff00000)
                textSize = typedArray.getDimension(R.styleable.ColorPickerTextSeekBar_android_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18F, resources.displayMetrics))
                text = typedArray.getString(R.styleable.ColorPickerTextSeekBar_android_text)
            } finally {
                typedArray.recycle()
            }
        }

        textPaint.color = textColor
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.getTextBounds("255", 0, 3, textRect)

        val paddingTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (0.6 * textRect.height()).toFloat(), resources.displayMetrics).toInt()
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawText(if (text == null) progress.toString() else text.toString(),
                (thumb.bounds.left + paddingLeft).toFloat(),
                (textRect.height() + (paddingTop shr 2)).toFloat(),
                textPaint
        )
    }
}
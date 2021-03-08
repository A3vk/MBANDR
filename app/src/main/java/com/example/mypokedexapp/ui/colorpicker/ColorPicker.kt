package com.example.mypokedexapp.ui.colorpicker

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import com.example.mypokedexapp.R
import com.example.mypokedexapp.utils.ColorHelper

class ColorPicker(private val activity: Activity): Dialog(activity), SeekBar.OnSeekBarChangeListener {
    private var red: Int = 0
    private var green: Int = 0
    private var blue: Int = 0

    private lateinit var callback: ColorPickerCallback

    private lateinit var colorView: View
    private lateinit var hexCode: EditText

    private lateinit var redSeekBar: SeekBar
    private lateinit var greenSeekBar: SeekBar
    private lateinit var blueSeekBar: SeekBar

    constructor(activity: Activity, color: Int) : this(activity) {
        this.red = Color.red(color)
        this.green = Color.green(color)
        this.blue = Color.blue(color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.color_picker)

        colorView = findViewById(R.id.colorView)
        hexCode = findViewById(R.id.hexCode)
        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)

        redSeekBar.setOnSeekBarChangeListener(this)
        greenSeekBar.setOnSeekBarChangeListener(this)
        blueSeekBar.setOnSeekBarChangeListener(this)

        hexCode.filters = arrayOf(InputFilter.LengthFilter(6))
        hexCode.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                updateColorView(v.text.toString())
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(hexCode.windowToken, 0)
                true
            }
            false
        }

        val sendButton: Button = findViewById(R.id.sendColorButton)
        sendButton.setOnClickListener {
            callback.onColorChosen(getColor())
            dismiss()
        }
    }

    private fun initUi() {
        colorView.setBackgroundColor(getColor())
        redSeekBar.progress = red
        greenSeekBar.progress = green
        blueSeekBar.progress = blue
        hexCode.setText(ColorHelper.rgbToHex(red, green, blue))
    }

    private fun updateColorView(input: String) {
        try {
            val color = Color.parseColor("#$input")
            red = Color.red(color)
            green = Color.green(color)
            blue = Color.blue(color)

            colorView.setBackgroundColor(getColor())

            redSeekBar.progress = red
            greenSeekBar.progress = green
            blueSeekBar.progress = blue
        } catch (ignored: IllegalArgumentException) {
            hexCode.error = activity.resources.getText(R.string.colorpicker_error);
        }
    }

    fun setCallback(listener: ColorPickerCallback) {
        callback = listener
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id) {
            R.id.redSeekBar -> red = progress
            R.id.greenSeekBar -> green = progress
            R.id.blueSeekBar -> blue = progress
        }

        colorView.setBackgroundColor(getColor())
        hexCode.setText(ColorHelper.rgbToHex(red, green, blue))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        return
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        return
    }

    private fun getColor(): Int {
        return Color.rgb(red, green, blue)
    }

    override fun show() {
        super.show()
        initUi()
    }
}
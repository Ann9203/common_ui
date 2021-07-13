package com.xue.data.androidviewstudy

import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xue.data.androidviewstudy.wiget.FlowLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val flowLayout = findViewById<FlowLayout>(R.id.fl)
        val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
        val tempArray = arrayListOf<String>()
        val tempView = arrayListOf<View>()
        tempArray.add("小花猫")
        tempArray.add("大脸猫")
        tempArray.add("布偶奶狗")
        tempArray.add("整理家具")
        tempArray.add("水洗牛仔裤")
        tempArray.add("水")
        tempArray.add("冰冰凉")
        tempArray.add("老冰柜加热")
        tempArray.add("女 拖鞋")
        tempArray.add("女 长裙子超长宽的")
        tempArray.add("波西米亚")

        tempArray.forEach { value ->
            val text = TextView(baseContext)
            text.text = value
            text.setTextColor(Color.parseColor("#333333"))
            text.textSize = 20.0f
            layoutParams.bottomMargin = 10
            layoutParams.leftMargin=15
            text.setBackgroundResource(R.drawable.bg)
            text.setPadding(50,3,50,3)
            text.layoutParams = layoutParams
            flowLayout.addView(text,layoutParams)
        }
//        flowLayout.setContents(tempView)
    }
}
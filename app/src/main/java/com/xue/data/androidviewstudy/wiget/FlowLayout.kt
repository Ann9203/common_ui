package com.xue.data.androidviewstudy.wiget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import com.xue.data.androidviewstudy.R
import kotlin.math.log
import kotlin.math.max

/**
 *
 * @ProjectName:    AndroidViewStudy
 * @Package:        com.xue.data.androidviewstudy.wiget
 * @ClassName:      FlowLayout
 * @Description:
 * @Author:         李雪
 * @CreateDate:     2021-07-12 16:40
 * @UpdateUser:
 * @UpdateDate:     2021-07-12 16:40
 * @UpdateRemark:   更新说明
 * @Version:        1.0.0
 */
class FlowLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ViewGroup(context, attrs, defStyleAttr) {
        constructor(context: Context?):  this(context, null)
        constructor(context: Context?,attrs: AttributeSet?):this(context, attrs,0)

//    var viewList= arrayListOf<View>();
    var currentLineWidth=0;
    var currentLineHeight=0;
    val viewLines= SparseArray<List<View>>();
    val lineHeightArray = SparseArray<Int>();
    var paddingWidth = 10;
    var paddingHeight = 10;
    var currentLineIndex =0;
    var contentWidth = 0;
    var  contentHeight = 0;
    var modify = 0;

    init {
        attrs?.let {
            val typeArray = context?.obtainStyledAttributes(it, R.styleable.FlowLayout, defStyleAttr, 0)
            typeArray?.let {
                paddingWidth = typeArray.getInt(R.styleable.FlowLayout_paddingWidth, 10);
                paddingHeight=typeArray.getInt(R.styleable.FlowLayout_paddingHeight, 15)
            }
        }
    }


    //测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (childCount >0 && modify == 0){
            modify =1
            measureChildView(widthMeasureSpec,heightMeasureSpec,selfWidth)
            for(i in 0 until lineHeightArray.size()){
                contentHeight+=lineHeightArray.get(i)
            }
            //ViewGroup指定尺寸
            val widthMode = MeasureSpec.getMode(widthMeasureSpec);
            val heightMode = MeasureSpec.getMode(heightMeasureSpec);
            val readWidth =if(widthMode == MeasureSpec.EXACTLY )selfWidth else contentWidth
            val realHeight = if(heightMode == MeasureSpec.EXACTLY) selfHeight else contentHeight
            setMeasuredDimension(readWidth, realHeight)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
       val lineCounts = viewLines.size();
        //记录当前左边距
        var currentLeft = paddingWidth
        var currentTop = paddingHeight

        for (index in 0 until  lineCounts){
            //获取每一行 View
            val lineView = viewLines.get(index)
            val lineHeight = lineHeightArray.get(index)
            for (view in lineView){
                val left = currentLeft
                val top = currentTop
                val right = view.measuredWidth+left
                val bottom = view.measuredHeight +top
                view.layout(left,top,right, bottom)
                currentLeft   = right+paddingWidth
            }
            currentLeft = paddingWidth
            currentTop+=lineHeight+paddingHeight
        }
    }

    //测量 子view  获取到 布局真是宽高
    fun measureChildView(widthMeasureSpec: Int, heightMeasureSpec: Int,selfWidth:Int){
        var count = childCount;
        var viewList = arrayListOf<View>();
        if (count>0){
            for (index in 0 until count){
                var childView = getChildAt(index);
                var layoutParams = childView.layoutParams;
                val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,20, layoutParams.width)
                val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 10, layoutParams.height)
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                var childHeight = childView.measuredHeight
                var childWidth = childView.measuredWidth

                if (viewList.size !=0 && currentLineWidth+childWidth+paddingWidth > selfWidth){
                    //超出一行了
                    currentLineWidth = 0;
                    viewList = arrayListOf()
                    currentLineHeight = 0;
                    currentLineIndex++
                }
                viewList.add(childView)
                currentLineHeight= max(currentLineHeight, childHeight)
                if (viewLines[currentLineIndex] == null) {
                    viewLines.put(currentLineIndex, viewList)
                }

                lineHeightArray.put(currentLineIndex,currentLineHeight)
                currentLineWidth += childWidth + paddingWidth
                contentWidth= max(currentLineWidth, contentWidth)
            }
        }
    }

//     fun setContents(tempViewList:ArrayList<View>){
//        viewList.clear()
//        viewList.addAll(tempViewList);
//        invalidate()
//    }

}
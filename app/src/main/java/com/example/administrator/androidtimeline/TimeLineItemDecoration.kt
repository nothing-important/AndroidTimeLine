package com.example.administrator.androidtimeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class TimeLineItemDecoration(var context: Context) : RecyclerView.ItemDecoration() {

    //上下左右的偏移量
    var topOffset : Int = dp2px(context , 30)
    var bottomOffset : Int = 0
    var leftOffset : Int = dp2px(context , 60)
    var rightOffset : Int = 0
    //轴点半径
    var circleRadius : Float = dp2px(context , 8).toFloat()
    //轴点画笔
    var paintCircle : Paint? = null
    //轴线画笔
    var paintLine : Paint? = null

    init {
        paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        paintCircle?.color = Color.RED
        paintLine = Paint(Paint.ANTI_ALIAS_FLAG)
        paintLine?.color = Color.GRAY
        paintLine?.strokeCap = Paint.Cap.ROUND
        paintLine?.strokeWidth = dp2px(context , 5).toFloat()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(leftOffset , topOffset , rightOffset , bottomOffset)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        var childCount = parent.childCount
        for (i in 0 until childCount){
            var child = parent.getChildAt(i)
            //绘制轴点
            //确定圆心
            var circleX : Float = (child.left - leftOffset/3).toFloat()
            var circleY : Float = (child.top).toFloat()
            //绘制轴点
            c.drawCircle(circleX , circleY , circleRadius , paintCircle)
            //绘制上半轴线
            //上端点
            var topTopPointX : Float = circleX
            var topTopPointY : Float = circleY - circleRadius/2 - topOffset
            //下端点
            var topBottomPointX : Float = circleX
            var topBottomPointY : Float = circleY - circleRadius*2
            c.drawLine(topTopPointX , topTopPointY , topBottomPointX , topBottomPointY , paintLine)
            //绘制下半轴线
            var bottomTopPointX : Float = circleX
            var bottomTopPointY : Float = circleY + circleRadius*2
            var bottomBottomPointX : Float = circleX
            var bottomBottomPointY : Float = child.bottom.toFloat()
            c.drawLine(bottomTopPointX , bottomTopPointY , bottomBottomPointX , bottomBottomPointY , paintLine)
        }
    }

    private fun dp2px(context: Context , dpValue : Int) : Int{
        val displayMetrics = context.resources.displayMetrics
        return ((displayMetrics.density*dpValue + 0.5f).toInt())
    }

}
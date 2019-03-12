package com.example.administrator.androidtimeline

import android.content.Context
import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.view.View

class TimeLineItemDecoration(var context: Context , var timeBeanList : List<TimeLineBean>) : RecyclerView.ItemDecoration() {

    //上下左右的偏移量
    private var topOffset : Int = 0
    private var bottomOffset : Int = 0
    private var leftOffset : Int = 0
    private var rightOffset : Int = 0
    //轴点半径
    private var circleRadius : Float = dp2px(context , 8).toFloat()
    //轴点画笔
    private var paintCircle : Paint? = null
    //轴线画笔
    private var paintLine : Paint? = null
    //时间画笔
    private var paintText : Paint? = null

    init {
        paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        paintCircle?.color = Color.RED
        paintLine = Paint(Paint.ANTI_ALIAS_FLAG)
        paintLine?.color = Color.GRAY
        paintLine?.style = Paint.Style.STROKE
        paintLine?.strokeCap = Paint.Cap.ROUND
        paintLine?.strokeWidth = dp2px(context , 4).toFloat()
        val array : FloatArray = floatArrayOf(10f , 10f)
        paintLine?.pathEffect = DashPathEffect(array , 0f)
        paintText = Paint(Paint.ANTI_ALIAS_FLAG)
        paintText?.textSize = dp2px(context , 14).toFloat()
        paintText?.color = Color.GRAY
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        setVerticalRect(outRect)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawVerticalTimeLine(c , parent)
    }

    /**
     * 纵向排布偏移矩形
     */
    private fun setVerticalRect(outRect: Rect){
        topOffset = dp2px(context , 30)
        leftOffset = dp2px(context , 180)
        bottomOffset = 0
        rightOffset = 0
        outRect.set(leftOffset , topOffset , rightOffset , bottomOffset)
    }

    /**
     * 横向排布偏移矩形
     */
    private fun setHorizontalRect(outRect: Rect){
        topOffset = 0
        bottomOffset = 0
        leftOffset = dp2px(context , 50)
        rightOffset = dp2px(context , 50)
        outRect.set(leftOffset , topOffset , rightOffset , bottomOffset)
    }

    /**
     * 绘制纵向排布时间线
     */
    private fun drawVerticalTimeLine(c: Canvas, parent: RecyclerView){
        val childCount = parent.childCount
        for (i in 0 until childCount){
            val child = parent.getChildAt(i)
            val currentPosition = parent.getChildLayoutPosition(child)
            //绘制轴点
            //确定圆心
            val circleX : Float = (child.left - dp2px(context , 30)).toFloat()
            val circleY : Float = (child.top).toFloat()
            //绘制轴点
            if (timeBeanList[currentPosition].img == 0){
                c.drawCircle(circleX , circleY , circleRadius , paintCircle)
            }else{
                val bitmap : Bitmap = BitmapFactory.decodeResource(context.resources , timeBeanList[currentPosition].img)
                //val bitmapResult : Bitmap = Bitmap.createBitmap(bitmap , 0 , 0 , dp2px(context , 10) , dp2px(context , 10))
                c.drawBitmap(bitmap , circleX - circleRadius , circleY - circleRadius , paintLine)
            }
            //绘制时间
            val drawText : String = timeBeanList[currentPosition].time
            val rect = Rect()
            paintText?.getTextBounds(drawText , 0 , drawText.length , rect)
            c.drawText(drawText , circleX - rect.width() - dp2px(context , 20) , circleY, paintText)
            //绘制上半轴线
            //上端点
            val topTopPointX : Float = circleX
            val topTopPointY : Float = circleY - circleRadius/2 - topOffset
            //下端点
            val topBottomPointX : Float = circleX
            val topBottomPointY : Float = circleY - circleRadius*3
            c.drawLine(topTopPointX , topTopPointY , topBottomPointX , topBottomPointY , paintLine)
            //绘制下半轴线
            val bottomTopPointX : Float = circleX
            val bottomTopPointY : Float = circleY + circleRadius*3
            val bottomBottomPointX : Float = circleX
            val bottomBottomPointY : Float = child.bottom.toFloat()
            c.drawLine(bottomTopPointX , bottomTopPointY , bottomBottomPointX , bottomBottomPointY , paintLine)
        }
    }

    /**
     * 绘制横向排布时间线
     */
    private fun drawHorizontalTimeLine(c: Canvas, parent: RecyclerView){
        val childCount = parent.childCount
        val offset : Float = dp2px(context , 10).toFloat()
        val path = Path()
        for (i in 0 until childCount){
            val child = parent.getChildAt(i)
            //左边轴线
            val leftStartPointX : Float = (child.left - leftOffset).toFloat()
            val leftStartPointY : Float = (child.top + ((child.bottom - child.top)/2)).toFloat()
            val leftEndPointX : Float = child.left.toFloat() - offset
            val leftEndPointY : Float = leftStartPointY
            path.reset()
            path.moveTo(leftStartPointX , leftStartPointY)
            path.lineTo(leftEndPointX , leftEndPointY)
            c.drawPath(path , paintLine)
            //c.drawLine(leftStartPointX , leftStartPointY , leftEndPointX , leftEndPointY , paintLine)
            //右边轴线
            val rightStartPointX : Float = child.right.toFloat() + offset
            val rightStartPointY : Float = (child.top + ((child.bottom - child.top)/2)).toFloat()
            val rightEndPointX : Float = (child.right + rightOffset).toFloat()
            val rightEndPointY : Float = rightStartPointY
            path.reset()
            path.moveTo(rightStartPointX , rightStartPointY)
            path.lineTo(rightEndPointX , rightEndPointY)
            c.drawPath(path , paintLine)
            //c.drawLine(rightStartPointX , rightStartPointY , rightEndPointX , rightEndPointY , paintLine)
        }
    }

    private fun dp2px(context: Context , dpValue : Int) : Int{
        val displayMetrics = context.resources.displayMetrics
        return ((displayMetrics.density*dpValue + 0.5f).toInt())
    }

}
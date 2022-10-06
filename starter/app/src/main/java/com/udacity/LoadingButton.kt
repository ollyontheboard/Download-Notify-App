package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import java.lang.reflect.Type
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0
    private var initButtonColor = 0
    private var loadingButtonColor =0
    private var circleprogress = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = initButtonColor
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30f
        typeface= Typeface.create("",Typeface.BOLD)
    }



    private var valueAnimator = ValueAnimator()
    private var circleAnimator = ValueAnimator()

     var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
         when(new){
             ButtonState.Loading->{
                 valueAnimator = ValueAnimator.ofInt(0,width)
                 valueAnimator.duration = 1000
                 valueAnimator.repeatCount = ValueAnimator.INFINITE
                 this.setBackgroundColor(initButtonColor)

                 valueAnimator.addUpdateListener {animator->
                     progress = animator.animatedValue as Int
                     this.invalidate()
                 }

                 valueAnimator.start()

                 circleAnimator = ValueAnimator.ofFloat(0f,360f)
                 circleAnimator.duration= 1000
                 circleAnimator.repeatCount = ValueAnimator.INFINITE
                 circleAnimator.addUpdateListener {animator->
                   circleprogress = animator.animatedValue as Float
                     this.invalidate()
                 }
                 circleAnimator.start()

             }

             ButtonState.Completed->{

                 valueAnimator.end()
                 circleAnimator.end()
                 progress = 0
                 circleprogress= 0f
                 invalidate()



             }

         }


    }


    init {
        isClickable = true
        context.withStyledAttributes(attrs, R.styleable.LoadingButton){
            initButtonColor = getColor(R.styleable.LoadingButton_buttonColor1,0)
            loadingButtonColor = getColor(R.styleable.LoadingButton_buttonColor2,0)
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
     drawButton(canvas)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }


    private fun drawButton(canvas: Canvas?) {
        paint.color = Color.BLUE
       canvas?.drawRect(0f, 0f, width.toFloat(),height.toFloat() ,paint)



        paint.color = loadingButtonColor
        canvas?.drawRect(0f, 0f, width.toFloat() * progress/100 ,height.toFloat() ,paint)
        paint.color = Color.YELLOW
        canvas?.drawArc(width/1.2f,height/10f, width.toFloat(), height.toFloat(), 0f,circleprogress,true,paint)

        paint.color = Color.BLACK
        canvas?.drawText("DOWNLOAD", width/2f,height/2f,paint)



    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

} 
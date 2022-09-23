package com.udacity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->

    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 60f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        color = resources.getColor(R.color.colorPrimary)
    }
    private val textBoundRect = Rect()
    private var progress = 0f
    private val listener = ValueAnimator.AnimatorUpdateListener {
        progress = (it.animatedValue as Float)
        invalidate()
        if (progress == 100f) {
            valueAnimator.cancel()
            buttonState = ButtonState.Completed
            invalidate()
        }
    }


    init {
        isClickable = true
        valueAnimator = AnimatorInflater.loadAnimator(context, R.animator.loading) as ValueAnimator
        valueAnimator.addUpdateListener(listener)

    }

    override fun performClick(): Boolean {
        super.performClick()
        if (buttonState == ButtonState.Completed)
            buttonState = ButtonState.Loading
        valueAnimator.start()
        return true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val text =
            if (buttonState == ButtonState.Loading) resources.getText(R.string.button_loading)
            else resources.getString(R.string.button_downloading)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        if (buttonState == ButtonState.Loading) {
            paint.color = resources.getColor(R.color.colorPrimaryDark)
            canvas?.drawRect(
                0f,
                0f,
                (widthSize * (progress / 100)),
                height.toFloat(),
                paint
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)// when removing this condition an error shows
            {
                paint.getTextBounds(text, 0, text.length, textBoundRect)
            }
            val x = measuredWidth.toFloat() / 2

            paint.color = resources.getColor(R.color.colorAccent)

            canvas?.drawArc(
                x + textBoundRect.right / 2 + 40f,
                heightSize / 2 - 30f,
                x + textBoundRect.right / 2 + 80f,
                heightSize / 2 + 30f,
                0f,
                360 * progress / 100,
                true,
                paint
            )

        }

        paint.color = Color.WHITE
        canvas?.drawText(text as String, (width / 2).toFloat(), (height / 2).toFloat(), paint)
        paint.color = resources.getColor(R.color.colorPrimary)

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
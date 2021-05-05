package com.grupposts.trasporti.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.grupposts.trasporti.R
import java.util.*

class SignatureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private val STATE_STILL = 0
    private val STATE_MOVING = 1
    private var DEFAULT_COLOR = 0

    private var state = 0
    private val paintPenList: ArrayList<Paint> = ArrayList()
    private var latestPath: Path? = null
    private var latestPaint: Paint? = null
    private val pathPenList: ArrayList<Path> = ArrayList()

    private var lineWidth = 10
    private var currentColor = 0

    private var imageCanvas: Canvas = Canvas()
    private var bitmap: Bitmap? = null

    init {
        DEFAULT_COLOR = ContextCompat.getColor(context, R.color.black)
        currentColor = DEFAULT_COLOR
        initPaintNPen(currentColor)
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
            }
        })
    }

    private fun initPaintNPen(color: Int) {
        latestPaint = getNewPaintPen(color)
        latestPath = getNewPathPen()
        paintPenList.add(latestPaint!!)
        pathPenList.add(latestPath!!)
    }

    private fun getNewPathPen(): Path {
        return Path()
    }

    private fun getNewPaintPen(color: Int): Paint {
        val mPaintPen = Paint()
        mPaintPen.strokeWidth = lineWidth.toFloat()
        mPaintPen.isAntiAlias = true
        mPaintPen.isDither = true
        mPaintPen.style = Paint.Style.STROKE
        mPaintPen.strokeJoin = Paint.Join.MITER
        mPaintPen.strokeCap = Paint.Cap.ROUND
        mPaintPen.color = color
        return mPaintPen
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)

        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startPath(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                updatePath(x, y)
            }
            MotionEvent.ACTION_UP -> {
                endPath(x, y)
            }
        }
        invalidate()
        return true
    }

    private fun startPath(x: Float, y: Float) {
        initPaintNPen(currentColor)
        latestPath?.moveTo(x, y)
    }

    private fun updatePath(x: Float, y: Float) {
        state = STATE_MOVING
        latestPath?.lineTo(x, y)
    }

    private fun endPath(x: Float, y: Float) {}

    fun setDrawColor(color: Int) {
        currentColor = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        imageCanvas.setBitmap(bitmap)
        for (i in paintPenList.indices) {
            canvas.drawPath(pathPenList[i], paintPenList[i])
            imageCanvas.drawPath(pathPenList[i], paintPenList[i])
        }
    }

    fun increaseWidth(decrease: Boolean) {
        if (decrease) {
            if (lineWidth > 5) {
                lineWidth -= 10
            }
        } else {
            if (lineWidth < 50) {
                lineWidth += 10
            }
        }
        invalidate()
    }

    fun getSignatureBitmap(): Bitmap? {
        return bitmap
    }

    fun resetView() {
        currentColor = DEFAULT_COLOR
        state = STATE_STILL
        latestPath!!.reset()
        latestPaint!!.reset()
        pathPenList.clear()
        paintPenList.clear()
        lineWidth = 10
        initPaintNPen(currentColor)
        invalidate()
    }

}
package com.grupposts.trasporti.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.grupposts.trasporti.R

class CameraOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let {
            setLayerType(LAYER_TYPE_SOFTWARE, null)

            val outerPaint = Paint()
            outerPaint.color = Color.BLACK
            outerPaint.alpha = 89
            outerPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
            outerPaint.isAntiAlias = true

            val innerPaint = Paint()
            innerPaint.color = Color.TRANSPARENT
            innerPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            innerPaint.isAntiAlias = true

            val squareSize = context.resources.getDimension(R.dimen.camera_overlay_square_size)
            val left = (width.toFloat() / 2) - squareSize / 2
            val top = (height.toFloat() / 2) - squareSize / 2
            val right = (width.toFloat() / 2) + squareSize / 2
            val bottom = (height.toFloat() / 2) + squareSize / 2

            canvas.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), outerPaint)
            canvas.drawRect(left, top, right, bottom, innerPaint)
        }

    }

}
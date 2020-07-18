package foodapp.com.foodapp.ui.views

import android.graphics.*
import com.squareup.picasso.Transformation

class CircleTransform : Transformation {

    companion object {
        private const val BORDER_COLOR = Color.WHITE
        private const val BORDER_RADIUS = 5
    }

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f

        // Prepare the background
        val paintBg = Paint()
        paintBg.color = BORDER_COLOR
        paintBg.isAntiAlias = true

        // Draw the background circle
        canvas.drawCircle(r, r, r, paintBg)

        // Draw the image smaller than the background so a little border will be seen
        canvas.drawCircle(r, r, r - BORDER_RADIUS, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}
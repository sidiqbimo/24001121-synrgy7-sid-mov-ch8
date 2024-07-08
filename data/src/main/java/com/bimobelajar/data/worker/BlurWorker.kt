package com.bimobelajar.data.worker

import android.content.Context
import android.graphics.*
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val imagePath = inputData.getString("image_path")
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            val blurredBitmap = blurBitmap(bitmap)
            val circularBitmap = getCircularBitmap(blurredBitmap)
            val resizedBitmap = resizeBitmap(circularBitmap, 55, 55)
            FileOutputStream(File(imagePath)).use { out ->
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out) //jadiin PNG biar bisa transparrent or gak ada black square
            }
            return Result.success()
        }
        return Result.failure()
    }

    private fun blurBitmap(bitmap: Bitmap): Bitmap {
        val width = Math.round(bitmap.width * 0.1f)
        val height = Math.round(bitmap.height * 0.1f)
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(applicationContext)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(25f)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return Bitmap.createScaledBitmap(outputBitmap, bitmap.width, bitmap.height, false)
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true

        val rect = Rect(0, 0, size, size)
        val rectF = RectF(rect)

        canvas.drawARGB(0, 0, 0, 0)

        val path = Path()
        path.addOval(rectF, Path.Direction.CCW)
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        val borderSize = 4
        val strokeColor = Color.parseColor("#A91D3A")
        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderSize.toFloat()
        canvas.drawOval(rectF, paint)



        return output
    }

    private fun resizeBitmap(bitmap: Bitmap, widthDp: Int, heightDp: Int): Bitmap {
        val scale = applicationContext.resources.displayMetrics.density
        val widthPx = (widthDp * scale + 0.5f).toInt()
        val heightPx = (heightDp * scale + 0.5f).toInt()
        return Bitmap.createScaledBitmap(bitmap, widthPx, heightPx, false)
    }
}

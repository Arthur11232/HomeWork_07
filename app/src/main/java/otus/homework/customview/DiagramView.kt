package otus.homework.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import otus.homework.customview.utils.px

class DiagramView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    private val list = ArrayList<Float>()
    private var maxValue = 0
    private var barWidth = 50.px.toFloat()
    private lateinit var paintBaseFill: Paint
    private lateinit var paintDangerFill: Paint
    private var threshold: Int = Int.MAX_VALUE
    private lateinit var paintStroke: Paint
    private val rect = RectF()
    private var eachAngel = 0f
    private var nextAngel = 0f
    private val listColor = listOf(
        Color.YELLOW,
        Color.BLUE,
        Color.GRAY,
        Color.GREEN,
        Color.BLACK,
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.BLUE,
        Color.LTGRAY,
    )

    init {
        setup(Color.GREEN, Color.RED, Int.MAX_VALUE, 50.px.toFloat())
        if (isInEditMode) {
            setValues(listOf(1000, 500, 800, 900, 600, 700, 200, 200, 600, 500))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        when (wMode) {
            MeasureSpec.EXACTLY -> {
                setMeasuredDimension(wSize, hSize)
            }

            MeasureSpec.AT_MOST -> {
                val newW = Integer.min((list.size * barWidth).toInt(), wSize)
                setMeasuredDimension(newW, hSize)
            }

            MeasureSpec.UNSPECIFIED -> {
                setMeasuredDimension((list.size * barWidth).toInt(), hSize)
            }
        }
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 5f
        textSize = 32f
        textAlign = Paint.Align.LEFT
        style = Paint.Style.STROKE
    }

    private val paintDiagram = Paint().apply {
        strokeWidth = 30f
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (list.size == 0) return

        rect.set(
            100f,
            500f,
            width - 100f,
            width - 100f + 500f,
        )

        list.forEachIndexed { index, item ->
            paintDiagram.color = listColor[index]
            nextAngel += if (index == 0) 0f else list[index -1]
            canvas.drawArc(rect, nextAngel, item, true, paintDiagram)
            canvas.drawText(item.toInt().toString(), width / 2f + item, (height / 2f) - (100 - nextAngel), textPaint)
        }
    }

    fun setValues(values: List<Int>) {
        list.clear()
        maxValue = list.size
        var totalValue = 0
        values.forEach { totalValue += it }
        eachAngel = 360f / totalValue
        val ll = values.map { i ->
            i * eachAngel
        }
        list.addAll(ll)
        requestLayout()
        invalidate()
    }

    fun setThreshold(threshold: Int) {
        this.threshold = threshold

        requestLayout()
        invalidate()
    }

    private fun setup(baseColor: Int, dangerColor: Int, threshold: Int, barWidth: Float) {
        paintBaseFill = Paint().apply {
            color = baseColor
            style = Paint.Style.FILL
        }
        paintDangerFill = Paint().apply {
            color = dangerColor
            style = Paint.Style.FILL
        }
        paintStroke = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 2.0f
        }
        this.threshold = threshold
        this.barWidth = barWidth
    }
}
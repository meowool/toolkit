@file:Suppress("OverridingDeprecatedMember")

package com.mars.ui.foundation.modifies

import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.core.view.doOnLayout
import com.mars.toolkit.appScreenHeight
import com.mars.toolkit.appScreenWidth
import com.mars.toolkit.float
import com.mars.ui.core.Benchmark
import com.mars.ui.core.Modifier
import kotlin.math.roundToInt

// for Float

/**
 * 调整 View 的百分比高宽为 [size]
 * @param size 声明 View 要占 [benchmark] 的大小的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @FloatRange(from = 0.0, to = 1.0) size: Float,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(size, size, benchmark)

/**
 * 调整 View 的大小
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @FloatRange(from = 0.0, to = 1.0) width: Float,
  @FloatRange(from = 0.0, to = 1.0) height: Float,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width, height, benchmark)

/**
 * 调整 View 的宽度
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentWidth(
  @FloatRange(from = 0.0, to = 1.0) width: Float,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width = width, benchmark = benchmark)

/**
 * 调整 View 的高度
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentHeight(
  @FloatRange(from = 0.0, to = 1.0) height: Float,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(height = height, benchmark = benchmark)


// for Double

/**
 * 调整 View 的百分比高宽为 [size]
 * @param size 声明 View 要占 [benchmark] 的大小的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @FloatRange(from = 0.0, to = 1.0) size: Double,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(size.float, size.float, benchmark)

/**
 * 调整 View 的大小
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @FloatRange(from = 0.0, to = 1.0) width: Double,
  @FloatRange(from = 0.0, to = 1.0) height: Double,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width.float, height.float, benchmark)

/**
 * 调整 View 的宽度
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentWidth(
  @FloatRange(from = 0.0, to = 1.0) width: Double,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width = width.float, benchmark = benchmark)

/**
 * 调整 View 的高度
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentHeight(
  @FloatRange(from = 0.0, to = 1.0) height: Double,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(height = height.float, benchmark = benchmark)


// for Int

/**
 * 调整 View 的百分比高宽为 [size]
 * @param size 声明 View 要占 [benchmark] 的大小的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @IntRange(from = 0, to = 100) size: Int,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(size / 100F, size / 100F, benchmark)

/**
 * 调整 View 的大小
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentSize(
  @IntRange(from = 0, to = 100) width: Int,
  @IntRange(from = 0, to = 100) height: Int,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width / 100F, height / 100F, benchmark)

/**
 * 调整 View 的宽度
 * @param width 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentWidth(
  @IntRange(from = 0, to = 100) width: Int,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(width = width / 100F, benchmark = benchmark)

/**
 * 调整 View 的高度
 * @param height 声明 View 要占 [benchmark] 的宽度的百分之几
 * @param benchmark 声明百分比要以什么为标准计算
 */
fun Modifier.percentHeight(
  @IntRange(from = 0, to = 100) height: Int,
  benchmark: Benchmark = Benchmark.Parent,
) = +LayoutPercentSizeModifier(height = height / 100F, benchmark = benchmark)


/** 布局百分比大小调整的具体实现 [ViewGroup.LayoutParams] */
private data class LayoutPercentSizeModifier(
  val width: Float? = null,
  val height: Float? = null,
  val benchmark: Benchmark = Benchmark.Parent,
) : Modifier {
  override fun realize(myself: View, parent: ViewGroup?) {
    when (benchmark) {
      Benchmark.Parent -> parent?.doOnLayout { changeSize(myself, it.width, it.height) }
      Benchmark.Root -> changeSize(myself, appScreenWidth, appScreenHeight)
    }
  }

  fun changeSize(myself: View, benchmarkWidth: Int, benchmarkHeight: Int) {
    myself.layoutParams = myself.layoutParams?.also {
      width?.apply { it.width = (benchmarkWidth / this).roundToInt() }
      height?.apply { it.height = (benchmarkHeight / this).roundToInt() }
    } ?: ViewGroup.LayoutParams(
      width?.run { (benchmarkWidth / this).roundToInt() } ?: ViewGroup.LayoutParams.WRAP_CONTENT,
      height?.run { (benchmarkHeight / this).roundToInt() } ?: ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }
}
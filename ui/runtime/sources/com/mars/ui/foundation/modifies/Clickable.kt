@file:Suppress("OverridingDeprecatedMember")

package com.mars.ui.foundation.modifies

import android.view.View
import android.view.ViewGroup
import com.mars.toolkit.view.onMultipleClick
import com.mars.ui.core.Modifier

/**
 * 调整视图可点击的相关参数
 * @param enabled 让视图可点击
 * @param onLongClick 点击后长按时的回调
 * @param onClick 点击时的回调
 */
fun Modifier.clickable(
  enabled: Boolean = true,
  onLongClick: ((View) -> Boolean)? = null,
  onClick: ((View) -> Unit)? = null,
) = +ClickableModifier(enabled, onLongClick, onClick)

/**
 * 调整视图可多次点击的相关参数
 * @param enabled 让视图可点击
 * @param repeat 需要重复点击次数
 * @param interval 点击次数之间的时间间隔
 * @param onClick 满足重复点击次数后执行的回调
 */
fun Modifier.multipleClicks(
  repeat: Int,
  interval: Long = 1000,
  enabled: Boolean = true,
  onClick: (View) -> Unit,
) = +MultiClickableModifier(enabled, repeat, interval, onClick)


/** 为视图提供点击调整 */
private data class ClickableModifier(
  val enabled: Boolean = true,
  val onLongClick: ((View) -> Boolean)? = null,
  val onDoubleClick: ((View) -> Unit)? = null,
  val onClick: ((View) -> Unit)? = null,
) : Modifier {
  override fun realize(myself: View, parent: ViewGroup?) {
    onLongClick?.apply { myself.setOnLongClickListener(onLongClick) }
    onClick?.apply { myself.setOnClickListener(onClick) }
    onDoubleClick?.apply {
      var clicked = false
      myself.onMultipleClick(count = 2) {
        invoke(myself)
        // 确保 onClick 的回调只执行一次
        if (!clicked) {
          onClick?.apply { myself.setOnClickListener(onClick) }
          clicked = true
        }
      }
    }
    myself.isClickable = enabled

  }
}

/** 为视图提供多次点击的调整器 */
private data class MultiClickableModifier(
  val enabled: Boolean = true,
  val repeat: Int,
  val interval: Long = 1000,
  val onClick: ((View) -> Unit)? = null,
) : Modifier {
  override fun realize(myself: View, parent: ViewGroup?) {
    onClick?.apply {
      myself.setOnClickListener(object : View.OnClickListener {
        var clickCount = 0
        var lastClickTime = 0L
        override fun onClick(v: View) {
          val currentTime = System.currentTimeMillis()
          if (lastClickTime != 0L && (currentTime - lastClickTime > interval)) {
            clickCount = 1
            lastClickTime = currentTime
            return
          }
          ++clickCount
          lastClickTime = currentTime

          if (clickCount == repeat) {
            clickCount = 0
            lastClickTime = 0L
            invoke(v)
          }
        }
      })
    }
    myself.isClickable = enabled
  }
}
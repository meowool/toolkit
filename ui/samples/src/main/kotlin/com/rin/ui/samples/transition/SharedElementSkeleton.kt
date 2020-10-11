package com.rin.ui.samples.transition

import android.content.Context
import android.util.AttributeSet
import com.mars.ui.UiPreview
import com.mars.ui.skeleton.Skeleton

/**
 * 拥有共享元素过渡动画的 [Skeleton] 案例
 *
 * @author 凛
 * @github https://github.com/oh-Rin
 * @date 2020/10/11 - 22:56
 */
class SharedElementSkeleton : Skeleton() {

}

@Deprecated(UiPreview.Deprecated)
private class SharedElementSkeletonPreview(context: Context, attrs: AttributeSet?) :
  UiPreview(context, attrs, ::TransitionsSkeleton)
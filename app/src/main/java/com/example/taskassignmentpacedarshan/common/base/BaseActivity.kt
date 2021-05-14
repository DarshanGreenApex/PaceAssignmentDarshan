package com.example.taskassignmentpacedarshan.common.base

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.taskassignmentpacedarshan.common.util.addOnPropertyChanged
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity :AppCompatActivity(){

    internal var viewDataBinding: ViewDataBinding? = null
    internal val compositeDisposable = CompositeDisposable()
    protected val isLoading = ObservableField<Boolean>(false)

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getVm(): ViewModel
    abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
        subscribeLoader()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding?.setVariable(getBindingVariable(), getVm())
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()
    }
    protected open fun subscribeLoader() {
        compositeDisposable.add(isLoading.addOnPropertyChanged {
            runOnUiThread {
                val isVisible = it.get() == true
                val loadingView = getLoadingView()

                loadingView?.clearAnimation()

                val targetAlpha = if (isVisible) 1f else 0f
                val anim = ObjectAnimator.ofFloat(loadingView, "alpha", loadingView?.alpha ?: 0.0f, targetAlpha)
                anim.duration = 200

                val mAnimationSet = AnimatorSet()
                mAnimationSet.play(anim)
                mAnimationSet.start()

                if (isVisible) {
                    loadingView?.visibility = View.VISIBLE
                }

                mAnimationSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        if (!isVisible && loadingView != null) {
                            loadingView.visibility = View.GONE
                        }
                    }

                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                })
            }
        })
    }

    fun <T> subscribeToLoader(c: Observable<T>): Observable<T> {
        return c.doOnSubscribe {
            isLoading.set(true)
        }.doFinally {
            isLoading.set(false)
        }
    }

    fun subscribeToLoader(c: Completable): Completable {
        return c.doOnSubscribe { isLoading.set(true) }.doFinally { isLoading.set(false) }
    }
    open fun getLoadingView(): View? {
        return null; }
}
package com.example.taskassignmentpacedarshan.common.base

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.taskassignmentpacedarshan.common.util.addOnPropertyChanged
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment :Fragment(){

    protected val compositeDisposable = CompositeDisposable()
    protected val isLoading = ObservableField<Boolean>(false)

    abstract fun getVm(): ViewModel

    internal fun init() {
        setHasOptionsMenu(true)
        retainInstance = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLoader()

    }

    protected open fun subscribeLoader() {
        compositeDisposable.add(
            isLoading.addOnPropertyChanged {
                activity?.runOnUiThread {
                    val isVisible = it.get() == true
                    val loadingView = (activity as BaseActivity).getLoadingView()

                    loadingView?.clearAnimation()

                    val targetAlpha = if (isVisible) 1f else 0f
                    val anim = ObjectAnimator.ofFloat(loadingView, "alpha", loadingView?.alpha ?: 0.0f, targetAlpha)
                    anim.duration = 200

                    val mAnimationSet = AnimatorSet()
                    mAnimationSet.play(anim)
                    mAnimationSet.start()

                    if (isVisible) {
                        loadingView?.visibility = View.GONE
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
            }
        )
    }

    fun <T> subscribeToLoader(c: Observable<T>): Observable<T> {
        return c
            .doOnSubscribe {
                isLoading.set(true) }
            .doFinally {
                isLoading.set(false) }
    }

    fun subscribeToLoader(c: Completable): Completable {
        return c
            .doOnSubscribe { isLoading.set(true) }
            .doFinally { isLoading.set(false) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
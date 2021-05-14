package com.example.taskassignmentpacedarshan.common.misc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import io.reactivex.Observable


class RxAlertDialog {
    companion object {
        const val DISMISS_ALERT = 0
        const val BUTTON_POSITIVE = 1
        const val BUTTON_NEGATIVE = 2

        @JvmStatic
        @JvmOverloads
        fun show(
            context: Context,
            @StringRes title: Int? = null,
            @StringRes message: Int? = null,
            @StringRes positiveButton: Int? = null,
            @StringRes negativeButton: Int? = null
        ): Observable<Int> {

            return Observable.create<Int> { emitter ->
                val builder = AlertDialog.Builder(context)
                    .setOnDismissListener { emitter.onNext(DISMISS_ALERT) }

                title?.let { builder.setTitle(it) }
                message?.let { builder.setMessage(it) }
                positiveButton?.let {
                    builder.setPositiveButton(positiveButton) { _, _ ->
                        emitter.onNext(BUTTON_POSITIVE)
                        emitter.onComplete()
                    }
                }
                negativeButton?.let {
                    builder.setNegativeButton(negativeButton) { _, _ ->
                        emitter.onNext(BUTTON_NEGATIVE)
                        emitter.onComplete()
                    }
                }

                val dialog = builder.show()
                //emitter.setCancellation { dialog.dismiss() }
            }
        }



        @JvmStatic
        @JvmOverloads
        fun showCustomAlert(
            context: Context,
            @LayoutRes layout: Int,
            @IdRes positiveButton: Int? = null,
            @IdRes negativeButton: Int? = null
        ): Observable<Int> {

            var dialog: AlertDialog? = null

            return Observable.create<Int> { emitter ->
                val builder = AlertDialog.Builder(context)
                    .setOnDismissListener {
                        emitter.onNext(DISMISS_ALERT)
                        emitter.onComplete()
                    }

                val v = LayoutInflater.from(context).inflate(layout, null)
                builder.setView(v)


                positiveButton?.let {
                    v.findViewById<View>(positiveButton).setOnClickListener {
                        emitter.onNext(BUTTON_POSITIVE)
                        emitter.onComplete()
                    }
                }
                negativeButton?.let {
                    v.findViewById<View>(negativeButton).setOnClickListener {
                        emitter.onNext(BUTTON_NEGATIVE)
                        emitter.onComplete()
                    }
                }

                dialog = builder.show()

                dialog?.window?.setLayout(400, 400)
            }
                .doFinally { dialog?.hide() }
        }
    }
}
package com.example.taskassignmentpacedarshan.di.managers

import com.example.taskassignmentpacedarshan.di.api.ApiException
import com.example.taskassignmentpacedarshan.di.api.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


class ApiManager(private val apiService: ApiService) {
    private inline fun <reified T> prepareObservable(o: Observable<T>): Observable<T> {
        return o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext { it: Throwable ->
            val e = if (it is HttpException) {
                when (it.code()) {
                    401 -> {
                        ApiException(ApiException.Reason.UNAUTHORIZED)
                    }
                    403 -> {
                        ApiException(ApiException.Reason.UNAUTHORIZED)
                    }
                    400 -> ApiException(ApiException.Reason.SERVER_ERROR)
                    500 -> ApiException(ApiException.Reason.SERVER_ERROR)
                    else -> ApiException(ApiException.Reason.SERVER_ERROR)
                }
            } else {
                ApiException(ApiException.Reason.NETWORK_ERROR)
            }

            Observable.error<T>(e)
        }.share()
    }
}
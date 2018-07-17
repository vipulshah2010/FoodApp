package foodapp.com.domain.base

import foodapp.com.data.shared.ExecutionThread
import foodapp.com.data.shared.PostExecutionThread
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleInteractor<Emitted, Params>(executionThread: ExecutionThread, postExecutionThread: PostExecutionThread)
    : BaseInteractor(executionThread, postExecutionThread) {

    fun execute(observer: DisposableSingleObserver<Emitted>, params: Params) {
        val observable = buildSingle(params).subscribeOn(mExecutionThread.scheduler)
                .observeOn(mPostExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    protected abstract fun buildSingle(params: Params): Single<Emitted>
}

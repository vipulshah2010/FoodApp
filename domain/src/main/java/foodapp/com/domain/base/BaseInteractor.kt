package foodapp.com.domain.base

import foodapp.com.data.shared.ExecutionThread
import foodapp.com.data.shared.PostExecutionThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseInteractor constructor(val mExecutionThread: ExecutionThread,
                                          val mPostExecutionThread: PostExecutionThread) {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
}

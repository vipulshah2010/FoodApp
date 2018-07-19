package foodapp.com.foodapp.base;

import foodapp.com.data.shared.utils.ErrorUtils
import foodapp.com.foodapp.ErrorType
import timber.log.Timber

open class BasePresenter<V : MvpView> : MvpPresenter<V> {

    private var mMvpView: V? = null

    val mvpView: V?
        get() {
            checkViewAttached()
            return mMvpView
        }

    private val isViewAttached: Boolean
        get() = mMvpView != null

    override fun onAttach(mvpView: V) {
        mMvpView = mvpView
    }

    override fun onDetach() {
        mMvpView = null
    }

    private fun checkViewAttached() {
        if (!isViewAttached) {
            Timber.e("Please call Presenter.onAttach(MvpView) before requesting data to the Presenter")
        }
    }

    fun handleError(throwable: Throwable) =
            when {
                ErrorUtils.isConnectionError(throwable) -> mvpView!!.onError(ErrorType.CONNECTION_ERROR)
                ErrorUtils.isTimeOut(throwable) -> mvpView!!.onError(ErrorType.TIMEOUT_ERROR)
                ErrorUtils.isServerError(throwable) -> mvpView!!.onError(ErrorType.SERVER_ERROR)
                ErrorUtils.isClientError(throwable) -> mvpView!!.onError(ErrorType.CLIENT_ERROR)
                else -> {
                    mvpView!!.onError(ErrorType.CONNECTION_ERROR)
                }
            }
}

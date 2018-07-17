package foodapp.com.foodapp.base;

import foodapp.com.foodapp.ErrorType

interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun onError(type: ErrorType)

}

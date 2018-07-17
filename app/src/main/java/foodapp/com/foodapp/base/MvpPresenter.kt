package foodapp.com.foodapp.base

interface MvpPresenter<V : MvpView> {
    fun onAttach(mvpView: V)

    fun onDetach()
}

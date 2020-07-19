package foodapp.com.foodapp.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import foodapp.com.data.network.utils.ErrorUtils
import foodapp.com.domain.fooditems.FoodUsecase
import foodapp.com.foodapp.ErrorType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FoodViewModel @ViewModelInject constructor(
        private val usecase: FoodUsecase) : ViewModel() {

    private var _foodItemsLiveData = MutableLiveData<FoodResult<List<FoodItem>>>()
    private var _foodItemLiveData = MutableLiveData<FoodResult<FoodItem>>()

    val foodItemsLiveData: LiveData<FoodResult<List<FoodItem>>>
        get() = _foodItemsLiveData

    val foodItemLiveData: LiveData<FoodResult<FoodItem>>
        get() = _foodItemLiveData

    @ExperimentalCoroutinesApi
    fun getFoodItems(forceFetch: Boolean) {
        viewModelScope.launch {
            usecase.getFoodItems(forceFetch)
                    .onStart {
                        _foodItemsLiveData.value = FoodResult.Loading
                    }.catch {
                        _foodItemsLiveData.value = FoodResult.Error(it)
                    }.collect {
                        _foodItemsLiveData.value = it
                    }
        }
    }

    fun getFoodItem(id: Int) {
        viewModelScope.launch {
            usecase.getFoodItem(id)
                    .catch {
                        _foodItemLiveData.value = FoodResult.Error(it)
                    }.collect {
                        _foodItemLiveData.value = it
                    }
        }
    }

    fun parseError(throwable: Throwable) = when {
        ErrorUtils.isTimeOut(throwable) -> {
            ErrorType.TIMEOUT_ERROR
        }
        ErrorUtils.isConnectionError(throwable) -> {
            ErrorType.CONNECTION_ERROR
        }
        else -> {
            ErrorType.GENERIC_ERROR
        }
    }
}

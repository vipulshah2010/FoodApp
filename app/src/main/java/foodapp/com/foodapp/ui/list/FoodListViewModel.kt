package foodapp.com.foodapp.ui.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import foodapp.com.data.FoodResult
import foodapp.com.domain.fooditems.FoodUsecase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class FoodListViewModel @ViewModelInject constructor(
        private val usecase: FoodUsecase,
        @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    fun loadFoodItems(forceFetch: Boolean) =
            usecase.getFoodItems(forceFetch)
                    .onStart {
                        emit(FoodResult.Loading)
                    }.catch {
                        emit(FoodResult.Error(Exception("Unknown error!")))
                    }.asLiveData(viewModelScope.coroutineContext)
}

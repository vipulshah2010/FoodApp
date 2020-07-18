package foodapp.com.domain.fooditems

import foodapp.com.data.model.FoodItem
import foodapp.com.data.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodUsecase @Inject
constructor(private val foodRepository: FoodRepository) {

    fun getFoodItems(forceNetworkFetch: Boolean) = foodRepository.getFoodItems(forceNetworkFetch)

    fun getFoodItem(id: String): Flow<FoodItem> = foodRepository.getFoodItem(id)
}
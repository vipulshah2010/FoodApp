package foodapp.com.domain.fooditems

import foodapp.com.data.repository.FoodRepository
import javax.inject.Inject

class FoodUsecase @Inject constructor(private val foodRepository: FoodRepository) {

    fun getFoodItems(forceNetworkFetch: Boolean) = foodRepository.getFoodItems(forceNetworkFetch)

    fun getFoodItem(id: Int) = foodRepository.getFoodItem(id)
}
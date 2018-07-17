package foodapp.com.domain.fooditems

import foodapp.com.data.model.FoodItem
import foodapp.com.data.repository.FoodItemsRepository
import foodapp.com.data.shared.ExecutionThread
import foodapp.com.data.shared.PostExecutionThread
import foodapp.com.domain.base.SingleInteractor
import io.reactivex.Single
import javax.inject.Inject

class GetFoodItemInteractor @Inject
constructor(private val foodItemsRepository: FoodItemsRepository, executionThread: ExecutionThread,
            postExecutionThread: PostExecutionThread) : SingleInteractor<FoodItem,
        GetFoodItemInteractor.Input>(executionThread, postExecutionThread) {

    public override fun buildSingle(params: Input): Single<FoodItem> {
        return foodItemsRepository.getFoodItem(params.id)
    }

    class Input(val id: String)
}

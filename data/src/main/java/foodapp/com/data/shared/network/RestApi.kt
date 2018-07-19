package foodapp.com.data.shared.network

import foodapp.com.data.model.FoodItems
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {

    @GET("bVKAwQVsSW")
    fun getFoodItems(): Single<FoodItems>
}
package foodapp.com.data.network

import foodapp.com.data.model.FoodItems
import retrofit2.http.GET

interface RestApi {

    @GET("bVKAwQVsSW")
    suspend fun getFoodItems(): FoodItems
}
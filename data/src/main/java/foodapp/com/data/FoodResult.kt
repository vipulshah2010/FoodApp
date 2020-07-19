package foodapp.com.data

sealed class FoodResult<out T> {

    data class Success<T>(val data: T) : FoodResult<T>()
    data class Error(val throwable: Throwable) : FoodResult<Nothing>()
    object Loading : FoodResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            Loading -> "Loading"
        }
    }
}
package foodapp.com.data.store.remote

import foodapp.com.data.network.RestApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CloudCloudDataStoreImpl @Inject constructor(private val mRestApi: RestApi) : CloudDataStore {

    @FlowPreview
    override fun getFoodItems() = flow {
        emit(mRestApi.getFoodItems().fooditems)
    }
}

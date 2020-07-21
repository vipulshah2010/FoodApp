package foodapp.com.data.repository.utils

import foodapp.com.data.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProviderImpl constructor(private val dispatcher: TestCoroutineDispatcher) : DispatcherProvider {

    override val default: CoroutineDispatcher
        get() = dispatcher
    override val io: CoroutineDispatcher
        get() = dispatcher
    override val main: CoroutineDispatcher
        get() = dispatcher
}
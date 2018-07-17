package foodapp.com.domain.shared

import foodapp.com.data.shared.ExecutionThread
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IOThread @Inject constructor() : ExecutionThread {

    override val scheduler: Scheduler
        get() = Schedulers.io()
}

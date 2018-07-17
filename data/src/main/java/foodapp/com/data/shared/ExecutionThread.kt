package foodapp.com.data.shared

import io.reactivex.Scheduler

interface ExecutionThread {
    val scheduler: Scheduler
}

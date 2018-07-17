package foodapp.com.data.shared

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}

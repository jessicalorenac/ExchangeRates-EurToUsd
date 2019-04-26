package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.exception.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

abstract class UseCase<out Type, in Params> where Type: Any {

    private var job: Job? = null

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params) =
        CoroutineScope(Dispatchers.IO).async { run(params) }.apply { job = this }

    fun cancel() = job?.cancel()

    class None
}
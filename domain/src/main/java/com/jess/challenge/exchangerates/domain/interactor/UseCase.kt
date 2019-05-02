package com.jess.challenge.exchangerates.domain.interactor

import com.jess.challenge.exchangerates.domain.DomainScope
import com.jess.challenge.exchangerates.domain.Either
import com.jess.challenge.exchangerates.domain.exception.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    private var job: Job? = null

    abstract suspend fun run(params: Params): Either<Failure, Type>

//    operator fun invoke(params: Params) =
//        DomainScope.async(Dispatchers.IO) { run(params) }.apply { job = this }

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val diferredJob = DomainScope.async(Dispatchers.IO) { run(params) }.apply { job = this }
        DomainScope.launch { onResult(diferredJob.await()) }
    }

    fun cancel() = job?.cancel()

    class None
}
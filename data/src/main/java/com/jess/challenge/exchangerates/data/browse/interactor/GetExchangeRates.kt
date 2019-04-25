package com.jess.challenge.exchangerates.data.browse.interactor

import com.jess.challenge.exchangerates.data.browse.EuroExchangeEntity
import com.jess.challenge.exchangerates.data.executor.PostExecutionThread
import com.jess.challenge.exchangerates.data.executor.ThreadExecutor
import com.jess.challenge.exchangerates.data.interactor.FlowableUseCase
import com.jess.challenge.exchangerates.data.repository.ExchangeRepository

/**
 * Use case implementation
 */
class GetExchangeRates(
    val exchangeRepository: ExchangeRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread) :
    FlowableUseCase<List<EuroExchangeEntity>, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?) = exchangeRepository.getExchangeRates()

}
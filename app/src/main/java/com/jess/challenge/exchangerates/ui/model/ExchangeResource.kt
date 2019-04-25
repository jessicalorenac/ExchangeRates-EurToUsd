package com.jess.challenge.exchangerates.ui.model

class ExchangeResource<out T> constructor(val status: ExchangeResourceState, val data: T?, val message: String?) {

    fun <T> success(data: T): ExchangeResource<T> {
        return ExchangeResource(ExchangeResourceState.SUCCESS, data, null)
    }

    fun <T> error(message: String, data: T?): ExchangeResource<T> {
        return ExchangeResource(ExchangeResourceState.ERROR, null, message)
    }

    fun <T> loading(): ExchangeResource<T> {
        return ExchangeResource(ExchangeResourceState.LOADING, null, null)
    }
}
package com.jess.challenge.exchangerates.domain

import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith (MockitoJUnitRunner::class)
abstract class AbstractUnitTest {

    @Suppress("LeakingThis")
    @Rule
    @JvmField val injectMocks = create(this)

    private fun create(testClass: Any) = TestRule { base, _ -> base }.apply { MockitoAnnotations.initMocks(testClass) }

}
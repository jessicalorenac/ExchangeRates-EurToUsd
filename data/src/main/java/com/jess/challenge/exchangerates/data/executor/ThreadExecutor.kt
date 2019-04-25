package com.jess.challenge.exchangerates.data.executor

import java.util.concurrent.Executor

/**
 * Executor implementation for parallel execution, extending from Executor
 * as we'll need to send a Runnable to the #execute method
 */

interface ThreadExecutor: Executor
package com.example.holidays.util

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider @Inject constructor(): ContextProvider {
    override val Main: CoroutineContext by lazy { Dispatchers.Main }
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
}

interface ContextProvider {
    val Main: CoroutineContext
    val IO: CoroutineContext
}
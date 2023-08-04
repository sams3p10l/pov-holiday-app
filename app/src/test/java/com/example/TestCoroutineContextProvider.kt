package com.example

import com.example.holidays.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

class TestCoroutineContextProvider: CoroutineContextProvider() {
    override val Main = Dispatchers.Unconfined
    override val IO = Dispatchers.Unconfined
}
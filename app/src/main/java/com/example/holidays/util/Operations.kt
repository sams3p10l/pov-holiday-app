package com.example.holidays.util

sealed class Operations(val text: String) {
    object None: Operations( "")
    object Common: Operations( "See common holidays")
    object ExclusiveA: Operations("See holidays exclusive for ")
    object ExclusiveB: Operations("See holidays exclusive for ")
}

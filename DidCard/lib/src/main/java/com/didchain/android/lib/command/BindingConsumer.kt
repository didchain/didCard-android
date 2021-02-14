package com.didchain.android.lib.command

interface BindingConsumer<T> {
    fun call(t: T)
}
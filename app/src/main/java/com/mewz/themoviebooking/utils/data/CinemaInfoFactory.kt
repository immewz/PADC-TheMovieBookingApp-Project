package com.mewz.themoviebooking.utils.data

import java.util.Stack

class CinemaInfoFactory {
    val safetyList = mutableListOf<String>()

    fun pushToStack(list:List<String>) : Stack<String> {
        val st = Stack<String>()
        for(element in list) {
            st.push(element)
        }
        return st
    }
}
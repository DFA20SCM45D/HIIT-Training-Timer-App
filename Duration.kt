package com.example.myapplication

class Duration {

    private var woDuration: String = ""
    private var restDuration: String = ""

    fun setWODuration(s: String) {
        woDuration = s
    }

    fun setRestDuraion(s: String){
        restDuration = s
    }

    fun getWODuration(): String {
        return woDuration
    }

    fun getRestDuration(): String {
        return restDuration
    }

    fun getWO() = woDuration


}
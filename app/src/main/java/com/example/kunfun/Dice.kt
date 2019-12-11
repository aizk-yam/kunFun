package com.example.kunfun

// ダイス１個
// ダイスを振る（1個）

class Dice() {

    // インスタンス変数
    var points: Int = throwing()

    fun throwing(): Int {

        val randomInteger: Int
        randomInteger = (1..6).shuffled().first()
        points = randomInteger
        return (points)
    }

}
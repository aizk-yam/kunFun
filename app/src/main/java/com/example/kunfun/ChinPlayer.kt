package com.example.kunfun

// プレーヤ ☓一人
// ダイスセット（３個）を持っている
// １ゲーム分のダイスを振る
// インスタンス同士を比較できる（どちらが強いか）

import android.util.Log

class ChinPlayer(aName: String) {

    // インスタンス変数
    var myName: String = aName
    var myDices: ChinDices = ChinDices()
    var myHand: Hand = Hand.Butame
    var power: Int = 0
    var myPoints: Int = 1000
    var howWon: Judges = Judges.na

    // accessing

    // 三回スローイング（ブタでなければそこで終わり）
    fun setThrowing() {
        myDices.throwing()
        if (myDices.isButame()) myDices.throwing()
        if (myDices.isButame()) myDices.throwing()
        power = myDices.power
        myHand = myDices.myHand
        howWon = Judges.na
    }

    // comparing

    // このオブジェクトを”＝＝”で比較する
    override fun equals(other: Any?): Boolean {
        val aPlayer: ChinPlayer? = other as? ChinPlayer
        return (myDices == aPlayer?.myDices)
    }

    override fun hashCode(): Int {
        return (this.hashCode())
    }

}
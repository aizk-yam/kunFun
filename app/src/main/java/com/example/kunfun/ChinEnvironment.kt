package com.example.kunfun

// 四人のプレーヤを生む
// クリックされたら１回ゲームする
// ゲームのジャッジ（誰がどんな手で何点買ったか／負けたかの判定）ができる

import android.widget.TextView

class ChinEnvironment() {

    // インスタンス変数
    val players: MutableList<ChinPlayer> = mutableListOf<ChinPlayer>()

    // 初期化メソット（必ず呼ばれる）
    init {
        players.add(ChinPlayer("PlayerU"))
        players.add(ChinPlayer("PlayerA"))
        players.add(ChinPlayer("PlayerB"))
        players.add(ChinPlayer("PlayerC"))
    }

    // 1回分（「NEXT」ボタンのクリックのたびに呼ばれる)
    fun onePlay(aBet: Int): Judges {

        for (eachPlayer in players) {
            eachPlayer.setThrowing()
        }

        return (judging(aBet))

    }

    // 地方によってルールが少し違うかもしれない
    fun judging(bet: Int): Judges {

        val howWon: Judges
        val sortedPlayers: List<ChinPlayer> = players.sortedWith(compareBy({ it.myHand.power }, { it.power }))

        // 上位二人が同点ならDrowで、チャラ
        if ((sortedPlayers.last()) == (sortedPlayers.takeLast(2).first())) {
            howWon = Judges.Drow
            sortedPlayers.last().howWon = howWon
            sortedPlayers.takeLast(2).first().howWon = howWon
            return (howWon)
        }

        // 普通の順位づけ（得点移動あり）
        val winerPlayer: ChinPlayer = sortedPlayers.last()
        val winerPower: Int = winerPlayer.power

        // 勝者が１２３か判定
        howWon = if (winerPower >= 0) Judges.Win else Judges.Fall
        winerPlayer.howWon = howWon

        // 敗者から勝者（１２３含む）にポイントを移動する
        // （勝者以外の人から勝者（１２３含む）への移動のみ）
        for (eachPlayer: ChinPlayer in sortedPlayers.filter { it -> it != winerPlayer }) {
            eachPlayer.myPoints -= winerPower * bet
            winerPlayer.myPoints += winerPower * bet
        }

        return (howWon)
    }

}
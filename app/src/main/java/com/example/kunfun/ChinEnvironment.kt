package com.example.kunfun

// 四人のプレーヤを生む
// クリックされたら１回ゲームする
// ゲームのジャッジ（誰がどんな手で何点買ったか／負けたかの判定）ができる

class ChinEnvironment {

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
    fun onePlay(aBet: Int) {

        for (eachPlayer in players) {
            eachPlayer.setThrowing()
        }
        judging(aBet)
        return

    }

    // 地方によってルールが少し違うかもしれない
    private fun judging(aBet: Int) {

        val sortedPlayers: List<ChinPlayer> = players.sortedWith(compareBy({ it.myHand.power }, { it.power }))

        // 上位二人が同点ならDrowで、チャラ
        val top2Players: List<ChinPlayer> = sortedPlayers.takeLast(2)
        if ((top2Players.first()) == (top2Players.last())) {
            top2Players.first().howWon = Judges.Drow
            top2Players.last().howWon = Judges.Drow
            return
        }

        // 普通の順位づけ（得点移動あり）
        val winnerPlayer: ChinPlayer = sortedPlayers.last()
        val winnerPower: Int = winnerPlayer.power

        // 勝者が１２３か判定
        winnerPlayer.howWon = if (winnerPower >= 0) Judges.Win else Judges.Fall

        // 敗者から勝者（１２３含む）にポイントを移動する
        // （勝者以外の人から勝者（１２３含む）への移動のみ）
        for (eachPlayer: ChinPlayer in sortedPlayers.filter { it -> it != winnerPlayer }) {
            eachPlayer.myPoints -= winnerPower * aBet
            winnerPlayer.myPoints += winnerPower * aBet
        }

        return
    }

}
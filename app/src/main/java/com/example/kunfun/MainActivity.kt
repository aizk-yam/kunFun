package com.example.kunchin

// 全体の環境を生成
// 画面とプログラムを結びつける
// 画面への出力

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.kunfun.R

// ↓のimportで　findViewById　が不要になる!!!!
import kotlinx.android.synthetic.main.activity_main.*

// このクラスパッケージのインスタンス全体で使うemun型(グローバルenum）
enum class Hand(val power: Int) {
    Butame(1),
    Yakume(2),
    Sigoro(3),
    Zorome(4),
    Hifumi(99)
}

enum class Judges {
    Win, Fall, Drow, na
}

// ★★★　viewをMainで簡易的につくっているので、、これのList型で表現。　本来は「データバインディング」で実装する
data class ViewSet(var bt: Button, var tv1: TextView, var tv2: TextView, var tv3: TextView, var player: ChinPlayer)


// いわゆる「メイン」　
class MainActivity : AppCompatActivity() {

    // インスタンス変数
    val thisEnvironment: ChinEnvironment = ChinEnvironment()
    var viewList: MutableList<ViewSet> = mutableListOf<ViewSet>()
    var playCnt = 0

    // "onCreate"イベントを拾う；ここから始まる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewList.add(ViewSet(bt_P1, tv_P1_1, tv_P1_2, tv_P1_3, thisEnvironment.players[0]))
        viewList.add(ViewSet(bt_P2, tv_P2_1, tv_P2_2, tv_P2_3, thisEnvironment.players[1]))
        viewList.add(ViewSet(bt_P3, tv_P3_1, tv_P3_2, tv_P3_3, thisEnvironment.players[2]))
        viewList.add(ViewSet(bt_P4, tv_P4_1, tv_P4_2, tv_P4_3, thisEnvironment.players[3]))

        val myTurn1 = TurnOfPlayer(viewList[0])
        val myTurn2 = TurnOfPlayer(viewList[1])
        val myTurn3 = TurnOfPlayer(viewList[2])
        val myTurn4 = TurnOfPlayer(viewList[3])

        //        Log.d("myTAG", "登録前")     // debug-write

        // 画面のボタンとプログラムを結びつける
        bt_P1.setOnClickListener(myTurn1)          // リスナ関数を登録
        bt_P2.setOnClickListener(myTurn2)          // リスナ関数を登録
        bt_P3.setOnClickListener(myTurn3)          // リスナ関数を登録
        bt_P4.setOnClickListener(myTurn4)          // リスナ関数を登録

        bt_PN.setOnClickListener(NextListener())          // リスナ関数を登録
        // ★nextListenerのインスタンス化してないけど？　いいのか？　動く。。。

        bt_PN.isEnabled = false

    }

    // リスナとして登録されるクラス　★クラス名は大文字で始まる
    private inner class NextListener : View.OnClickListener {
        // onClickイベントを拾うメソッドをオーバーライド
        override fun onClick(view: View) {

            for (aView in viewList) {
                aView.tv1.text = ""
                aView.tv2.text = ""
                aView.tv3.text = ""
                aView.bt.isEnabled = true
            }
            bt_PN.isEnabled = false

        }
    }

    private inner class TurnOfPlayer(aViewSet: ViewSet) : View.OnClickListener {

        val myButton: Button = aViewSet.bt
        val textView1: TextView = aViewSet.tv1
        val textView2: TextView = aViewSet.tv2
        val aPlayer: ChinPlayer = aViewSet.player

        // onClickイベントを拾うメソッドをオーバーライド
        override fun onClick(v: View?) {

            myButton.isEnabled = false

//            Log.d("myTAG", "onClick in..." )     // debug-write


            // 指定プレーヤにサイを振らせる
            aPlayer.setThrowing()
            textView1.text = aPlayer.myHandMe
            textView2.text = aPlayer.myHandName + "(" + aPlayer.myHandKachime + ") "

            playCnt += 1

            if (playCnt == 4) { // 4人プレーしたら判定（judging()）へ

                val aBet: Int? = betValue.text.toString().toIntOrNull()
                if (aBet == null) return  // Betを入力項目からget

                bt_PN.isEnabled = true
                thisEnvironment.judging(aBet)
                putview()   // 画面への出力

                playCnt = 0
            }
        }

    }

    fun putview() {

        val playersPuts: MutableList<String> = mutableListOf<String>()

        for (eachPlayer in thisEnvironment.players) {
            var bufString: String =
                if (eachPlayer.howWon != Judges.na) " <" + eachPlayer.howWon.toString() + "> " else "　　　　"
            bufString += eachPlayer.myPoints.toString()

            playersPuts.add(bufString)
        }

//            Log.d("myTAG", "After string Set")     // debug-write

        tv_P1_3.text = playersPuts[0]
        tv_P2_3.text = playersPuts[1]
        tv_P3_3.text = playersPuts[2]
        tv_P4_3.text = playersPuts[3]

    }

}

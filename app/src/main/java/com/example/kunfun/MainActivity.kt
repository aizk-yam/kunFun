package com.example.kunfun

// 全体の環境を生成
// 画面とプログラムを結びつける
// 画面への出力

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

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


// いわゆる「メイン」　
class MainActivity : AppCompatActivity() {

    // インスタンス変数
    val thisEnvironment: ChinEnvironment = ChinEnvironment()


    // "onCreate"イベントを拾う；ここから始まる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        Log.d("myTAG", "登録前")     // debug-write
        // 画面のボタンとプログラムを結びつける
        val btClick = findViewById<Button>(R.id.btClick)    // ボタンを特定
        btClick.setOnClickListener(nextListener())          // リスナ関数を登録

        putview()

    }

    // リスナとして登録されるクラス
    private inner class nextListener : View.OnClickListener {

        // onClickイベントを拾うメソッドをオーバーライド
        override fun onClick(view: View) {

//            Log.d("myTAG", "onclick")     // debug-write

            val tview0 = findViewById<EditText>(R.id.bet)
            val aBet = tview0.text.toString().toIntOrNull()     // Nullを無視できない（下のif文も必要）
            if (aBet != null) {
                thisEnvironment.onePlay(aBet)   // Betを入力項目からget
            }

            putview()   // 画面への出力
        }

    }

    // 以降は普通のプログラム

    fun putview() {
//            Log.d("myTAG", "After Throwing")     // debug-write

        val playersPuts: MutableList<String> = mutableListOf<String>()

        for (eachPlayer in thisEnvironment.players) {
            var bufString: String? = eachPlayer.myName
            bufString += ": "
            bufString += eachPlayer.myDices.myHandMe()
            bufString += eachPlayer.myDices.myHandName() + "(" + eachPlayer.myDices.kachime.toString() + ") "
            bufString += if (eachPlayer.howWon != Judges.na) " <" + eachPlayer.howWon.toString() + "> " else "　　　　"
            bufString += eachPlayer.myPoints.toString()

            if (bufString != null) {
                playersPuts.add(bufString)
            }
        }

//            Log.d("myTAG", "After string Set")     // debug-write

        val tview1 = findViewById<TextView>(R.id.forUserA)
        tview1.setText(playersPuts[0])

        val tview2 = findViewById<TextView>(R.id.forUserB)
        tview2.setText(playersPuts[1])

        val tview3 = findViewById<TextView>(R.id.forUserC)
        tview3.setText(playersPuts[2])

        val tview4 = findViewById<TextView>(R.id.forUserD)
        tview4.setText(playersPuts[3])


    }

}

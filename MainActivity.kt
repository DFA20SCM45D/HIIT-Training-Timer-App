package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    var counter = 0
    public final lateinit var a:String
    var isRunning: Boolean = false
    var isPaused: Boolean = false
    lateinit var countDownTimer: CountDownTimer
    var c: Long = 0
    var inputTime: Long = 0
    var countAct = 0
    private val timeList = mutableListOf<String>()
    private var finalList = mutableListOf<Duration>()

    private var time: String = ""
    private var wo: Boolean = true
    var i: Int = 0
    private var activityFinished: Boolean = false
    var t: Int = 0
    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("HIIT TRAINING TIMER")

    }

    public fun startTimer(view: View) {

        controlTimer(finalList)

    }

    public fun addActivity(view: View) {

        var a01: TextView = findViewById(R.id.a1)
        var a02: TextView = findViewById(R.id.a2)
        var r01: TextView = findViewById(R.id.r1)
        var r02: TextView = findViewById(R.id.r2)

        var duration = Duration()

        var enterTime1: TextView = findViewById(R.id.enterTime1)
        var a1 = enterTime1.text.toString()

        var enterTime2: TextView = findViewById(R.id.enterTime2)
        var a2 = enterTime2.text.toString()

        if(t == 0) {
            a01.text = a1
            r01.text = a2
            t++
        } else {
            a02.text = a1
            r02.text = a2
            t = 0
        }

        duration.setWODuration(a1)
        Log.d(TAG, "controlTimer: $a1")
        duration.setRestDuraion(a2)
        Log.d(TAG, "controlTimer: $a2")
        finalList.add(duration)

        enterTime1.text = ""
        enterTime2.text = ""

        Log.d(TAG, "addActivity: getWO ${finalList.get(0).getWO()}")
    }

    private fun controlTimer(d: List<Duration>) {

        var tempDuration: Duration
        tempDuration = d.get(i)

        if(wo == true) {
            time = tempDuration.getWO()

        } else
        {
            time = tempDuration.getRestDuration()
        }

        StartTimerCounter(time)
    }

   private fun StartTimerCounter(a: String) {

            Log.d(TAG, "StartTimerCounter: $a ")
           val countTime: TextView = findViewById(R.id.countTime)

           val button: Button = findViewById(R.id.button)

           if (isPaused) {
               inputTime = c
           } else {
               if (a != "")
                   inputTime = a.toLong() * 1000
               else
                   return
           }

           if (isRunning) {
               pauseTimer(inputTime)
           } else {
               button.text = "Pause"

               isRunning = true
               countDownTimer = object : CountDownTimer(inputTime, 1000) {

                   override fun onTick(p0: Long) {
                       countTime.text = counter.toString()
                       counter++
                       if(counter > 4) {
                           stopSound()
                       }
                   }

                   override fun onFinish() {
                       playSound()
                       countTime.text = "Finished"
                       button.text = "start"
                       isPaused = false
                       isRunning = false
                       counter = 0
                       wo = false
                       if (countAct < 1) {
                          countAct++
                         controlTimer(finalList)
                       } else
                       {
                          countAct = 0
                           i++
                           wo = true
                           if(i < finalList.size){
                               controlTimer(finalList)
                           } else {
                               playSound()
                           }
                      }
                   }
               }.start()
           }
    }

    private fun pauseTimer(a: Long){
        val button: Button = findViewById(R.id.button)
            countDownTimer.cancel();
            button.text = "start"
            isRunning = false
            isPaused = true

        c = (a - counter * 1000)
        val countTime: TextView = findViewById(R.id.countTime)
        countTime.text = "Paused"
    }

    fun onStop(view: View) {
        stopSound()
        if (isRunning == true || isPaused == true) {
            //  super.onStop()
            val stop: Button = findViewById(R.id.stop)
            val button: Button = findViewById(R.id.button)
            button.text = "start"
            isPaused = false
            isRunning = false
            countDownTimer.cancel();
            c = 0
            val enterTime: TextView = findViewById(R.id.enterTime1)
            enterTime.text = ""
            val countTime: TextView = findViewById(R.id.countTime)
            countTime.text = "Start"
            counter = 0
        }
}
   private fun playSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.water)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()

       //stopSound()
    }

    // 2. Pause playback
    private fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) mMediaPlayer?.pause()
    }

    // 3. Stops playback
    private fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }
}
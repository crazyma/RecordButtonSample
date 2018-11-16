package com.crazyma.recordbuttonsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    companion object {
        const val TAG = "RecordButton"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
        recordButton.setOnClickListener {
            Log.d(TAG,"on click")
        }
        
        recordButton.onRecordListener = object : RecordButton.OnRecordListener{
            override fun onStartRecording() {
                Log.d(TAG,"on start recording")
            }

            override fun onCancelRecording() {
                Log.d(TAG,"on cancel recording")
            }

            override fun onFinishRecording() {
                Log.d(TAG,"on finish recording")
            }

        }
    }

}

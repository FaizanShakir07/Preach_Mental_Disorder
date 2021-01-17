package com.preach.mentaldisorder.Utils.GeneralUtils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.util.*
import kotlin.collections.ArrayList

class SpeechRecognizer(var context: Context?, var callback: OnDataFetchCallback) {



        private fun checkAudioPermissions()
        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                startListeningWithoutDialog()

            } else {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
                Permissions.check(context, permissions, null, null, object : PermissionHandler() {
                    override fun onGranted() {
                        startListeningWithoutDialog()
                    }

                    override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                        callback.onErrorData("Audio Permissions required ")
                        super.onDenied(context, deniedPermissions)

                    }
                })
            }
        }
        fun startSpeechRecognizer()
        {
            checkAudioPermissions()
        }

        private fun startListeningWithoutDialog() {
            var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context?.packageName);
            var listener = CustomRecognitionListener();
            var sr = SpeechRecognizer.createSpeechRecognizer(context);
            sr.setRecognitionListener(listener);
            sr.startListening(intent);
        }




    inner class CustomRecognitionListener : RecognitionListener {

        private val TAG = "RecognitionListener"

        override fun onReadyForSpeech(params: Bundle) {
            Log.d(TAG, "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d(TAG, "onRmsChanged")
        }

        override fun onBufferReceived(buffer: ByteArray) {
            Log.d(TAG, "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech")
        }

        override fun onError(error: Int) {
            callback.onErrorData("Error: "+error)

        }

        override fun onResults(results: Bundle) {
            val result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if(!result.isNullOrEmpty())
            {
                callback.onContinueData(result[0])
                callback.onFetchVoiceData(result[0])
            }
            else
            {
                callback.onErrorData("Please try again ")
            }

        }

        override fun onPartialResults(partialResults: Bundle) {
            val result = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if(!result.isNullOrEmpty())
            {
                callback.onContinueData(result[0])
            }
            else
            {
                callback.onErrorData("Please try again ")
            }
        }

        override fun onEvent(eventType: Int, params: Bundle) {
            Log.d(TAG, "onEvent $eventType")
        }
    }



    interface OnDataFetchCallback {
        fun onFetchVoiceData(data: String)
        fun onErrorData(error: String)
        fun onContinueData(data: String)

    }

}
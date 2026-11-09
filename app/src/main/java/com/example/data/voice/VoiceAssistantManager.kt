package com.example.data.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

sealed class VoiceState {
    object Idle : VoiceState()
    object Listening : VoiceState()
    data class Processing(val textSpoken: String) : VoiceState()
    data class Speaking(val textToSpeak: String) : VoiceState()
    data class Error(val message: String) : VoiceState()
}

class VoiceAssistantManager(
    private val context: Context,
    private val onCommandRecognized: (String) -> Unit
) : TextToSpeech.OnInitListener {

    private val _voiceState = MutableStateFlow<VoiceState>(VoiceState.Idle)
    val voiceState = _voiceState.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private var textToSpeech: TextToSpeech? = null
    private var isTtsInitialized = false

    var isMicActive = false
        private set

    init {
        initSpeechRecognizer()
        initTextToSpeech()
    }

    private fun initSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {
                        _voiceState.value = VoiceState.Listening
                    }

                    override fun onBeginningOfSpeech() {
                        _voiceState.value = VoiceState.Listening
                    }

                    override fun onRmsChanged(rmsdB: Float) {}

                    override fun onBufferReceived(buffer: ByteArray?) {}

                    override fun onEndOfSpeech() {
                        isMicActive = false
                    }

                    override fun onError(error: Int) {
                        isMicActive = false
                        val errMsg = when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "لم يتم التعرف على الصوت، حاول مرة أخرى."
                            SpeechRecognizer.ERROR_AUDIO -> "خطأ في تسجيل الصوت من الميكروفون."
                            SpeechRecognizer.ERROR_NETWORK -> "خطأ في الاتصال بالشبكة لمعالجة الصوت."
                            else -> "تعذر التعرف على الكلام ($error)"
                        }
                        _voiceState.value = VoiceState.Error(errMsg)
                    }

                    override fun onResults(results: Bundle?) {
                        isMicActive = false
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val spokenText = matches?.firstOrNull() ?: ""
                        if (spokenText.isNotBlank()) {
                            _voiceState.value = VoiceState.Processing(spokenText)
                            onCommandRecognized(spokenText)
                        } else {
                            _voiceState.value = VoiceState.Idle
                        }
                    }

                    override fun onPartialResults(partialResults: Bundle?) {
                        val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val partial = matches?.firstOrNull() ?: ""
                        if (partial.isNotBlank()) {
                            _voiceState.value = VoiceState.Processing(partial)
                        }
                    }

                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }
        }
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("ar"))
            isTtsInitialized = (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED)
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    _voiceState.value = VoiceState.Idle
                }
                override fun onError(utteranceId: String?) {
                    _voiceState.value = VoiceState.Idle
                }
            })
        }
    }

    fun startListening() {
        // Immediate user permission & feedback: Mic turns on strictly on action
        if (isMicActive) return
        stopSpeaking() // Interrupt TTS if speaking

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ar")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        try {
            isMicActive = true
            _voiceState.value = VoiceState.Listening
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            isMicActive = false
            _voiceState.value = VoiceState.Error("خطأ في تشغيل الميكروفون: ${e.localizedMessage}")
        }
    }

    fun stopListening() {
        if (isMicActive) {
            isMicActive = false
            speechRecognizer?.stopListening()
            _voiceState.value = VoiceState.Idle
        }
    }

    fun speak(text: String, speechRate: Float = 1.0f) {
        if (!isTtsInitialized || text.isBlank()) return
        stopListening()

        _voiceState.value = VoiceState.Speaking(text)
        textToSpeech?.setSpeechRate(speechRate)
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "METRA_TTS_UTTERANCE")
        }
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "METRA_TTS_UTTERANCE")
    }

    fun stopSpeaking() {
        if (textToSpeech?.isSpeaking == true) {
            textToSpeech?.stop()
        }
        if (_voiceState.value is VoiceState.Speaking) {
            _voiceState.value = VoiceState.Idle
        }
    }

    fun shutdown() {
        speechRecognizer?.destroy()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

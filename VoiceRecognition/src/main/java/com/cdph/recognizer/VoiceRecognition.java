/*
*This library was created for the sake
*of others so that they won't have the
*struggle of implementing SpeechRecognizer
*to their projects.
*
*This library is created by SnoopyCodeX
*from CyberDroid Developers PH
*
*Visit and like our page: https://fb.me/cdphdevelopers
*
*@package	VoiceReconition.java
*@author	CyberDroid Developers PH
*@copyright	2019-2020
*/

package com.cdph.recognizer;

import android.content.Context;
import android.content.Intent;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.os.Bundle;
import java.util.Locale;

public final class VoiceRecognition implements RecognitionListener
{
	private OnVoiceReceivedListener listener;
	private SpeechRecognizer recognizer;
	private Context ctx;
	private Intent recogIntent;
	private boolean isListening = false;
	
	/*
	*This constructor was purposely set to PRIVATE
	*so that we can use SINGLETON when using this
	*class
	*
	*@constructor	VoiceRecognition
	*/
	private VoiceRecognition()
	{}
	
	/*
	*This constructor was purposely set to PRIVATE
	*so that we can use SINGLETON when using this
	*class
	*
	*@constructor	VoiceRecognition
	*@param		Context - application context
	*/
	private VoiceRecognition(Context ctx)
	{
		this.ctx = ctx;
		
		this.recognizer = SpeechRecognizer.createSpeechRecognizer(ctx);
		this.recogIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		this.recogIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		this.recogIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
		this.recognizer.setRecognitionListener(this);
	}
	
	/*
	*This method is used to create a new
	*instance of this class
	*
	*@param		Context - application context
	*@return	VoiceRecognition
	*/
	public static final VoiceRecognition bindWith(Context ctx)
	{
		return (new VoiceRecognition(ctx));
	}
	
	/*
	*This method is used to set listener
	*so that we can keep track if the user
	*spoke a word
	*
	*@param		VoiceRecognition.OnVoiceReceivedListener - voice listener
	*@return	null
	*/
	public void setOnVoiceReceivedListener(VoiceRecognition.OnVoiceReceivedListener listener)
	{
		this.listener = listener;
	}
	
	/*
	*This method is used to start the
	*voice recognition
	*
	*@return	null
	*/
	public void startVoiceRecognition()
	{
		if(listener != null)
		{
			if(recognizer.isRecognitionAvailable(ctx)) 
			{
				this.recognizer.startListening(recogIntent);
				this.isListening = true;
			}
			else
				listener.onVoiceRecognitionNotSupported("Voice recognition is not supported on this device");
		}
	}
	
	/*
	*This method is used to
	*stop the voice recognition
	*
	*@return	null
	*/
	public void stopVoiceRecognition()
	{
		if(isListening)
			this.recognizer.stopListening();
		this.isListening = false;
	}

	@Override
	public void onReadyForSpeech(Bundle args)
	{}

	@Override
	public void onBeginningOfSpeech()
	{}

	@Override
	public void onRmsChanged(float rms)
	{}

	@Override
	public void onBufferReceived(byte[] data)
	{}

	@Override
	public void onEndOfSpeech()
	{}

	@Override
	public void onError(int error)
	{}

	@Override
	public void onResults(Bundle args)
	{
		if(listener != null)
		{
			String result = args.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
			listener.onVoiceReceived(result);
		}
	}

	@Override
	public void onPartialResults(Bundle args)
	{
		if(listener != null)
		{
			String result = args.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
			listener.onVoiceReceived(result);
		}
	}

	@Override
	public void onEvent(int event, Bundle args)
	{}
	
	public static final interface OnVoiceReceivedListener
	{
		public void onVoiceReceived(String voice);
		public void onVoiceRecognitionNotSupported(String msg);
	}
}

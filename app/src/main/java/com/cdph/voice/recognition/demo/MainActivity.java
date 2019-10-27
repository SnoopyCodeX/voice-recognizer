/*
*This sample is created by SnoopyCodeX
*from CyberDroid Developers PH
*
*Visit and like our page: https://fb.me/cdphdevelopers
*
*@package	com.cdph.voice.recognition.demo.MainActivity.java
*@author	CyberDroid Developers PH
*@copyright	2019 - 2020
*/

package com.cdph.voice.recognition.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.cdph.recognizer.VoiceRecognition;
import com.cdph.recognizer.VoiceRecognition.OnVoiceReceivedListener;

public class MainActivity extends Activity implements OnVoiceReceivedListener
{
	private VoiceRecognition recognizer;
	private Button startStopButton;
	private TextView display;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		this.recognizer = VoiceRecognition.bindWith(this);
		this.recognizer.setOnVoiceReceivedListener(this);
		
		this.startStopButton = (Button) findViewById(R.id.startStop);
		this.display = (TextView) findViewById(R.id.display);
		
		this.startStopButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				boolean ret = false;
				int action = event.getAction();
				
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
						recognizer.startVoiceRecognition();
						ret = true;
					break;
					
					case MotionEvent.ACTION_UP:
						recognizer.stopVoiceRecognition();
						ret = true;
					break;
				}
				
				return ret;
			}
		});
    }
	
	@Override
	public void onVoiceReceived(String voice)
	{
		display.setText((display.getText().toString() + " " + voice));
	}

	@Override
	public void onVoiceRecognitionNotSupported(String msg)
	{
		display.setText("Error: " + msg);
	}
}

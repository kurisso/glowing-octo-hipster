package com.example.test;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String RECORDED_AUDIO = "sound.3gp";
	private MediaPlayer mPlayer;
	private MediaRecorder mRecorder;
	private File mSound;
	private Button recordButton;
	private Button playButton;

	/*sashimi shine*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSound = new File(getCacheDir(), RECORDED_AUDIO);
		recordButton = (Button) findViewById(R.id.record);
		playButton = (Button) findViewById(R.id.play);
		recordButton.setOnClickListener(new recordButtonClick());
		playButton.setOnClickListener(new playButtonClick());

		if (mSound.exists()) {
			playButton.setEnabled(true);
		} else {
			playButton.setEnabled(false);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// recordButtonClick
	public class recordButtonClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (mSound.exists()) {
				playButton.setEnabled(true);
			} else {
				playButton.setEnabled(false);
			}

			if (recordButton.getText().equals(getString(R.string.record_button))) {
				recordButton.setText(R.string.record_stop);
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mRecorder.setOutputFile(mSound.getAbsolutePath());

				try {
					mRecorder.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mRecorder.start();
			} else {
				recordButton.setText(R.string.record_button);
				mRecorder.stop();
			}

		}

	}

	// playButtonClick
	public class playButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (playButton.getText().equals(getString(R.string.play_button))) {
				playButton.setText(R.string.play_stop);

				try {
					mPlayer.setDataSource(mSound.getAbsolutePath());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					mPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mPlayer.start();
			} else {
				playButton.setText(R.string.play_button);
				mPlayer.stop();
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mPlayer = new MediaPlayer();
		mRecorder = new MediaRecorder();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				playButton.setText(R.string.play_button);
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mPlayer.release();
		mRecorder.release();
	}
}

package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.util.Tools;

public class TimerActivity extends Activity {	

	private Chronometer chronometer;
	
	private long time;
	
	private boolean isStarted;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		time = 0;
		isStarted = true;
		intent = getIntent();
		setContentView(R.layout.timer);
		chronometer = (Chronometer) findViewById(R.id.timer_chronometer);	
		
		intent.removeExtra(Tools.TIMER_TIME_EXTRA);
		intent.removeExtra(Tools.TIMER_WEIGHT_EXTRA);
		
	}
	
	public void startTimer(View view) {
		if (isStarted) { 
			isStarted = false;
			chronometer.setBase(SystemClock.elapsedRealtime());
			chronometer.start();
		}
	}
	
	public void stopTimer(View view) {
		chronometer.stop();
		time = getElapsedSeconds();
		
		LinearLayout timerButtons = (LinearLayout) findViewById(R.id.timer_buttons);
		LinearLayout timerStats = (LinearLayout) findViewById(R.id.timer_stats);
		TextView timeValue = (TextView) findViewById(R.id.timer_time_value);
	   		
		timeValue.setText(this.chronometer.getText());
		//remove timer buttons
		timerButtons.setVisibility(8);	
		chronometer.setVisibility(8);
		//set visibility to visible
		timerStats.setVisibility(0);
	}

	public void okButtonClick(View view) {
		EditText weightField = (EditText) findViewById(R.id.timer_weight_field);
		
		if (Tools.isNullOrEmpty(weightField.getText())) {
			Toast.makeText(getApplicationContext(),
					"Please, enter weight.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		intent.putExtra(Tools.TIMER_TIME_EXTRA, time);
		intent.putExtra(Tools.TIMER_WEIGHT_EXTRA, weightField.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private long getElapsedSeconds() {
		String chronoText = chronometer.getText().toString();
		String array[] = chronoText.split(":");
		long elapsedSeconds = 0;
		if (array.length == 2) {
			elapsedSeconds = Integer.parseInt(array[0]) * 60
					+ Integer.parseInt(array[1]);
		} else if (array.length == 3) {
			elapsedSeconds = Integer.parseInt(array[0]) * 60 * 60 
					+ Integer.parseInt(array[1]) * 60
					+ Integer.parseInt(array[2]);
		}
		
		return elapsedSeconds;
	}
}
package bg.su.fmi.fitness.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FitnessAssistantActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Check commit by sefo
        //commit by vel
        TextView textView = new TextView(this);
        textView.setText(R.string.app_name);
        
        setContentView(textView);
    }
}
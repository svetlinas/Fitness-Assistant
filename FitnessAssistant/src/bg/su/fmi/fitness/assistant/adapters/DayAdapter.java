package bg.su.fmi.fitness.assistant.adapters;

import java.util.ArrayList;
import java.util.List;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayAdapter extends ArrayAdapter<Day>{
	private final Context context;
	private final ArrayList<Day> values;

	public  DayAdapter(Context context,  ArrayList<Day> values) {
		super(context, R.layout.workout_day_view, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.workout_day_view, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.day_vlaue);
		textView.setText(Long.toString(values.get(position).getNumber()));
		
		LinearLayout exercisesList = (LinearLayout)rowView.findViewById(R.id.day_exercises);
		exercisesList.removeAllViews();
		List<Exersize> exercises = values.get(position).getExercises();
		
		for(Exersize exercise : exercises)
		{
			View row = inflater.inflate(R.layout.day_exercise, null);
			
			TextView exerciseTitle = (TextView) row.findViewById(R.id.day_exercise_value);
			exerciseTitle.setText(exercise.getName());
			TextView exerciseSets = (TextView) row.findViewById(R.id.day_sets_value);
			exerciseSets.setText(Long.toString(exercise.getSets()));
			TextView exerciseRepetitions = (TextView) row.findViewById(R.id.day_repetitions_value);
			exerciseRepetitions.setText(Long.toString(exercise.getRepetitions()));
			
			exercisesList.addView(row);
		}
		
		return rowView;
	}
}

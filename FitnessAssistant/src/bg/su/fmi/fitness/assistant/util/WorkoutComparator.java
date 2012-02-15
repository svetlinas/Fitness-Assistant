package bg.su.fmi.fitness.assistant.util;

import android.graphics.Color;

public final class WorkoutComparator {
	
	private WorkoutComparator() {
		// No instances
	}
	
	public static int getTimeColor(long timeFirst, long timeSecond) { 
		if(timeFirst > timeSecond) {
			return Color.parseColor("#FF0000"); // red
		}
		if(timeFirst < timeSecond) {
			return Color.parseColor("#66FF33"); // green
		}
		return Color.parseColor("#FFFFFF");  // white
	}
	
	public static int getWeightColor(double weightFirst, double weightSecond) { 
		if(weightFirst < weightSecond) {
			return Color.parseColor("#FF0000"); // red
		}
		if(weightFirst > weightSecond) {
			return Color.parseColor("#66FF33"); // green
		}
		return Color.parseColor("#FFFFFF");  // white
	}

}

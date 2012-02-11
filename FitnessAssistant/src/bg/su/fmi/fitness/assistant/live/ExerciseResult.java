package bg.su.fmi.fitness.assistant.live;

import bg.su.fmi.fitness.assistant.entities.Exersize;

public class ExerciseResult {

	private Exersize exercise;

	private double maxWight;

	private long time;

	public Exersize getExercise() {
		return exercise;
	}

	public void setExercise(Exersize exercise) {
		this.exercise = exercise;
	}

	public double getMaxWight() {
		return maxWight;
	}

	public void setMaxWight(double maxWight) {
		this.maxWight = maxWight;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ExerciseResult(Exersize exercise, double maxWeight, long time) {
		this.exercise = exercise;
		this.maxWight = maxWeight;
		this.time = time;
	}

	public ExerciseResult(ExerciseResult other) {
		this(other.getExercise(), other.getMaxWight(), other.getTime());
	}

}

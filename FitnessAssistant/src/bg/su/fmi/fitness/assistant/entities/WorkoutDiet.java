package bg.su.fmi.fitness.assistant.entities;

public class WorkoutDiet {

	private long id;
	private long workoutId;
	private long exersizeId;
	
	public WorkoutDiet(long id, long workoutId, long exersizeId) {
		super();
		this.id = id;
		this.workoutId = workoutId;
		this.exersizeId = exersizeId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(long workoutId) {
		this.workoutId = workoutId;
	}

	public long getExersizeId() {
		return exersizeId;
	}

	public void setExersizeId(long exersizeId) {
		this.exersizeId = exersizeId;
	}

}
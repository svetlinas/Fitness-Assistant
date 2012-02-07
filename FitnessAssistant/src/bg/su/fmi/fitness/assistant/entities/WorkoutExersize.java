package bg.su.fmi.fitness.assistant.entities;

import java.util.Date;

public class WorkoutExersize {

	private long id;
	private long workoutId;
	private long exersizeId;
	private int days;
	private Date created;
	
	public WorkoutExersize(long id, long workoutId, long exersizeId,
			int days, Date created) {
		super();
		this.id = id;
		this.workoutId = workoutId;
		this.exersizeId = exersizeId;
		this.days = days;
		this.created = created;
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

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
}

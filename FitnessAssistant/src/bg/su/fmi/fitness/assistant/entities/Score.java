package bg.su.fmi.fitness.assistant.entities;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {

	private long id;
	private long workoutId;
	private long exersizeId;
	private int setNumber;
	private double weight;
	private Date time;
	private Date created;
	
	public Score(long id, long workoutId, long exersizeId,
			int setNumber, double weight, Date time, Date created) {
		super();
		this.id = id;
		this.workoutId = workoutId;
		this.exersizeId = exersizeId;
		this.setNumber = setNumber;
		this.weight = weight;
		this.time = time;
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

	public int getSetNumber() {
		return setNumber;
	}

	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
}

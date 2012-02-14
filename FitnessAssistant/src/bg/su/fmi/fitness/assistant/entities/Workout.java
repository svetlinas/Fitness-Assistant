package bg.su.fmi.fitness.assistant.entities;

import java.io.Serializable;

public class Workout implements Serializable {

	private static final long serialVersionUID = 9206226494418505604L;
	private long id;
	private String gender;
	private String type;
	private int duration;
	private String name;
	
	public Workout(long id, String gender, String type, int duration,
			String name) {
		super();
		this.id = id;
		this.gender = gender;
		this.type = type;
		this.duration = duration;
		this.name = name;
	}
	
	public Workout()
	{
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}

package bg.su.fmi.fitness.assistant.entities;

public class Workout {

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

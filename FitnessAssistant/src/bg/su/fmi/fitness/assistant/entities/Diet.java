package bg.su.fmi.fitness.assistant.entities;

public class Diet {

	private long id;
	private String type;
	private int duration;
	private String name;
	private String description;
	
	public Diet(long id, String type, int duration, String name,
			String description) {
		super();
		this.id = id;
		this.type = type;
		this.duration = duration;
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

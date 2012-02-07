package bg.su.fmi.fitness.assistant.entities;

public class Exersize {

	private long  id;
	private String name;
	private int sets;
	private int repetitions;
	private String description;
	private String video;
	
	public Exersize(long id, String name, int sets, int repetitions,
			String description, String video) {
		super();
		this.id = id;
		this.name = name;
		this.sets = sets;
		this.repetitions = repetitions;
		this.description = description;
		this.video = video;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) {
		this.repetitions = repetitions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
}

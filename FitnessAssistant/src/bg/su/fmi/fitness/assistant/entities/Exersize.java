package bg.su.fmi.fitness.assistant.entities;

import java.io.Serializable;

public class Exersize implements Serializable {

	private static final long serialVersionUID = -6966378985986700616L;
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
		Exersize other = (Exersize) obj;
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
	
	@Override
	public String toString() {
		return getName();
	}
	
}

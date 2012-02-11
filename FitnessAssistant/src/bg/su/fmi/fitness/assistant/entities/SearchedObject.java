package bg.su.fmi.fitness.assistant.entities;

public class SearchedObject {
	
	private String name;
	private long id;
	private String type;
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public SearchedObject(String name, long id, String type) {
		super();
		this.name = name;
		this.id = id;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}

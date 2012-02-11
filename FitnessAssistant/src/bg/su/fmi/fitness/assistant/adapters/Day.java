package bg.su.fmi.fitness.assistant.adapters;

import java.io.Serializable;

import bg.su.fmi.fitness.assistant.entities.Exersize;

public class Day implements Serializable{
	private int number;
	private Exersize[] exercises;
	
	
	public Day(int number)
	{
		this.number = number;
		
		exercises = new Exersize[]{new Exersize(1, "name1", 4, 10, "description", "video"),
				new Exersize(2, "name2", 4, 10, "description", "video"),
				new Exersize(3, "name3", 4, 10, "description", "video"),
				new Exersize(4, "name4", 4, 10, "description", "video")};
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public Exersize[] getExercises()
	{
		return exercises;
	}
	public void setNumber(int number)
	{
		this.number=number;
	}
	public void setExercises(Exersize[] exercises)
	{
		this.exercises = exercises;
	}
}

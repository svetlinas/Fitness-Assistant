package bg.su.fmi.fitness.assistant.adapters;

import java.io.Serializable;
import java.util.List;

import bg.su.fmi.fitness.assistant.entities.Exersize;

public class Day implements Serializable{
	private int number;
	private List<Exersize> exercises;
	
	
	public Day(int number)
	{
		this.number = number;
		
		
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public List<Exersize> getExercises()
	{
		return exercises;
	}
	
	public void setNumber(int number)
	{
		this.number=number;
	}
	public void setExercises(List<Exersize> exercises)
	{
		this.exercises = exercises;
	}
	
	public String toString()
	{
		return "Day " + number;
	}
}

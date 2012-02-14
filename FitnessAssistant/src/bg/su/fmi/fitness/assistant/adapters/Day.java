package bg.su.fmi.fitness.assistant.adapters;

import java.io.Serializable;
import java.util.List;

import bg.su.fmi.fitness.assistant.entities.Exersize;

public class Day implements Serializable{
	private static final long serialVersionUID = 9206226494418505604L;
	
	private int number;
	private List<Exersize> exercises;
	//private Workout parent;
	
	public Day(int number)
	{
		this.number = number;
		//this.parent = parent;
		
	}
	/*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	*/
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (number != other.number)
			return false;
		return true;
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

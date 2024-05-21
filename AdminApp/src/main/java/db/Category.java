package db;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private String name;
	private int id;
	private List<Exercise> exercise = new ArrayList<Exercise>();
	
	
	public List<Exercise> getExercise() {
		return exercise;
	}


	public void setExercise(List<Exercise> exercise) {
		this.exercise = exercise;
	}


	public Category(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Category() {
		// TODO Auto-generated constructor stub
	}

}

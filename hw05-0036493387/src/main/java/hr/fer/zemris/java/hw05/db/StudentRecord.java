package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class models a student record.
 * 
 * @author Mihael Stočko
 *
 */
public class StudentRecord {

	/**
	 * Student's jmbag
	 */
	private String jmbag;
	
	/**
	 * Student's surname
	 */
	private String surname;
	
	/**
	 * Student's name
	 */
	private String name;
	
	/**
	 * Student's final grade
	 */
	private int finalGrade;
	
	/**
	 * Constructor
	 * 
	 * @param jmbag
	 * @param surname
	 * @param name
	 * @param finalGrade
	 * @throws NullPointerException
	 */
	public StudentRecord(String jmbag, String surname, String name, int finalGrade) {
		Objects.requireNonNull(jmbag, "jmbag cannot be null");
		Objects.requireNonNull(surname, "surname cannot be null");
		Objects.requireNonNull(name, "name cannot be null");
		
		this.jmbag = jmbag;
		this.surname = surname;
		this.name = name;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for jmbag
	 * 
	 * @return
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Setter for jmbag
	 * 
	 * @param jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Getter for surname
	 * 
	 * @return
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Setter for surname
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Getter for name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for finalGrade
	 * 
	 * @return
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Setter for finalGrade
	 * 
	 * @param finalGrade
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
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
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}

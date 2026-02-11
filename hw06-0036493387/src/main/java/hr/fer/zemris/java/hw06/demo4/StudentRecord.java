package hr.fer.zemris.java.hw06.demo4;

import java.util.Objects;

/**
 * This class models one student's results.
 * 
 * @author Mihael Stočko
 *
 */
public class StudentRecord {

	/**
	 * jmbag of the student
	 */
	private String jmbag;
	
	/**
	 * Student's last name
	 */
	private String lastName;
	
	/**
	 * Student's first name
	 */
	private String firstName;
	
	/**
	 * Points the student has earned on the first exam
	 */
	private double pointsExam1;
	
	/**
	 * Points the student has earned on the final exam
	 */
	private double pointsExam2;
	
	/**
	 * Points the student has earned on the lab assignments
	 */
	private double pointsLab;
	
	/**
	 * Student's final grade
	 */
	private int finalGrade;
	
	/**
	 * Constructor
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param pointsExam1
	 * @param pointsExam2
	 * @param pointsLab
	 * @param finalGrade
	 * @throws NullPointerException
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double pointsExam1, double pointsExam2,
			double pointsLab, int finalGrade) {
		Objects.requireNonNull(jmbag, "JMBAG cannot be null.");
		Objects.requireNonNull(lastName, "Last name cannot be null.");
		Objects.requireNonNull(firstName, "First name cannot be null.");
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pointsExam1 = pointsExam1;
		this.pointsExam2 = pointsExam2;
		this.pointsLab = pointsLab;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for jmbag
	 * @return
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for lastName
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for firstName
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for pointsExam1
	 * @return
	 */
	public double getPointsExam1() {
		return pointsExam1;
	}

	/**
	 * Getter for pointsExam2
	 * @return
	 */
	public double getPointsExam2() {
		return pointsExam2;
	}

	/**
	 * Getter for pointsLab
	 * @return
	 */
	public double getPointsLab() {
		return pointsLab;
	}

	/**
	 * Getter for finalGrade
	 * @return
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
}

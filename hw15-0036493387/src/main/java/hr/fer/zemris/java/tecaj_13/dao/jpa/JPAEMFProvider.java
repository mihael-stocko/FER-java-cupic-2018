package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class acts as a provider of an {@link EntityManagerFactory}.
 *
 */
public class JPAEMFProvider {

	/**
	 * A single EntityManagerFactory object.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter for the EntityManagerFactory object.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter for the EntityManagerFactory object.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents an intersection of a ray with a sphere.
 * 
 * @author Mihael Stočko
 *
 */
public class RayIntersectionSphere extends RayIntersection {

	/**
	 * Red component of the diffuse coefficient
	 */
	final double kdr = 0.78125;
	
	/**
	 * Green component of the diffuse coefficient
	 */
	final double kdg = 0.78125;
	
	/**
	 * Blue component of the diffuse coefficient
	 */
	final double kdb = 0.78125;
	
	/**
	 * Red component of the specular coefficient
	 */
	final double krr = 0.17578125;
	
	/**
	 * Green component of the specular coefficient
	 */
	final double krg = 0.17578125;
	
	/**
	 * Blue component of the specular coefficient
	 */
	final double krb = 0.17578125;
	
	/**
	 * n for the specular coefficient
	 */
	final double krn = 10;
	
	/**
	 * Center of the sphere
	 */
	Point3D sphereCenter;
	
	/**
	 * Constructor
	 * 
	 * @param point Point of intersection
	 * @param distance Distance from the start of the ray
	 * @param outer Is the intersection outer
	 */
	public RayIntersectionSphere(Point3D point, double distance, boolean outer) {
		super(point, distance, outer);
	}
	
	/**
	 * Setter for sphere center
	 */
	public void setSphereCenter(Point3D sphereCenter) {
		this.sphereCenter = sphereCenter;
	}

	/**
	 * Getter for normal
	 */
	@Override
	public Point3D getNormal() {
		return getPoint().sub(sphereCenter);
	}
	
	/**
	 * Getter for krr
	 */
	@Override
	public double getKrr() {
		return krr;
	}
	
	/**
	 * Getter for krn
	 */
	@Override
	public double getKrn() {
		return krn;
	}
	
	/**
	 * Getter for krg
	 */
	@Override
	public double getKrg() {
		return krg;
	}
	
	/**
	 * Getter for krb
	 */
	@Override
	public double getKrb() {
		return krb;
	}
	
	/**
	 * Getter for kdr
	 */
	@Override
	public double getKdr() {
		return kdr;
	}
	
	/**
	 * Getter for kdg
	 */
	@Override
	public double getKdg() {
		return kdg;
	}
	
	/**
	 * Getter for kdb
	 */
	@Override
	public double getKdb() {
		return kdb;
	}
}

package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere object.
 * 
 * @author Mihael Stočko
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Center of the sphere
	 */
	Point3D center;
	
	/**
	 * Radius of the sphere
	 */
	double radius;
	
	/**
	 * Red component of the diffuse coefficient
	 */
	double kdr;
	
	/**
	 * Green component of the diffuse coefficient
	 */
	double kdg;
	
	/**
	 * Blue component of the diffuse coefficient
	 */
	double kdb;
	
	/**
	 * Red component of the specular coefficient
	 */
	double krr;
	
	/**
	 * Green component of the specular coefficient
	 */
	double krg;
	
	/**
	 * Blue component of the specular coefficient
	 */
	double krb;
	
	/**
	 * n for the specular coefficient
	 */
	double krn;
	
	/**
	 * Getter for center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Constructor.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * For the given ray, finds the closest of intersections with all objects the ray penetrates.
	 * @return The closest intersection
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double distance;
		
		double determinant = Math.pow(ray.direction.scalarProduct(ray.start.sub(center)), 2) - 
				Math.pow(ray.start.sub(center).norm(), 2) + radius*radius;
		
		if(determinant < 0) {
			return null;
		}
		
		if(determinant == 0) {
			distance = -(ray.direction.scalarProduct(ray.start.sub(center)));
		} else {
			double distance1 = -(ray.direction.scalarProduct(ray.start.sub(center))) + Math.sqrt(determinant);
			double distance2 = -(ray.direction.scalarProduct(ray.start.sub(center))) - Math.sqrt(determinant);
			distance = distance1 < distance2 ? distance1 : distance2;
		}
		
		Point3D intersectionPoint = ray.start.add(ray.direction.scalarMultiply(distance));
		RayIntersection intersection = new RayIntersectionSphere(intersectionPoint, distance, true);
		
		return intersection;
	}
}

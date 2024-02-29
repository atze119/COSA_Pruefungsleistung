package de.leuphana.cosa.routesystem.structure;

public class Route {
	
	private Location startLocation;
	private Location endLocation;
	private double distance;
	
	public Route(Location startLocation, Location endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.distance = distanceInKm(startLocation.getxCoordinate(), startLocation.getyCoordiante(), endLocation.getxCoordinate(), endLocation.getyCoordiante());
//		System.out.println(distance);
	}
	
	public Location getStartLocation() {
		return startLocation;
	}
	
	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}
	
	public Location getEndLocation() {
		return endLocation;
	}
	
	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}

	public double getDistance() {
		return distance;
	}

	private double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
	    int radius = 6371; 

	    double lat = Math.toRadians(lat2 - lat1);
	    double lon = Math.toRadians(lon2- lon1);

	    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = radius * c;

	    return Math.abs(d);
	}
	
//	public void setDistance(float distance) {
//		this.distance = distance * 1.45f; // * 1.45f because distance is in air-line distance to convert it to drivable-distance
//	}
//	
	
	

}

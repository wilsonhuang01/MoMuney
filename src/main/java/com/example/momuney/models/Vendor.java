package com.example.momuney.models;

public class Vendor {
	
	/** The name of the Vendor. */
	private String name;
	/** The location of the Vendor. */
	private String location;
	
	/**
	 * This constructor should only be called by DbConnection.
	 */
	public Vendor() {
		super();
	}
	
	/**
	 * Construct a new Vendor with specified name and location.
	 * @param name the name of the Vendor
	 * @param location the location of the Vendor
	 */
	public Vendor(String name, String location) {
		if (name == null) throw new NullPointerException("name is null");
		if (location == null) throw new NullPointerException("location is null");
		
		this.name = name;
		this.location = location;
	}
	
	/**
	 * Get the name of the Vendor.
	 * @return the name of the Vendor
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the location of the Vendor.
	 * @return the location of the Vendor
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Get the String representation of the Vendor.
	 * Ex. Whole Foods -- San Jose
	 * @return the String representation of the Vendor
	 */
	public String toString() {
		return name + " -- " + location;
	}
}

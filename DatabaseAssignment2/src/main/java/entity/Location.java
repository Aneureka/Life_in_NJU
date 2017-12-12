package entity;

/**
 * @author Hiki
 * @create 2017-11-07 21:15
 */
public class Location {

	/**
	 * The location of end point.
	 */
	private String location;
	/**
	 * Frequency of the location.
	 */
	private int frequency;

	public Location(String location, int frequency) {
		this.location = location;
		this.frequency = frequency;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
}

package entity;

/**
 * @author Hiki
 * @create 2017-11-07 14:59
 */
public class Bike {

	/**
	 * Bike's identification.
	 */
	private int bid;

	public Bike(int bid) {
		this.bid = bid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
}

package entity;

/**
 * @author Hiki
 * @create 2017-11-07 14:48
 */
public class User {

	/**
	 * User's unique id.
	 */
	private int uid;
	/**
	 * User's name.
	 */
	private String name;
	/**
	 * User's mobile phone.
	 */
	private String phone;
	/**
	 * User's balance.
	 */
	private double balance;
	/**
	 * User's address
	 */
	private String address;

	public User(int uid, String name, String phone, double balance) {
		this.uid = uid;
		this.name = name;
		this.phone = phone;
		this.balance = balance;
		this.address = "";
	}

	public static User fromStringArray(String[] array){
		int uid = Integer.valueOf(array[0]);
		String name = array[1];
		String phone = array[2];
		double balance = Double.valueOf(array[3]);
		return new User(uid, name, phone, balance);
	}

	@Override
	public String toString(){
		return new StringBuilder("[")
				.append(uid).append(", ")
				.append(name).append(", ")
				.append(phone).append(", ")
				.append(balance).append(", ")
				.append(address).append("]").toString();
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

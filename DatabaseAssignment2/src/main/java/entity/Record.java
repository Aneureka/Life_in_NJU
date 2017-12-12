package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author Hiki
 * @create 2017-11-07 14:50
 */
public class Record {

	/**
	 * User's id.
	 */
	private int uid;
	/**
	 * Bike's id.
	 */
	private int bid;
	/**
	 * Starting position.
	 */
	private String startPoint;
	/**
	 * The time of starting.
	 */
	private LocalDateTime startTime;
	/**
	 * End position.
	 */
	private String endPoint;
	/**
	 * The time of end.
	 */
	private LocalDateTime endTime;
	/**
	 * The cost of this journey.
	 */
	private double cost;

	public Record(int uid, int bid, String startPoint, LocalDateTime startTime, String endPoint, LocalDateTime endTime) {
		this.uid = uid;
		this.bid = bid;
		this.startPoint = startPoint;
		this.startTime = startTime;
		this.endPoint = endPoint;
		this.endTime = endTime;
		this.cost = 0.0;
	}

	public static Record fromStringArray(String[] array){
		// initialize the datetime formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
		int uid = Integer.valueOf(array[0]);
		int bid = Integer.valueOf(array[1]);
		String startPoint = array[2];
		LocalDateTime startTime = LocalDateTime.parse(array[3], formatter);
		String endPoint = array[4];
		LocalDateTime endTime = LocalDateTime.parse(array[5], formatter);
		Record record = new Record(uid, bid, startPoint, startTime, endPoint, endTime);
		record.setCost(record.calculateCost());
		return record;
	}

	public double calculateCost(){
		long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
		if (minutes <= 30) return 1;
		else if (minutes <= 60) return 2;
		else if (minutes <= 90) return 3;
		else return 4;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}

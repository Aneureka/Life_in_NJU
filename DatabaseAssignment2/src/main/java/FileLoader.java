
import entity.Record;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hiki
 * @create 2017-11-06 10:34
 */
public class FileLoader {

	private final static String FILE_LOCATION_OF_BIKE = "data/bike.txt";
	private final static String FILE_LOCATION_OF_RECORD = "data/record.txt";
	private final static String FILE_LOCATION_OF_USER = "data/user.txt";

	public static Map<Integer, User> loadUserInMap(){
		String fileName = FILE_LOCATION_OF_USER;
		BufferedReader br = getBufferedReader(fileName);
		Map<Integer, User> res = new HashMap<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				String[] tmp = line.split(";");
				int uid = Integer.valueOf(tmp[0]);
				User user = User.fromStringArray(tmp);
//				System.out.println(user);
				res.put(uid, user);
			}
			br.close();
		} catch (IOException e) {
			System.err.println("error while reading " + fileName + " because of: " + e);
		}
		return res;
	}

	public static List<Record> loadRecord(Map<Integer, User> userMap){
		String fileName = FILE_LOCATION_OF_RECORD;
		BufferedReader br = getBufferedReader(fileName);
		List<Record> res = new ArrayList<>();
		List<Integer> discardUids = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				String[] tmp = line.split(";");
				int uid = Integer.valueOf(tmp[0]);
				if (discardUids.contains(uid)){
//					System.out.println("---" + uid);
					continue;
				}
				Record record = Record.fromStringArray(tmp);
				res.add(record);
				// update the user balance
				User currentUser = userMap.get(record.getUid());
				currentUser.setBalance(currentUser.getBalance()-record.getCost());
				if (currentUser.getBalance() <= 0)
					discardUids.add(uid);
			}
			br.close();
		} catch (IOException e) {
			System.err.println("error while reading " + fileName + " because of: " + e);
		}
		return res;
	}

	public static List<Integer> loadBikeId(){
		String fileName = FILE_LOCATION_OF_BIKE;
		BufferedReader br = getBufferedReader(fileName);
		List<Integer> res = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				String[] tmp = line.split(";");
				res.add(Integer.valueOf(tmp[0]));
			}
			br.close();
		} catch (IOException e) {
			System.err.println("error while reading " + fileName + " because of: " + e);
		}
		return res;
	}

	private static BufferedReader getBufferedReader(String fileName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream txtFile =  classLoader.getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(txtFile));
	}

	public static void main(String[] args) {
		Map<Integer, User> userMap = loadUserInMap();
		loadRecord(userMap);
		loadBikeId();
	}



}

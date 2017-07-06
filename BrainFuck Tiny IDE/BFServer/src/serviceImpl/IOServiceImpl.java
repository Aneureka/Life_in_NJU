package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import service.IOService;

public class IOServiceImpl implements IOService {

	@Override
	public boolean writeFile(String file, String userId, String fileName, String time) {

		// TODO 若没有改变file内容，则不保存新版本，需要读里面的文件
		boolean successfulWrite = false;
		
		try {

			// 创建文件保存路径
			String path = userId + "/" + fileName;
			File dir = new File(path);
			// 若需要，创建文件夹
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			else{
			// 读取每个版本文件并与新的比较
			if(this.readVersionList(userId, fileName) != null){
			for (String version : this.readVersionList(userId, fileName)) {

				String versionPath = userId + "/" + fileName + "/" + version;
				File alreadyExistedVersion = new File(versionPath);

				FileReader fr = new FileReader(alreadyExistedVersion);
				BufferedReader br = new BufferedReader(fr);
				String ExistedVersionFile = "";
				// String line = null;

				// 读取已有版本的文件
				ExistedVersionFile = br.readLine() + "\n" + br.readLine();

				br.close();

				if (ExistedVersionFile.equals(file)) {
					successfulWrite = false;
					System.out.println("Version already existed!");
					return successfulWrite;
				}
			}
			}
			}
			// 创建文件新版本
			File f = new File(path + "/" + time);
			if (!f.exists()) {
				f.createNewFile();
			}

			// 写入代码
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			
			// 检验是否“已经”写入
			if(f.exists()){
				successfulWrite = true;
				return successfulWrite;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;

	}

	// 设置默认读取最新版本
	@Override
	public List<String> readFile(String userId, String fileName) {

		// 获取最后一个版本名
		String newestVersionName = readVersionList(userId, fileName).get(readVersionList(userId, fileName).size() - 1);

		System.out.println(newestVersionName + "  newestVersionNam");
		// 获取最后一个版本文件的路径
		String Path = userId + "/" + fileName + "/" + newestVersionName;
		File newestVersionFile = new File(Path);

		List<String> readFile = new ArrayList<>();

		try {
			FileReader fr = new FileReader(newestVersionFile);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine()) != null) {
				readFile.add(line);
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return readFile;
	}

	@Override
	public List<String> readFileVersion(String userId, String fileName, String versionName) {

		// 获取所要版本文件的路径
		String Path = userId + "/" + fileName + "/" + versionName;
		File versionFile = new File(Path);
		System.out.println(versionName);

		List<String> readFileVersion = new ArrayList<>();

		try {
			FileReader fr = new FileReader(versionFile);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine()) != null) {
				readFileVersion.add(line);
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return readFileVersion;
	}

	@Override
	public List<String> readFileList(String userId) {

		// 定位到某个用户文件夹里
		File user = new File(userId);
		File[] fileList = user.listFiles();
		List<String> fileNameList = new ArrayList<>();

		// 获取所有文件名
		for (int i = 0; i < fileList.length; i++) {
			fileNameList.add(fileList[i].getName());
		}

		return fileNameList;
	}

	@Override
	public List<String> readVersionList(String userId, String fileName) {

		// 定位到某个文件里
		File userFile = new File(userId + "/" + fileName);
		File[] versionList = userFile.listFiles();
		List<String> versionNameList = new ArrayList<>();
		System.out.println("here");

		// 获得所有文件版本名
		if (versionList != null) {
			for (int i = 0; i < versionList.length; i++) {
				versionNameList.add(versionList[i].getName());
				System.out.println("读到" + versionList[i].getName());
			}
			return versionNameList;
		}

		else {
			System.out.println("No VERSION!");
			return null;
		}

		
		
	}

}

package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.opencsv.CSVReader;

public class DataProviderUtility {

	 @DataProvider(name = "projectKeyProvider")
	public static Object[][] projectKeyProvider() {
		Object[][] obj;
		List<String> list = new ArrayList<>();

		try {
			String projectPath = System.getProperty("user.dir");
			CSVReader reader = new CSVReader(
					new FileReader(new File(projectPath + "/src/test/resources/ProjectKeys.csv")));
			Iterator<String[]> it = reader.iterator();
			String[] str;
			while ((str = it.next()) != null) {
				list.addAll(Arrays.asList(str));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		obj = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			obj[i][0] = list.get(i);
		}
		return obj;
	}

}

package com.ibm.reportsgenerator;

import com.ibm.reportsgenerator.dto.ReportForm;
import lombok.extern.log4j.Log4j2;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Log4j2
@RunWith(SpringRunner.class)
class ReportsGenetarorApplicationTests {


	@Test
	void contextLoads() {
	}

	@Test
	public void fileRead() throws IOException, ParseException {

		List<ReportForm> reportForms = new ArrayList<>();

		String filepath = "C:\\Users\\ChristianSalvadorRob\\Documents\\Workspace\\destination";

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-1);
		SimpleDateFormat fileDateFomat = new SimpleDateFormat("MM-dd-yyyy");

		String filename = filepath + "\\reports_" + fileDateFomat.format(calendar.getTime())+ ".txt";

		File fileDirectory = new File(filepath);
		if(!fileDirectory.exists()){
			fileDirectory.mkdir();
			log.info("|DIRECTORY CREATED| - {}",fileDirectory);
		}

		File file = new File(filename);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;

		while((line = bufferedReader.readLine()) != null){
			ReportForm reportForm = new ReportForm();
			reportForm.parse(line);
			reportForms.add(reportForm);
		}

		log.info("|REPORT FORMS| - {}",reportForms);
	}

}

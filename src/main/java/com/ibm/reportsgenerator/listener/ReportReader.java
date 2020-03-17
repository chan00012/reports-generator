package com.ibm.reportsgenerator.listener;

import com.ibm.reportsgenerator.dto.ReportForm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class ReportReader {

    @Value("${report.filepath}")
    private String filepath;

    public List<ReportForm> readReports() throws IOException, ParseException {
        List<ReportForm> reportForms = new ArrayList<>();


        SimpleDateFormat fileDateFomat = new SimpleDateFormat("MM-dd-yyyy");

        String filename = filepath + "\\reports_" + fileDateFomat.format(new Date()) + ".txt";

        File fileDirectory = new File(filepath);
        if(!fileDirectory.exists()){
            fileDirectory.mkdir();
            log.info("|DIRECTORY CREATED| - {}",fileDirectory);
        }

        File file = new File(filename);

        if(file.exists()) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                ReportForm reportForm = new ReportForm();
                reportForm.parse(line);
                reportForms.add(reportForm);
            }
            bufferedReader.close();
            fileReader.close();;
            file.renameTo(new File(filename + "_done"));
        }
        return  reportForms;
    }
}

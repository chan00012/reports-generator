package com.ibm.reportsgenerator.service;

import com.ibm.reportsgenerator.constants.BookType;
import com.ibm.reportsgenerator.dto.ReportForm;
import com.ibm.reportsgenerator.dto.SummarizedForm;
import com.ibm.reportsgenerator.listener.ReportReader;
import com.ibm.reportsgenerator.model.ExpenseData;
import com.ibm.reportsgenerator.model.UserData;
import com.ibm.reportsgenerator.repository.ExpenseDataRepository;
import com.ibm.reportsgenerator.repository.UserDataRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@EnableScheduling
public class ReportsGeneratorService {

    @Autowired
    private ReportReader reportReader;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private ExpenseDataRepository expenseDataRepository;

    //EVERY 1 A.M.
    //@Scheduled(cron = "0 1 0 * * ?")
    @Scheduled(cron = "10 0/1 * * * ?")
    public void insertToDatabase() throws IOException, ParseException {

        List<ReportForm> reportForms = reportReader.readReports();
        reportForms.forEach(reportForm -> {
            UserData userData = new UserData(reportForm.getUserId(), reportForm.getName(), reportForm.getEmail());
            ExpenseData expenseData = new ExpenseData(reportForm.getBookType(), reportForm.getAmount(), reportForm.getDateRecorded(), userData);

            if (!userDataRepository.existsById(userData.getUserId())) {
                userDataRepository.save(userData);
            }

            expenseDataRepository.save(expenseData);

        });

    }

    //EVERY 2 A.M.
    //@Scheduled(cron = "0 2 0 * * ?")
    @Scheduled(cron = "20 0/1 * * * ?")
    public void generateDailyReports() {

        List<UserData> userDataList = userDataRepository.findAll();

        Calendar calendar = Calendar.getInstance();

        userDataList.forEach(userData -> {

            List<ExpenseData> creditsExpenses = expenseDataRepository.findByDateRecorded(calendar.getTime())
                                                                                                            .stream()
                                                                                                            .filter(ed -> ed.getBookType().equals(BookType.CREDIT))
                                                                                                            .collect(Collectors.toList());

            List<ExpenseData> debitsExpenses = expenseDataRepository.findByDateRecorded(calendar.getTime())
                                                                                                            .stream()
                                                                                                            .filter(ed -> ed.getBookType().equals(BookType.DEBIT))
                                                                                                            .collect(Collectors.toList());


            BigDecimal creditsTotal = creditsExpenses.stream().map(ExpenseData::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal debitsTotal = debitsExpenses.stream().map(ExpenseData::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            MathContext mc = new MathContext(5);

            BigDecimal netAmount = creditsTotal.subtract(debitsTotal,mc);

            SummarizedForm summarizedForm = SummarizedForm.builder()
                                                                    .name(userData.getName())
                                                                    .email(userData.getEmail())
                                                                    .totalCredit(creditsTotal)
                                                                    .totalDebit(debitsTotal)
                                                                    .netAmount(netAmount)
                                                                    .build();

            log.info("|DAILY REPORT AS OF {} | - {}",new SimpleDateFormat("MM-dd-yyyy").format(calendar.getTime()),summarizedForm);
        });
    }
}

package com.ibm.reportsgenerator;

import com.ibm.reportsgenerator.constants.BookType;
import com.ibm.reportsgenerator.model.ExpenseData;
import com.ibm.reportsgenerator.model.UserData;
import com.ibm.reportsgenerator.repository.ExpenseDataRepository;
import com.ibm.reportsgenerator.repository.UserDataRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@Log4j2
@RunWith(SpringRunner.class)
public class GenerateReportsTest {

    @Mock
    UserDataRepository userDataRepository;

    @Mock
    ExpenseDataRepository expenseDataRepository;

    @Before
    public void setUp(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE,2);

        UserData userData = new UserData();
        userData.setUserId(1L);
        userData.setName("Christian Robles");
        userData.setEmail("cstrobles@gmail.com");

        ExpenseData expenseData1 = new ExpenseData();
        expenseData1.setExpsenseId(1L);
        expenseData1.setBookType(BookType.CREDIT);
        expenseData1.setAmount(new BigDecimal("1000"));
        expenseData1.setDateRecorded(calendar.getTime());

        ExpenseData expenseData2 = new ExpenseData();
        expenseData2.setExpsenseId(2L);
        expenseData2.setBookType(BookType.CREDIT);
        expenseData2.setAmount(new BigDecimal("1000"));
        expenseData2.setDateRecorded(calendar.getTime());

        ExpenseData expenseData3 = new ExpenseData();
        expenseData3.setExpsenseId(3L);
        expenseData3.setBookType(BookType.DEBIT);
        expenseData3.setAmount(new BigDecimal("500"));
        expenseData3.setDateRecorded(calendar.getTime());

        ExpenseData expenseData4 = new ExpenseData();
        expenseData4.setExpsenseId(4L);
        expenseData4.setBookType(BookType.DEBIT);
        expenseData4.setAmount(new BigDecimal("500"));
        expenseData4.setDateRecorded(calendar2.getTime());

        List<ExpenseData> expenseData = new ArrayList<>();

        expenseData.add(expenseData1);
        expenseData.add(expenseData2);
        expenseData.add(expenseData3);
        expenseData.add(expenseData4);
        userData.setExpenseDataList(expenseData);

        Mockito.when(userDataRepository.findAll()).thenReturn(Collections.singletonList(userData));
        Mockito.when(expenseDataRepository.findByDateRecorded(any(Date.class))).thenReturn(expenseData);
    }


    @Test
    public void compareDateRange(){
        Calendar calendar = Calendar.getInstance();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE,-2);

        Calendar inputDate =Calendar.getInstance();
        inputDate.add(Calendar.DATE,-1);


    }

    @Test
    public void generateDailyReports(){
        List<UserData> userDataList = userDataRepository.findAll();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);

        log.info("|DATE TODAY| - {}",calendar.getTime());

        userDataList.forEach(userData -> {

            List<ExpenseData> creditsExpenses = expenseDataRepository.findByDateRecorded(calendar.getTime())
                                                                                                            .stream()
                                                                                                            .filter(ed -> ed.getBookType().equals(BookType.CREDIT))
                                                                                                            .collect(Collectors.toList());

            List<ExpenseData> debitsExpenses = expenseDataRepository.findByDateRecorded(calendar.getTime())
                                                                                                            .stream()
                                                                                                            .filter(ed -> ed.getBookType().equals(BookType.DEBIT))
                                                                                                            .collect(Collectors.toList());


            BigDecimal creditsTotal = creditsExpenses.stream().map(ExpenseData::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal debitsTotal = debitsExpenses.stream().map(ExpenseData::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);

            log.info("|CREDIT TOTAL| - {}",creditsTotal);
            log.info("|DEBIT TOTAL| - {}",debitsTotal);
    });
}

}

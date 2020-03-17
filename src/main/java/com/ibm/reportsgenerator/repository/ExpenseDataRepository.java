package com.ibm.reportsgenerator.repository;

import com.ibm.reportsgenerator.model.ExpenseData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExpenseDataRepository extends JpaRepository<ExpenseData,Long> {

    List<ExpenseData> findByDateRecorded(Date dateRecorded);
    List<ExpenseData> findByDateRecordedBetween(Date startDate, Date endDate);
}

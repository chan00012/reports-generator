package com.ibm.reportsgenerator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.reportsgenerator.constants.BookType;
import com.ibm.reportsgenerator.utils.Rest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


@Log4j2
@Data
@Entity
@NoArgsConstructor
@Table(name = "REPORTS_EXPENSE")
public class ExpenseData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long expsenseId;

    private BookType bookType;

    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateRecorded;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData userData;

    public ExpenseData(BookType bookType, BigDecimal amount, Date dateRecorded, UserData userData)  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateRecorded);
        cal.add(Calendar.DATE,1);

        this.bookType = bookType;
        this.amount = amount;
        this.dateRecorded = cal.getTime();
        this.userData = userData;
    }

    @Override
    public String toString(){
        return Rest.toJsonString(this);
    }


}

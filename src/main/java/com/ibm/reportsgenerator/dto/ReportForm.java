package com.ibm.reportsgenerator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibm.reportsgenerator.constants.BookType;
import com.ibm.reportsgenerator.utils.Rest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Data
@NoArgsConstructor
public class ReportForm {

    private Long userId;
    private String name;
    private String email;
    private BookType bookType;
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date dateRecorded;

    @Override
    public String toString(){
        return Rest.toJsonString(this);
    }

    public void parse(String formatString) throws ParseException {
        String[] parsedForm = formatString.split("\\|");
        this.userId = Long.parseLong(parsedForm[0]);
        this.name = parsedForm[1];
        this.email = parsedForm[2];
        this.bookType = BookType.valueOf(parsedForm[3]);
        this.amount = new BigDecimal(parsedForm[4]);
        this.dateRecorded = new SimpleDateFormat("MM-dd-yyyy").parse(parsedForm[5]);
    }


}

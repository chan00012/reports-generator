package com.ibm.reportsgenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.reportsgenerator.utils.Rest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "REPORTS_USER")
public class UserData {

    @Id
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpenseData> expenseDataList;

    public UserData(Long userId, String name, String email){
        this.userId = userId;
        this.name = name;
        this.email = email;

    }

    @Override
    public String toString(){
        return Rest.toJsonString(this);
    }
}

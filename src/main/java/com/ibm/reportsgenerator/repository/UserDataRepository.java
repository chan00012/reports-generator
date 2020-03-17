package com.ibm.reportsgenerator.repository;

import com.ibm.reportsgenerator.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

}

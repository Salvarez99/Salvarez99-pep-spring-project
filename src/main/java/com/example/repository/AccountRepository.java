package com.example.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.username = :usernameVar")
    Optional<Account> findByUsername(@Param("usernameVar") String username);
    
    @Query("SELECT a FROM Account a WHERE a.username = :usernameVar AND a.password = :passwordVar")
    Optional<Account> findByUsernameAndPassword(@Param("usernameVar") String username, @Param("passwordVar") String password);

}

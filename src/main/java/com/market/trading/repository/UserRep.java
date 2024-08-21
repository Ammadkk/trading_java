package com.market.trading.repository;

import com.market.trading.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<User, Long> {
    User findByEmail(String email);

}

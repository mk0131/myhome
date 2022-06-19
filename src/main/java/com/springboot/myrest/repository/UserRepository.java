package com.springboot.myrest.repository;

import com.springboot.myrest.model.Board;
import com.springboot.myrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}

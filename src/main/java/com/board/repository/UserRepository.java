package com.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.board.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByNickname(String nickname);

    UserEntity findByNickname(String nickname);
}

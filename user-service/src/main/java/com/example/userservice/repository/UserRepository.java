package com.example.userservice.repository;

import com.example.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.userservice.util.ValidationUtil.checkModification;


@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(int id);

    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }

}

package org.example.serverapp.repository;

import org.example.serverapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryDB extends JpaRepository<User, Integer> {
}

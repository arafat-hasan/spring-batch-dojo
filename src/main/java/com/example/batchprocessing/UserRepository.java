package com.example.batchprocessing;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastName(String lastName);

	User findById(long id);
}

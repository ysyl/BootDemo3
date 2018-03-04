package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByNameAndAge(String name, Integer age);
    
    User findById(int Id);

    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
    
    User findByUsername(String username);
     
    User save(org.springframework.security.core.userdetails.User user);

	User findByArticles_id(Long id); 
	
	Long countByLikedArticles(Article article);
}

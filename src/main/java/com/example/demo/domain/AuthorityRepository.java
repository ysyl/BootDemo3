package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority save(Authority auth);
	Authority findByName(String name);
}

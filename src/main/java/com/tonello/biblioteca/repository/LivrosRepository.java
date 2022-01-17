package com.tonello.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonello.biblioteca.model.Livros;

@Repository
public interface LivrosRepository extends JpaRepository<Livros, Long> {
	public Optional<Livros> findById(Long id);
}

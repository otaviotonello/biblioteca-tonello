package com.tonello.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tonello.biblioteca.model.Livros;
import com.tonello.biblioteca.repository.LivrosRepository;

@RestController
@RequestMapping("/tb_livros")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class LivrosController {

	@Autowired
	private LivrosRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Livros>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Livros> getById(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PostMapping
	public ResponseEntity<Livros> post(@Valid @RequestBody Livros livro){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(livro));
	}

	@PutMapping
	public ResponseEntity<Livros> put(@Valid @RequestBody Livros livro){
		return repository.findById(livro.getId())
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(livro)))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<Livros> post = repository.findById(id);

		if(post.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		repository.deleteById(id);
	}
}

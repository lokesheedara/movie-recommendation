package com.movierecommendation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movierecommendation.dto.MovieDTO;
import com.movierecommendation.service.MovieService;



@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id){
        MovieDTO movieDTO =movieService.getMovieById(id);
        return (movieDTO!=null)? ResponseEntity.ok(movieDTO):ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MovieDTO> AddMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO savedMovie=movieService.addMovie(movieDTO);
        return ResponseEntity.ok(savedMovie);
    }

     @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO updatedDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(id, updatedDTO);
        return (updatedMovie != null) ? ResponseEntity.ok(updatedMovie) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    
}

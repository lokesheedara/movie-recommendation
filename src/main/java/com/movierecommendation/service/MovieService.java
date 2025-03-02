package com.movierecommendation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movierecommendation.dto.MovieDTO;
import com.movierecommendation.models.Movie;
import com.movierecommendation.repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    private MovieDTO convertToDTO(Movie movie) {
        return new MovieDTO(movie.getId(),movie.getTitle(), movie.getGenre(), movie.getReleaseYear(), movie.getRating(),
                movie.getDescription());
    }

    private Movie convertToEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setRating(dto.getRating());
        movie.setDescription(dto.getDescription());
        return movie;

    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList()); // ;
    }

    public MovieDTO getMovieById(Long id) {
        return movieRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie savedMovie = movieRepository.save(convertToEntity(movieDTO));
        return convertToDTO(savedMovie);

    }

    public MovieDTO updateMovie(Long id, MovieDTO updatedDTO) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(updatedDTO.getTitle());
            movie.setGenre(updatedDTO.getGenre());
            movie.setReleaseYear(updatedDTO.getReleaseYear());
            movie.setRating(updatedDTO.getRating());
            movie.setDescription(updatedDTO.getDescription());
            Movie updatedMovie = movieRepository.save(movie);
            return convertToDTO(updatedMovie);
        }).orElse(null);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}

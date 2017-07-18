package com.kaishengit.mapper;

import com.kaishengit.entity.Movie;

import java.util.List;
import java.util.Map;

/**
 * Created by zjs on 2017/7/14.
 */
public interface MovieMapper {

    List<Movie> findAll();

    void save(Movie movie);

    void delMovieById(Integer id);

    Movie findById(Integer id);

    void editMovie(Movie movie);

    List<Movie> findByParam(Map<String, Object> searchParam);
}

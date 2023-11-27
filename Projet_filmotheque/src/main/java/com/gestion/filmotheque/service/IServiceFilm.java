package com.gestion.filmotheque.service;

import java.util.List;

import com.gestion.filmotheque.entities.Film;

public interface IServiceFilm {
public Film createFilm(Film f);
public Film findFilmById(int id);
public List<Film> findAllFilms();
public Film updateFilm(Film f);
public void deleteFilm(int id);
List<Film> findByAnneeparution(int annee); 
List<Film> findByCategorieId(int id);
}

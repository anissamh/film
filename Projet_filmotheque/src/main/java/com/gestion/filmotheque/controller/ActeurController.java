package com.gestion.filmotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.service.IServiceActeur;
import com.gestion.filmotheque.service.IServiceCategorie;
import com.gestion.filmotheque.service.IServiceFilm;


@Controller
@RequestMapping("/acteur/")
public class ActeurController {
	
	@Autowired
	IServiceActeur iServiceActeur;
	
	@Autowired
	IServiceFilm iServiceFilm;
	
	@Autowired
	IServiceCategorie iServiceCategorie;
	
	@GetMapping("all")
	public String listeActeurs(Model model) {
		model.addAttribute("acteurs",iServiceActeur.findAllActeurs());
		return "afficher";
	}
	
	@GetMapping("new")
	public String afficherNewForm(Model model) {
	return "ajouter";
	}
	
	@PostMapping("add")
	public String add(Acteur a) {
		iServiceActeur.createActeur(a);
		return"redirect:/acteur/all";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id)
	{
		iServiceActeur.deleteActeur(id);
		return "redirect:/acteur/all"; 
	}
	

}

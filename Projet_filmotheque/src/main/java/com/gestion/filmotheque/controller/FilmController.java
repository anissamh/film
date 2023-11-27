package com.gestion.filmotheque.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.service.IServiceActeur;
import com.gestion.filmotheque.service.IServiceCategorie;
import com.gestion.filmotheque.service.IServiceFilm;

@Controller
@RequestMapping("/film/")
public class FilmController {

	@Autowired
	IServiceFilm iServiceFilm;
	
	@Autowired
	IServiceActeur iServiceActeur;
	@Autowired
	IServiceCategorie iServiceCategorie;
	
	
	private String uploadDirectory =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\photos"; 
			
	
	@GetMapping("all")
	public String listeFilms(Model model) {
		model.addAttribute("films",iServiceFilm.findAllFilms());
		model.addAttribute("categories",iServiceCategorie.findAllCategories()); 
		return "affiche";
	}
	
	@GetMapping("categorie/")
	 public String listeFilmsCategorie(Model model,@Param("categorie") int categorie) {
		
		if(categorie!=0) { model.addAttribute("films",iServiceFilm.findByCategorieId(categorie));
		 model.addAttribute("categories",iServiceCategorie.findAllCategories());
		 model.addAttribute("selectedCat",categorie);
		 return "affiche";}
		else {
			return "redirect:/film/all"; 
		}
			
			
		
		 }
	
	@GetMapping("new")
	public String afficherNewForm(Model model) {
		model.addAttribute("categories",iServiceCategorie.findAllCategories());
		model.addAttribute("acteurs",iServiceActeur.findAllActeurs());
		LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        model.addAttribute("currentYear", currentYear);
	return "ajout";
	}

	@PostMapping("add")
	public String add(Film f,Model model,@RequestParam("file") MultipartFile multipartFile) {
		
		
		try{ 
			String fileName=multipartFile.getOriginalFilename(); 
			Path fileNameAndPath=Paths.get(uploadDirectory, fileName);
			
	        try {
	        	Files.write(fileNameAndPath,multipartFile.getBytes()); 
	        }catch(IOException e) {
	        }
			
			f.setPhoto(fileName);
			iServiceFilm.createFilm(f);
			return "redirect:/film/all";
		
		} catch(DataIntegrityViolationException e){
			
			model.addAttribute("film", f);
			model.addAttribute("err", "true");
			model.addAttribute("categories",iServiceCategorie.findAllCategories());
			model.addAttribute("acteurs",iServiceActeur.findAllActeurs());
			return  "ajout";
			
			}
	}
	
	
	
	@GetMapping("details/{id}")
    public String afficheDetails(Model model,@PathVariable int id){
        model.addAttribute("categories",iServiceCategorie.findAllCategories() );
        model.addAttribute("acteurs",iServiceActeur.findAllActeurs() );
        model.addAttribute("film",iServiceFilm.findFilmById(id) );
        return "detail";
    }
	
	@DeleteMapping("delete/{id}")
	public String delete(@PathVariable int id)
	{
		iServiceFilm.deleteFilm(id);
		return "redirect:/film/all"; 
	}
	
	 @GetMapping("/update/{id}")
	    public String editeFilm(Model m,@PathVariable("id") int id) {
	        m.addAttribute("film",iServiceFilm.findFilmById(id));
	        m.addAttribute("acteurs",iServiceActeur.findAllActeurs());
	        m.addAttribute("categories", iServiceCategorie.findAllCategories());
	        
	        return "edite";
	    }
	    @PostMapping("/update")
	    public String update(Film f) {
	        iServiceFilm.updateFilm(f);
	        return "redirect:/film/all";
	    }
	    
	    
	    
	    
	    @GetMapping("/cherche")
	    public String chercheFilmByAnnee(@Param("annee")@RequestParam(required = false) Integer  annee, Model m) {
	        
	    	if (annee == null){return "redirect:/film/all";}
	    	else {
	    		System.out.println(annee);
	        List<Film> liste = iServiceFilm.findByAnneeparution(annee);
	        m.addAttribute("films", liste);
	        m.addAttribute("annee", annee);
	        return "affiche";
	        }
	    	
	    }
	    
	 
	
}


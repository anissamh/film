package com.gestion.filmotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.repository.ActeurRepository;

@Service
public class ServiceActeur  implements IServiceActeur {
	
	
	@Autowired
	ActeurRepository acteurRepository;

	@Override
	public Acteur createActeur(Acteur a) {
		// TODO Auto-generated method stub
		return acteurRepository.save(a);
	}

	@Override
	public Acteur findActeurById(int id) {
		// TODO Auto-generated method stub
		return acteurRepository.findById(id).get();
		 
	}

	@Override
	public List<Acteur> findAllActeurs() {
		// TODO Auto-generated method stub
		return acteurRepository.findAll();
		 
	}

	@Override
	public Acteur updateActeur(Acteur a) {
		// TODO Auto-generated method stub
		return acteurRepository.save(a);
		 
	}

	@Override
	public void deleteActeur(int id) {
		// TODO Auto-generated method stub
		acteurRepository.deleteById(id);
		
	}

	
}

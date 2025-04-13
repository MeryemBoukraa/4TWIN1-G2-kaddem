package com.example.contrat.services;

import com.example.contrat.entities.Contrat;
import com.example.contrat.repositories.ContratRepository;
import com.example.contrat.services.IContratService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService {
@Autowired
ContratRepository contratRepository;

	public List<Contrat> retrieveAllContrats(){
		return (List<Contrat>) contratRepository.findAll();
	}

	public Contrat updateContrat (Contrat  ce){
		return contratRepository.save(ce);
	}

	public  Contrat addContrat (Contrat ce){
		return contratRepository.save(ce);
	}

	public Contrat retrieveContrat (Integer  idContrat){
		return contratRepository.findById(idContrat).orElse(null);
	}


		public void removeContrat(Integer idContrat){
			contratRepository.deleteById(idContrat);
			log.info("Tentative de suppression du contrat avec ID: " + idContrat);

		}

	private boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) return false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
				cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
				cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}








}

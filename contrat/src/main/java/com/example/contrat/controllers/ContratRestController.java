package com.example.contrat.controllers;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import com.example.contrat.services.HistoriqueModificationService;
import com.example.contrat.services.IContratService;
import lombok.AllArgsConstructor;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Contrat")
public class ContratRestController {
	IContratService contratService;
	private final HistoriqueModificationService historiqueModificationService;
	// http://localhost:8044/Kaddem/contrat/retrieve-all-contrats

	@GetMapping("/retrieve-all-contrats")
	public List<Contrat> getContrats() {
		List<Contrat> listContrats = contratService.retrieveAllContrats();
		return listContrats;
	}
	// http://localhost:8089/Kaddem/contrat/retrieve-contrat/8
	/*@GetMapping("/retrieve-contrat/{contrat-id}")
	public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		return contratService.retrieveContrat(contratId);
	}
*/

	@GetMapping("/retrieve-contrat/{contrat-id}")
	public ResponseEntity<Contrat> retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		Contrat contrat = contratService.retrieveContrat(contratId);

		// Fetch historique modifications for the contract
		List<HistoriqueModification> historiques = historiqueModificationService.getHistoriqueByContrat(contratId);
		contrat.setHistoriques(historiques);  // Adding historique modifications to contract

		return ResponseEntity.ok(contrat);
	}



	// http://localhost:8089/Kaddem/econtrat/add-contrat
	@PostMapping("/add-contrat")
	public ResponseEntity<Contrat> addContrat(@RequestBody Contrat c) {
		Contrat contrat = contratService.addContrat(c);
		return ResponseEntity.ok().body(contrat);  // Retourne un ResponseEntity
	}

	// http://localhost:8089/Kaddem/contrat/remove-contrat/1
	@DeleteMapping("/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}

	// http://localhost:8089/Kaddem/contrat/update-contrat


	@PutMapping("/update-contrat")
	public Contrat updateContrat(@RequestBody Contrat c) {
		Contrat oldContrat = contratService.retrieveContrat(c.getIdContrat());

		if (oldContrat == null) {
			throw new RuntimeException("Contrat non trouvé");
		}

		if (!oldContrat.getSpecialite().equals(c.getSpecialite())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification de spécialité",
					"Spécialité modifiée de " + oldContrat.getSpecialite() + " à " + c.getSpecialite()
			);
			oldContrat.setSpecialite(c.getSpecialite());
		}

		if (oldContrat.getMontantContrat() != null && !oldContrat.getMontantContrat().equals(c.getMontantContrat())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification du montant",
					"Montant modifié de " + oldContrat.getMontantContrat() + " à " + c.getMontantContrat()
			);
			oldContrat.setMontantContrat(c.getMontantContrat());
		}

		if (!oldContrat.getArchive().equals(c.getArchive())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification d'archive",
					"Archive modifiée de " + oldContrat.getArchive() + " à " + c.getArchive()
			);
			oldContrat.setArchive(c.getArchive());
		}

		if (!oldContrat.getNom().equals(c.getNom())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification du nom",
					"Nom modifié de " + oldContrat.getNom() + " à " + c.getNom()
			);
			oldContrat.setNom(c.getNom());
		}

		// Il faut mettre à jour aussi les dates si elles changent
		if (c.getDateDebutContrat() != null && !DateUtils.isSameDay(c.getDateDebutContrat(), oldContrat.getDateDebutContrat())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification de la date de début",
					"Date de début modifiée de " + oldContrat.getDateDebutContrat() + " à " + c.getDateDebutContrat()
			);
			oldContrat.setDateDebutContrat(c.getDateDebutContrat());
		}

		if (c.getDateFinContrat() != null && !DateUtils.isSameDay(c.getDateFinContrat(), oldContrat.getDateFinContrat())) {
			historiqueModificationService.ajouterHistorique(
					oldContrat,
					"Modification de la date de fin",
					"Date de fin modifiée de " + oldContrat.getDateFinContrat() + " à " + c.getDateFinContrat()
			);
			oldContrat.setDateFinContrat(c.getDateFinContrat());
		}


		return contratService.updateContrat(oldContrat);
	}


	@GetMapping("/retrieve-historique/{contrat-id}")
	public ResponseEntity<List<HistoriqueModification>> getHistoriqueByContrat(@PathVariable("contrat-id") Integer contratId) {
		List<HistoriqueModification> historiques = historiqueModificationService.getHistoriqueByContrat(contratId);
		return ResponseEntity.ok(historiques);
	}

}



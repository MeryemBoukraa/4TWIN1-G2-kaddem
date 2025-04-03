package com.example.contrat.controllers;

import com.example.contrat.entities.Contrat;
import com.example.contrat.entities.HistoriqueModification;
import com.example.contrat.services.HistoriqueModificationService;
import com.example.contrat.services.IContratService;
import lombok.AllArgsConstructor;

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
	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		contratService.removeContrat(contratId);
	}

	// http://localhost:8089/Kaddem/contrat/update-contrat


	@PutMapping("/update-contrat")
	public Contrat updateContrat(@RequestBody Contrat c) {
		Contrat contrat = contratService.retrieveContrat(c.getIdContrat()); // Récupérer l'ancien contrat

		// Vérification des modifications sur les attributs
		if (contrat.getSpecialite() != c.getSpecialite()) {
			historiqueModificationService.ajouterHistorique(
					contrat,
					"Modification de spécialité",
					"Spécialité modifiée de " + contrat.getSpecialite() + " à " + c.getSpecialite()
			);
		}

		if (contrat.getMontantContrat() != c.getMontantContrat()) {
			historiqueModificationService.ajouterHistorique(
					contrat,
					"Modification du montant",
					"Montant modifié de " + contrat.getMontantContrat() + " à " + c.getMontantContrat()
			);
		}

		if (contrat.getArchive() != c.getArchive()) {
			historiqueModificationService.ajouterHistorique(
					contrat,
					"Modification d'archive",
					"Archive modifiée de " + contrat.getArchive() + " à " + c.getArchive()
			);
		}

		if (!contrat.getNom().equals(c.getNom())) {
			historiqueModificationService.ajouterHistorique(
					contrat,
					"Modification du nom",
					"Nom modifié de " + contrat.getNom() + " à " + c.getNom()
			);
		}

		// Mise à jour du contrat
		contrat = contratService.updateContrat(c);

		return contrat;
	}
	@GetMapping("/retrieve-historique/{contrat-id}")
	public ResponseEntity<List<HistoriqueModification>> getHistoriqueByContrat(@PathVariable("contrat-id") Integer contratId) {
		List<HistoriqueModification> historiques = historiqueModificationService.getHistoriqueByContrat(contratId);
		return ResponseEntity.ok(historiques);
	}

}



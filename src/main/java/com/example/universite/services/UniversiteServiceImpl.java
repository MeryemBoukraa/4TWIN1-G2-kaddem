package com.example.universite.services;

import com.example.universite.entities.Universite;
import com.example.universite.repositories.UniversiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UniversiteServiceImpl implements IUniversiteService{
@Autowired
UniversiteRepository universiteRepository;

    public UniversiteServiceImpl() {
        // TODO Auto-generated constructor stub
    }
  public   List<Universite> retrieveAllUniversites(){
return (List<Universite>) universiteRepository.findAll();
    }

 public    Universite addUniversite (Universite  u){
return  (universiteRepository.save(u));
    }

 public    Universite updateUniversite (Universite  u){
     return  (universiteRepository.save(u));
    }

  public Universite retrieveUniversite (Integer idUniversite){
    Universite u = universiteRepository.findById(idUniversite).get();
    return  u;
    }
    public  void deleteUniversite(Integer idUniversite){
        universiteRepository.delete(retrieveUniversite(idUniversite));
    }
    public Universite findUniversiteById(int idUniversite) {
        Universite u = universiteRepository.findById(idUniversite).get();
        return u;
    }

    @Override
    public List<Universite> findUniversitiesWithinRadius(double latitude, double longitude, double radiusKm) {
        List<Universite> allUniversities = StreamSupport
                .stream(universiteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return allUniversities.stream()
                .filter(u -> haversine(latitude, longitude, u.getLatitude(), u.getLongitude()) <= radiusKm)
                .toList();
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }




}

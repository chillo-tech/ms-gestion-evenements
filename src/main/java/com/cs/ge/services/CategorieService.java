package com.cs.ge.services;

import com.cs.ge.entites.Categorie;
import com.cs.ge.repositories.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(final CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }


    public void add(final Categorie categorie) { // en entrée je dois avoir quelque chose sous la forme d'un Utilisateur de type utilisateur
        String name = categorie.getLibelle();
        name = name.toUpperCase();
        categorie.setLibelle(name);
        this.categorieRepository.save(categorie);
    }

    public List<Categorie> search() {
        return this.categorieRepository.findAll();
    }

    public void deleteCategorie(final String id) {
        this.categorieRepository.deleteById(id);
    }

    public void updateCategorie(final String id, final Categorie categorie) {
        final Optional<Categorie> current = this.categorieRepository.findById(id);
        if (current.isPresent()) {
            final Categorie foundUser = current.get();
            foundUser.setId(id);
            foundUser.setLibelle(categorie.getLibelle());
            foundUser.setDescription(categorie.getDescription());
            foundUser.setDescription(categorie.getDescription());
            this.categorieRepository.save(foundUser);
        }
    }

    // public List<Categorie> search(final String id) {
    // return (<Categorie>) this.categorieRepository.findById(id);
    //  }


}

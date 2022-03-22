package com.cs.ge.controllers;


import com.cs.ge.entites.Adresse;
import com.cs.ge.entites.Adresse;
import com.cs.ge.services.AdresseService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping( path = "adresse", produces ="application/json")
public class AdresseControlleur {

    private AdresseService adresseService;

    public AdresseControlleur(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @PostMapping
    public void create (@RequestBody Adresse adresse ){adresseService.creation(adresse);
    }

    @ResponseBody
    @GetMapping
    public List<Adresse> search() {
        return adresseService.search();
    }

    @DeleteMapping (value="/{id}")
    public  void deleteAddress(@PathVariable String id   ){
        adresseService.deleteAddress(id);
    }
    @ResponseBody
    @PutMapping(value="/{id}")
    public void updateAddress (@PathVariable String id, @RequestBody Adresse address) {
        adresseService.updateAddress(id,address);

    }
    @GetMapping ("/queryparam")
    List<Adresse>search(@RequestParam("ville") String ville){
        return adresseService.search();
    }


}

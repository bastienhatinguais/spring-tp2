package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.dto.CityWithCountry;
import monprojet.entity.City;
import monprojet.entity.Country;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/ville") // This means URL's start with /hello (after Application path)
@Slf4j
public class VilleController {

    @Autowired
    private CityRepository cityDAO;

    @Autowired
    private CountryRepository countryDAO;

    // On affichera par défaut la page 'hello.html'
    private static final String DEFAULT_VIEW = "listeVille";

    @GetMapping(path = "show")
    public String afficherListeVilles(@RequestParam Optional<String> success, Model model) {
        List<CityWithCountry> cities = cityDAO.citiesWithCountries();
        List<Country> countries = countryDAO.findAll();
        model.addAttribute("villes", cities);
        model.addAttribute("pays", countries);
        if (success.isPresent())
            model.addAttribute("success", success.get());
        return DEFAULT_VIEW;
    }

    @PostMapping(path = "add")
    public String ajouterVille(@RequestParam String name, @RequestParam int population, @RequestParam int pays,
            Model model) {
        Optional<Country> optionalCountry = countryDAO.findById(pays);
        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();
            City newCity = new City(name, country);
            newCity.setPopulation(population);
            cityDAO.save(newCity);
            return "show?success=La ville a ete ajoute.";
        } else {
            return "redirect:show";
        }

    }

    @GetMapping(path = "delete")
    public String supprimerVille(@RequestParam int id,
            Model model) {
        Optional<City> optionalCity = cityDAO.findById(id);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            cityDAO.delete(city);
        }

        return "show?success=La ville a bien ete supprime.";
    }

    @GetMapping(path = "edit")
    public String modifierVille(@RequestParam int id,
            Model model) {
        Optional<City> optionalCity = cityDAO.findById(id);
        if (optionalCity.isPresent()) {
            List<Country> countries = countryDAO.findAll();
            City ville = optionalCity.get();
            model.addAttribute("pays", countries);
            model.addAttribute("city", ville);
            return "modifierVille";
        } else {
            // la ville n'existe pas
            return "redirect:show";
        }

    }

    @PostMapping(path = "save")
    public String editerVille(@RequestParam int id, @RequestParam String name, @RequestParam int population,
            @RequestParam int pays,
            Model model) {
        Optional<City> optionalCity = cityDAO.findById(id);
        if (optionalCity.isPresent()) {
            City ville = optionalCity.get();
            ville.setCountry(countryDAO.getById(pays));
            ville.setName(name);
            ville.setPopulation(population);
            cityDAO.save(ville);
        }
        return "redirect:show?success=La ville est modifiee.";
    }
}
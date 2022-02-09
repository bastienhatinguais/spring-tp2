package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.dto.CityWithCountry;

public interface CityRepository extends JpaRepository<City, Integer> {

    /**
     * Trouve une ville par son nom.
     * Cette méthode sera automatiquement implémentée par Spring Data JPA.
     * 
     * @param cityName
     * @return La ville correspondant au nom fourni, ou null si pas trouvé.
     */
    public City findByName(String cityName);

    // SQL : formulée sur le modèle logique de données, il faut expliciter la
    // jointure
    @Query("SELECT c.name AS cityName, c.id AS cityId, population as population, c.country.name AS countryName, c.country.id AS countryId FROM City c")
    List<CityWithCountry> citiesWithCountries();

}

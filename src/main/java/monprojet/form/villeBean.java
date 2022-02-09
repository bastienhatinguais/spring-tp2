package monprojet.form;

import java.math.BigInteger;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Un objet java qui sera affiché / modifié par un formulaire HTML
 * 
 * @see demo.controller.HelloController le contrôleur
 *      cf. La vue (src/main/resources/templates/formulaireHello.html)
 */
@Data // https://projectlombok.org/features/Data
public class villeBean {
    // on peut définir des valeurs par défaut pour les différentes propriétés
    private String nom = "";

    private Integer paysId;

    private Integer population;
}

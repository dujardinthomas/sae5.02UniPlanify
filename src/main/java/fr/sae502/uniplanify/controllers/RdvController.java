package fr.sae502.uniplanify.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.sae502.uniplanify.models.CompositeKeyRDV;
import fr.sae502.uniplanify.models.ConstraintPro;
import fr.sae502.uniplanify.models.utils.SenderEmail;
import fr.sae502.uniplanify.models.Unavailability;
import fr.sae502.uniplanify.models.TypicalDayPro;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;

@Controller
@Component
@RequestMapping(value = "/rdv")
public class RdvController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private ConstraintProRepository constraintRepository;

    @Autowired
    private UnavailabilityRepository unavailabilityRepository;

    @Autowired
    private TypicalDayProRepository workdayTypeProRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount user;

    @GetMapping("/confirmSuppressionRdv")
    public String deleteRDV(@RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month,
            @RequestParam(value = "day") int day,
            @RequestParam(value = "hours") int hours,
            @RequestParam(value = "minutes") int minutes,
            Model model) {
        model.addAttribute("rdv", new Rdv(
                new CompositeKeyRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0))));
        return "rdv/confirmSuppressionRdv";
    }

    @GetMapping("/confirmSuppressionRdvParticipant")
    public String deleteParticipantRDV(@RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month,
            @RequestParam(value = "day") int day,
            @RequestParam(value = "hours") int hours,
            @RequestParam(value = "minutes") int minutes,
            Model model) {
        model.addAttribute("rdv", new Rdv(
                new CompositeKeyRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0))));
        return "rdv/confirmSuppressionRdvParticipant";
    }

    @PostMapping("/suppressionRdv")
    public String confirmSuppressionRDV(@RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "day", required = false) int day,
            @RequestParam(value = "hours", required = false) int hours,
            @RequestParam(value = "minutes", required = false) int minutes,
            @RequestParam(value = "reponse") String reponse,
            @RequestParam(value = "raison") String raison,
            Model model) {

        if (!reponse.equals("non")) {

            Rdv rdvExistantASuppr = rdvRepository
                    .findById(new CompositeKeyRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0)))
                    .orElse(null);
            try {

                SenderEmail senderEmail = new SenderEmail();
                for (UserAccount participant : rdvExistantASuppr.getParticipants()) {
                    System.out.println("envoie du mail à " + participant.getEmail() + " : "
                            + senderEmail.sendEmail(sender, participant.getEmail(), "Annulation de votre rendez-vous",
                                    "Votre rendez-vous du " + rdvExistantASuppr.dateToString() + " à "
                                            + rdvExistantASuppr.getHours() + " heures " + rdvExistantASuppr.getMinutes()
                                            + " a été annulé pour la raison suivante : " + raison));
                }

                rdvRepository.delete(rdvExistantASuppr);
                System.out.println("Rdv supprimé : " + rdvExistantASuppr);

            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression du rdv : " + rdvExistantASuppr);
                System.out.println(e.getMessage());
            }
        }
        return "redirect:/my";
    }

    @PostMapping("/suppressionRdvParticipant")
    public String confirmSuppressionParticipantRDV(@RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "day", required = false) int day,
            @RequestParam(value = "hours", required = false) int hours,
            @RequestParam(value = "minutes", required = false) int minutes,
            @RequestParam(value = "reponse") String reponse,
            @RequestParam(value = "raison") String raison,
            Principal principal, Model model) {

        if (!reponse.equals("non")) {

            user = userAccountRepository.findByEmail(principal.getName());

            Rdv rdvExistant = rdvRepository
                    .findById(new CompositeKeyRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0)))
                    .orElse(null);
            System.out.println("Rdv existant : " + rdvExistant);
            try {

                SenderEmail senderEmail = new SenderEmail();
                senderEmail.sendEmail(sender, user.getEmail(), "Confirmation de votre annulation de votre rendez-vous",
                        "Votre rendez-vous du " + rdvExistant.dateToString() + " à "
                                + rdvExistant.getHours() + " heures " + rdvExistant.getMinutes()
                                + " a été correctement annulé par votre part pour la raison suivante : " + raison);
                rdvExistant.removeParticipant(user, raison, constraintRepository);
                if (rdvExistant.getParticipants().isEmpty()) {
                    rdvRepository.delete(rdvExistant);
                } else {
                    rdvRepository.save(rdvExistant);
                }

                Rdv rdvModfie = rdvRepository
                        .findById(new CompositeKeyRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0)))
                        .orElse(null);
                System.out.println("Rdv supprimé : " + rdvModfie);

            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression du rdv : " + rdvExistant);
                System.out.println(e.getMessage());
                System.out.println(e);
            }
        }
        return "redirect:/my";
    }

    @GetMapping(value = "/reserve")
    public String reserve(@RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "day", required = false) int day,
            @RequestParam(value = "hours", required = false) int hours,
            @RequestParam(value = "minutes", required = false) int minutes,
            Principal principal, Model model) {

        LocalDate dateDuRdv = LocalDate.of(year, month, day);
        LocalTime heureDuRdv = LocalTime.of(hours, minutes, 0);
        System.out.println(dateDuRdv);

        user = userAccountRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);

        Map.Entry<Boolean, String> rdvPossibleStatus = getBooleanRdvPossible(dateDuRdv, heureDuRdv).entrySet()
                .iterator().next();

        if (!rdvPossibleStatus.getKey()) {
            // IMPOSSIBLE DE PRENDRE RDV
            model.addAttribute("status", rdvPossibleStatus.getValue());
            model.addAttribute("rdv", new Rdv(new CompositeKeyRDV(dateDuRdv, heureDuRdv)));
            return "rdv/reservation";
        }
        LocalDateTime heureActuelle = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
        String jjMMyy = heureActuelle.format(format);
        String commentaire = "reservé le " + jjMMyy;

        CompositeKeyRDV cleRDV = new CompositeKeyRDV();
        cleRDV.setTimeRdv(heureDuRdv);
        cleRDV.setDayRdv(dateDuRdv);
        Rdv rdvExistant = rdvRepository.findById(cleRDV).orElse(new Rdv());

        ConstraintPro constraint = constraintRepository.findAll().iterator().next();
        int nbPersonneMax = constraint.getNbPersonneMaxDefault();

        String etat = "";
        String statusRDVMail = "";
        if (!rdvExistant.isEmpty()) {
            // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
            if (rdvExistant.getParticipants().size() < nbPersonneMax) {
                // on peut ajouter un client
                try {
                    rdvExistant.addParticipant(user, user.getNom() + " ajouté le " + jjMMyy, constraintRepository);

                    etat = "add";
                    statusRDVMail = "Confirmation de votre ajout au rendez-vous";
                } catch (Exception e) {
                    etat = "error";
                }
            } else {
                etat = "plein";
            }
        } else {
            rdvExistant.setCompositeKeyRDV(cleRDV);
            rdvExistant.addParticipant(user, commentaire, constraintRepository);
            etat = "created";
            statusRDVMail = "Confirmation de votre rendez-vous";
        }

        //ENVOIE CONFIRMATION PAR MAIL
        if (etat.equals("add") || etat.equals("created")) {
            SenderEmail senderEmail = new SenderEmail();
            System.out.println("envoie du mail à " + user.getEmail() + " : "
                    + senderEmail.sendEmail(sender, user.getEmail(), statusRDVMail,
                            "Votre rendez-vous du " + rdvExistant.dateToString() + " à "
                                    + rdvExistant.getHours() + " heures " + rdvExistant.getMinutes()
                                    + " a été enregistré avec succès"));
        }

        rdvRepository.save(rdvExistant);
        model.addAttribute("status", etat);
        model.addAttribute("rdv", rdvExistant);
        return "rdv/reservation";
    }

    /**
     * Teste si le rdv peut etre reservé, si dans le futur et dans les heures
     * d'ouvertures, si pas de chevauchement
     * avec un autre rdv et pas sur une indispo du pro
     * 
     * @return Map avec une seule clé boolean et un string pour l'explication (pour
     *         le html)
     */
    private Map<Boolean, String> getBooleanRdvPossible(LocalDate dateDuRdv, LocalTime heureDuRdv) {
        Map<Boolean, String> statut = new HashMap<>();
        LocalDateTime heureActuelle = LocalDateTime.now();
        LocalDateTime heureDuRdvComplete = LocalDateTime.of(dateDuRdv, heureDuRdv);

        // 1.On vérifie si le rdv est dans le passé
        if (heureDuRdvComplete.isBefore(heureActuelle)) {
            statut.put(false, "old");
            return statut;
        }

        // 2.On verifie si le rdv est dans les horaires d'ouverture
        String dayStringNumberMonthYear = dateDuRdv
                .format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
        TypicalDayPro typicalDayPro = workdayTypeProRepository.findById((dayStringNumberMonthYear.split(" ")[0])).get();
        if (heureDuRdv.isBefore(typicalDayPro.getStartTime()) || heureDuRdv.isAfter(typicalDayPro.getEndTime())) {
            statut.put(false, "hors horaires");
            return statut;
        }

        // 3.On verifie si le rdv chevauche le precedent
        Rdv previousRDV = rdvRepository
                .findFirstByCompositeKeyRDVDayRdvAndCompositeKeyRDVTimeRdvBeforeOrderByCompositeKeyRDVDayRdvDescCompositeKeyRDVTimeRdvDesc(
                        dateDuRdv, heureDuRdv);
        System.out.println("previousRDV : " + previousRDV);
        int dureeRDV = constraintRepository.findAll().iterator().next().getDureeDefaultMinutes();
        if (previousRDV != null && previousRDV.getLocalTime().plusMinutes(dureeRDV).isAfter(heureDuRdv)
                && previousRDV.getLocalDate().equals(dateDuRdv)) {
            statut.put(false, "chevauchement");
            System.out.println("chevauchement avec le rdv precedent");
            return statut;
        }

        // 4.On verifie si le rdv est dans une indisponibilité
        List<Unavailability> indispos = unavailabilityRepository.findByCreneau(dateDuRdv, heureDuRdv);
        System.out.println(indispos);
        System.out.println(indispos.size());
        for (Unavailability indisponibilite : indispos) {
            System.out.println(indisponibilite);
        }
        if (indispos.size() != 0) {
            statut.put(false, "indispo");
            System.out.println("ya une indispo !");
            return statut;
        }

        // 5. On verifie qu'il n'a pas déja été ajouté bizzare car l'erreur ne se catch
        // plus !!!
        CompositeKeyRDV cleRDV = new CompositeKeyRDV();
        cleRDV.setTimeRdv(heureDuRdv);
        cleRDV.setDayRdv(dateDuRdv);
        Rdv rdvExistant = rdvRepository.findById(cleRDV).orElse(null);
        if (rdvExistant != null) {
            if (rdvExistant.getParticipants().contains(user)) {
                statut.put(false, "doublon");
                System.out.println("deja ajouté");
                return statut;
            }
        }

        // TOUTES LES VERIF ETANT FAITES ON AUTORISE LA CREATION DU RDV
        statut.put(true, "ok");
        return statut;
    }
}

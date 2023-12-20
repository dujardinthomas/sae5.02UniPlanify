package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

public class Calendrier {

    private int month;
    private int year;
    private LocalDate firstDayOfMonth;
    private int daysInMonth;
    private int startDayOfWeek;
    private String monthName;

    private int previousMonth;
    private int previousYear;

    private int nextMonth;
    private int nextYear;

    private List<Jour> listJours;

    private JourneeTypeProRepository journeeTypeProRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;
    private ContraintesRepository constraintRepository;

    public Calendrier() {

    }

    public List<Jour> getListJours() {
        return listJours;
    }

    public Calendrier(int year, int month, ContraintesRepository constraintRepository,
            JourneeTypeProRepository journeeTypeProRepository,
            IndisponibiliteRepository indisponibiliteRepository,
            RdvRepository rdvRepository) {
        this.month = month;
        this.year = year;

        this.firstDayOfMonth = LocalDate.of(year, month, 1);
        this.daysInMonth = firstDayOfMonth.lengthOfMonth();
        this.startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        this.monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());


        this.journeeTypeProRepository = journeeTypeProRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.constraintRepository = constraintRepository;

        this.previousMonth = month == 1 ? 12 : month - 1;
        this.previousYear = month == 1 ? year - 1 : year;

        this.nextMonth = month == 12 ? 1 : month + 1;
        this.nextYear = month == 12 ? year + 1 : year;

        this.listJours = generateCalendar(year, month);
    }

    public List<Jour> generateCalendar(int year, int month) {
        List<Jour> listDays = new ArrayList<>();

        Iterable<JourneeTypePro> semainePro = journeeTypeProRepository.findAll();
        List<String> dayWork = new ArrayList<>();
        for (JourneeTypePro journee : semainePro) {
            dayWork.add(journee.getJour());
            System.out.println(journee.getJour());
        }

        for (int day = 1; day <= daysInMonth; day++) {
            // chaque jour
            LocalDate now = LocalDate.of(year, month, day);
            Jour jour = null;
            String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            if (dayWork.contains(dayOfWeek.toString())) {
                // si ouvert lien cliquable
                jour = new Jour(now, true, constraintRepository, journeeTypeProRepository, indisponibiliteRepository,
                    rdvRepository);
            } else {
                jour = new Jour(now, false);
            }
            listDays.add(jour);
            //System.out.println("journée parcourus : " + jour);
            System.out.println("Il y a : " + listDays.size() + " jours dans la liste");
        }
        System.out.println("FINAL : Il y a : " + listDays.size() + " jours dans la liste");
        return listDays;
    }


    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public LocalDate getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public int getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public String getMonthName() {
        return monthName;
    }

    public int getPreviousMonth() {
        return previousMonth;
    }

    public int getPreviousYear() {
        return previousYear;
    }

    public int getNextMonth() {
        return nextMonth;
    }

    public int getNextYear() {
        return nextYear;
    }

}

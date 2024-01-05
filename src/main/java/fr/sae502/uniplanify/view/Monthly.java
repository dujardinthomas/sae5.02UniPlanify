package fr.sae502.uniplanify.view;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sae502.uniplanify.models.TypicalDayPro;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;

public class Monthly {

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

    private List<Daily> listJours;

    private TypicalDayProRepository typicalDayProRepository;
    private UnavailabilityRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;
    private ConstraintProRepository constraintRepository;

    public Monthly() {

    }

    public List<Daily> getListJours() {
        return listJours;
    }

    public Monthly(int year, int month, ConstraintProRepository constraintRepository,
            TypicalDayProRepository typicalDayProRepository,
            UnavailabilityRepository indisponibiliteRepository,
            RdvRepository rdvRepository) {
        this.month = month;
        this.year = year;

        this.firstDayOfMonth = LocalDate.of(year, month, 1);
        this.daysInMonth = firstDayOfMonth.lengthOfMonth();
        this.startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        this.monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());


        this.typicalDayProRepository = typicalDayProRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.constraintRepository = constraintRepository;

        this.previousMonth = month == 1 ? 12 : month - 1;
        this.previousYear = month == 1 ? year - 1 : year;

        this.nextMonth = month == 12 ? 1 : month + 1;
        this.nextYear = month == 12 ? year + 1 : year;

        this.listJours = generateCalendar(year, month);
    }

    public List<Daily> generateCalendar(int year, int month) {
        List<Daily> listDays = new ArrayList<>();

        Iterable<TypicalDayPro> typicalDayIterablePro = typicalDayProRepository.findAll();
        List<String> typicalDayListPro = new ArrayList<>();
        for (TypicalDayPro day : typicalDayIterablePro) {
            typicalDayListPro.add(day.getDay());
            System.out.println(day.getDay());
        }

        for (int day = 1; day <= daysInMonth; day++) {
            // chaque jour
            LocalDate now = LocalDate.of(year, month, day);
            Daily jour = null;
            String dayOfMonth = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            if (typicalDayListPro.contains(dayOfMonth.toString())) {
                // si ouvert lien cliquable

                jour = new Daily(now, true, constraintRepository, typicalDayProRepository, indisponibiliteRepository,
                    rdvRepository);
            } else {
                jour = new Daily(now, false);
            }
            listDays.add(jour);
            System.out.println("journée parcourus : " + jour);
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

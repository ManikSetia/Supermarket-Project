package fact.it.supermarket.model;

/*
    Name: Manik Setia
    Student number: r0912182
 */

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends Person{

    private LocalDate startDate;//date of creation of the staff member

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private boolean student;//indicates this employee is working student or not

    public Staff(String firstName, String surName) {
        super(firstName, surName);
        startDate=LocalDate.now();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String startDate=formatter.format(this.startDate);


        if(student) return "Staff member "+super.toString()+" (working student) is employed since "+startDate;
        return "Staff member "+super.toString()+" is employed since "+startDate;
    }
}

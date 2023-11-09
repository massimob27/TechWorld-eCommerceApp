package com.example.techworld.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class OrderCompare implements Comparator<Ordine> {
    @Override
    public int compare(Ordine o1, Ordine o2) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime ldt1 = LocalDateTime.parse(o1.data(), dtf);
        LocalDateTime ldt2 = LocalDateTime.parse(o2.data(), dtf);

        if (ldt1.isBefore(ldt2)){
            return 1;
        }
        if (ldt1.isAfter(ldt2)){
            return -1;
        }
        return 0;
    }
}

package com.example.metromate01;

import com.google.type.DateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// trip information model fo the cards:
public class Trips {
    int bus_no;
    String departure, arrival, trip_name, price, time;

    public int getBus_no(){
        return bus_no;
    }

    public String getTrip_name(){
        return trip_name;
    }

    public String getDeparture(){
        return departure;
    }

    public String getArrival(){
        return arrival;
    }

    public String getPrice(){
        return price;
    }

    public String getTime(){
        return time;
    }

}

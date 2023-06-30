package com.example.metromate01;


// trip information model for the card
public class trips {

    String arrivalTime, departureTime, cashPrice, tagPrice, busNo ,departureStop, arrivalStop;

    public trips(String arrivalTime, String departureTime, String cashPrice, String tagPrice, String busNo, String departureStop, String arrivalStop){
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.cashPrice = cashPrice;
        this.tagPrice = tagPrice;
        this.busNo = busNo;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
    }
    public String getDepartureStop() {return departureStop;}
    public String getArrivalStop() {return arrivalStop;}

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getCashPrice() {
        return cashPrice;
    }

    public String getTagPrice() {
        return tagPrice;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setDepartureTime(String formattedDepartureTime) {
        this.departureTime = formattedDepartureTime;
    }
}

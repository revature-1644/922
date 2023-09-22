package Model;

import java.util.Objects;

public class Flight {
    private int flightId;
    private String departureCity;
    private String arrivalCity;

    public Flight(){

    }

    public Flight(int flightId, String departureCity, String arrivalCity) {
        this.flightId = flightId;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightId == flight.flightId && Objects.equals(departureCity, flight.departureCity) && Objects.equals(arrivalCity, flight.arrivalCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, departureCity, arrivalCity);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                '}';
    }
}

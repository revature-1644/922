package Service;

import DAO.FlightDAO;
import Model.Flight;

import java.util.List;

public class FlightService implements iFlightService{

    FlightDAO flightDAO;

    public FlightService(FlightDAO flightDAO){
        this.flightDAO = flightDAO;
    }

    public Flight saveFlight(Flight f){
        int id = 0;

        do {
            id = (int) (Math.random() * Integer.MAX_VALUE);
        }while(flightDAO.queryFlightById(id) != null);

        f.setFlightId(id);
        flightDAO.insertFlight(f);

        return f;
    }

    /**
     * Set all flight with a particular arrival city to a new arrival city.
     * @param oldArrivalCity
     * @param newArrivalCity
     */
    public void resetArrivalCities(String oldArrivalCity, String newArrivalCity){
//        first, query all flights.
//        then, loop through to find the flights with a particular arrival city.
//        grab their id's.
//        change their arrival cities individually.
        List<Flight> flights = flightDAO.queryAllFlight();
        for(int i  = 0; i < flights.size(); i++){
//            if the specific flight in the list has an arrival city name equal to my parameter
            if(flights.get(i).getArrivalCity().equals(oldArrivalCity)){
//                grab the current flight's random id, and set its arrival city
                flightDAO.updateArrivalCity(flights.get(i).getFlightId(), newArrivalCity);
            }
        }
    }
    public List<Flight> getAllFlights(){
        return flightDAO.queryAllFlight();
    }
    public Flight getFlightById(int id){
        return flightDAO.queryFlightById(id);
    }
}

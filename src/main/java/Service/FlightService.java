package Service;

import DAO.FlightDAO;
import Model.Flight;

import java.util.List;
import java.util.Map;

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

    /**
     * method 1: we could use a single, long query that both finds the most commonly departed city, and finds the
     * flights for that city at the same time. (subqueries)
     * method 2: we could just make multiple queries, and multiple DAO methods, and work through the logic in the
     * service class.
     *  - first get the counts for the departure city.
     *  - then, get the max value out of those counts, together with the city (in java)
     *  - finally, query the db by departure city.
     * @return
     */
    public List<Flight> getFlightsFromMostCommonlyDepartedCity() {
        Map<String, Integer> departureCityCountMap = flightDAO.getDepartureCounts();
        String currentMaxCity = "na";
        int currentMaxCount = 0;
        for(Map.Entry<String, Integer> element : departureCityCountMap.entrySet()){
            if(element.getValue() > currentMaxCount){
                currentMaxCity = element.getKey();
                currentMaxCount = element.getValue();
            }
        }
        List<Flight> flightsFromMostCommonCity = flightDAO.queryFlightByDepartureCity(currentMaxCity);
        return flightsFromMostCommonCity;
    }
}

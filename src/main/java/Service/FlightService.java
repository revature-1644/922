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
    public List<Flight> getAllFlights(){
        return flightDAO.queryAllFlight();
    }
    public Flight getFlightById(int id){
        return flightDAO.queryFlightById(id);
    }
}

package DAO;

import Model.Flight;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FlightDAO {
    Connection conn;
    public FlightDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * method that persists a flight using JDBC
     * @param flight
     */
    public void insertFlight(Flight flight){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into flight (flight_id, departure_city, arrival_city" +
                    ") values (?, ?, ?)");
            ps.setInt(1, flight.getFlightId());
            ps.setString(2, flight.getDepartureCity());
            ps.setString(3, flight.getArrivalCity());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * a method that returns a list of all flights in the flights table
     * @return
     */
    public List<Flight> queryAllFlight(){
        List<Flight> flightList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from flight");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int dbFlightId = rs.getInt("flight_id");
                String dbDepartureCity = rs.getString("departure_city");
                String dbArrivalCity = rs.getString("arrival_city");
                Flight dbFlight = new Flight(dbFlightId, dbDepartureCity, dbArrivalCity);
                flightList.add(dbFlight);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return flightList;
    }

    /**
     * a method that returns the single flight with a particular flight id
     * @param id
     * @return
     */
    public Flight queryFlightById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from flight where flight_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int dbFlightId = rs.getInt("flight_id");
                String dbDepartureCity = rs.getString("departure_city");
                String dbArrivalCity = rs.getString("arrival_city");
                Flight dbFlight = new Flight(dbFlightId, dbDepartureCity, dbArrivalCity);
                return dbFlight;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * update a arrival city for a particular flight
     */
    public void updateArrivalCity(int flightId, String arrivalCity){
        try{
            PreparedStatement ps = conn.prepareStatement("update flight set arrival_city = ? where flight_id = ?");

            ps.setString(1, arrivalCity);
            ps.setInt(2, flightId);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * get the counts of how many times every departure city was present in the db
     */
    public Map<String, Integer> getDepartureCounts(){
        Map<String, Integer> departureCityCountMap = new HashMap<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select departure_city, count(departure_city) as num_times_appeared from flight group by departure_city");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                departureCityCountMap.put(rs.getString("departure_city"), rs.getInt("num_times_appeared"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return departureCityCountMap;
    }

    public List<Flight> queryFlightByDepartureCity(String departureCity) {
        List<Flight> flightList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from flight where departure_city = ?");
            ps.setString(1, departureCity);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int dbFlightId = rs.getInt("flight_id");
                String dbDepartureCity = rs.getString("departure_city");
                String dbArrivalCity = rs.getString("arrival_city");
                Flight dbFlight = new Flight(dbFlightId, dbDepartureCity, dbArrivalCity);
                flightList.add(dbFlight);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return flightList;
    }
}

import Controller.Controller;
import DAO.FlightDAO;
import Service.FlightService;
import Util.ConnectionSingleton;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
//        this line is just for testing that your tables get set up correctly
//        if not, you'll get a stack trace
        Connection conn = ConnectionSingleton.getConnection();
        FlightDAO flightDAO = new FlightDAO(conn);
        FlightService flightService = new FlightService(flightDAO);
        Controller controller = new Controller(flightService);
        controller.getAPI().start();
    }
}
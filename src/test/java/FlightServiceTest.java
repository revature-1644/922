import DAO.FlightDAO;
import Model.Flight;
import Service.FlightService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class FlightServiceTest {
    FlightService flightService;
    FlightDAO mockFlightDAO;

    @Before
    public void setUp(){
        mockFlightDAO = Mockito.mock(FlightDAO.class);
        flightService = new FlightService(mockFlightDAO);
    }

    /**
     * We expect that the flightService should generate a flight ID that is random, and it should attempt
     * to persist that flight with a randomly generated ID.
     *
     * We'll mock the methods queryFlightByID to act as if the ID is not in use, and we'll verify that the insertFlight
     * method was actually used.
     */
    @Test
    public void randomFlightIDShouldBeGeneratedTest(){
//        arrange
        Mockito.when(mockFlightDAO.queryFlightById(Mockito.anyInt())).thenReturn(null);
        Flight f = new Flight(0, "tampa", "philadelphia");
//        act
        Flight actual = flightService.saveFlight(f);
//        assert
//        the randomly generated flight id should not be the starting default values of 0
        Assert.assertTrue(actual.getFlightId()!=0);
//        verify that the insertFlight method was actually called with any value
        Mockito.verify(mockFlightDAO).insertFlight(Mockito.any());
    }
    /**
     * case 1: there was a single flight whose arrival city should be changed. verify that there was 1 call to the
     * update flight method.
     */
    @Test
    public void updateArrivalCitiesSingleFlightChangedTest(){
//        arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(111, "tampa", "nyc"));
        flightList.add(new Flight(111, "tampa", "philadelphia"));
        flightList.add(new Flight(111, "tampa", "baltimore"));
//        Mock objects won't have any behavior by default.
//        in fact, if we attempt to use a mock object whose behavior hasn't been defined.
//        so, we defined what behavior a flightDAO should exhibit in ideal circumstances - if it's coded 100% correctly.
//        in this pretend situation, the flightDAO should return a list of 3 flights.
//        Mocking a method can allow us to also test unusual circumstances that are difficult to test in a real
//        environment - 'corner cases' - cases where multiple 'edge cases' are combined
        Mockito.when(mockFlightDAO.queryAllFlight()).thenReturn(flightList);
        System.out.println(mockFlightDAO.queryAllFlight());
//        act
        flightService.resetArrivalCities("baltimore", "philadelphia");
//        assert
//        Mockito will keep track of how often its mock objects were used.
//        So, we can assert that a certain mocked method was called some amount of times - once, twice, never, etc.
//        we are asserting that the method updateArrivalCity was called exactly once, with any parameters.
        Mockito.verify(mockFlightDAO, Mockito.times(1))
                .updateArrivalCity(111, "philadelphia");
    }
    /**
     * case 2: there were no flights whose arrival city should be changed. verify that there were no calls to the
     * update flight method.
     */
    @Test
    public void updateArrivalCitiesNoFlightsChangedTest(){
//        arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(111, "tampa", "nyc"));
        flightList.add(new Flight(111, "tampa", "philadelphia"));
        flightList.add(new Flight(111, "tampa", "baltimore"));
        Mockito.when(mockFlightDAO.queryAllFlight()).thenReturn(flightList);
//        act
        flightService.resetArrivalCities("denver", "philadelphia");
//        assert
        Mockito.verify(mockFlightDAO, Mockito.never()).updateArrivalCity(Mockito.anyInt(), Mockito.any());
    }
    /**
     * case 3: there were multiple flights whose arrival city should be changed. verify that there were no calls
     * to the update flight method.
     */
    @Test
    public void updateArrivalCitiesMultipleFlightsChangedTest(){
//        arrange
        List<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(111, "tampa", "baltimore"));
        flightList.add(new Flight(111, "tampa", "philadelphia"));
        flightList.add(new Flight(111, "tampa", "baltimore"));
        Mockito.when(mockFlightDAO.queryAllFlight()).thenReturn(flightList);
//        act
        flightService.resetArrivalCities("baltimore", "philadelphia");
//        assert
        Mockito.verify(mockFlightDAO, Mockito.times(2)).updateArrivalCity(Mockito.anyInt(), Mockito.any());
    }
}

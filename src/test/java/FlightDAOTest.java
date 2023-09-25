import DAO.FlightDAO;
import Model.Flight;
import Util.ConnectionSingleton;
import org.junit.*;

import java.sql.Connection;

public class FlightDAOTest {
    Connection conn;
    FlightDAO flightDAO;

    /**
     * @Test expecting some assertion
     * @Before every test
     * @After every test
     * @BeforeClass once before the entire test suite
     * @AfterClass once after the entire test suite
     */
    @Before
    public void setUp(){
//        before the entire test suite runs, we want to establish a connection to the database
//        and, we also want to create our tables for the first time
//        so, we use a singleton design pattern to retrieve a single connection object for the whole app
//        you may also see a factory design pattern - a factory design pattern instantiates a specific object for you
//        depending on some parameters that you send in - it instantiates a new object for you
        conn = ConnectionSingleton.getConnection();
        flightDAO = new FlightDAO(conn);
    }

    /**
     * Test 1 - if we query an existing flight by ID, we expect a real result.
     */
    @Test
    public void flightByIdExistsTest(){
//        arrange
//        act
        Flight actualFlight = flightDAO.queryFlightById(1111);
//        assert
        Assert.assertNotNull(actualFlight);
    }
    /**
     * Test 2 - if we query a non-existent flight by ID, we expect a null value.
     */
    /**
     * Test 1 - if we query an existing flight by ID, we expect a real result.
     */
    @Test
    public void flightByIdNotExistsTest(){
//        arrange
//        act
        Flight actualFlight = flightDAO.queryFlightById(-1);
//        assert
        Assert.assertNull(actualFlight);
    }
//    after every test, i want to drop the tables used for the test and reset them for the next test
//    this is to prevent any new data inserted or deleted to pollute the results of the other tests
    @After
    public void tearDown(){
        ConnectionSingleton.resetTestDatabase();
    }
}

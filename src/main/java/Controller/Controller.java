package Controller;

import Model.Flight;
import Service.FlightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class Controller {
    FlightService flightService;
    public Controller(FlightService flightService){
        this.flightService = flightService;
    }

    public Javalin getAPI() {
        Javalin app = Javalin.create();
//        this endpoints are RESTful because they identify a resource to work with (the 'flight' model)
//        they descriptively provide information on what the intended action is
//        they will interact with JSON formats of the flight data model
        app.get("/flight", this::getAllFlightHandler);
        app.get("/flight/{id}", this::getFlightByIdHandler);
        app.post("/flight", this::postFlightHandler);
        app.get("/qparam-example", this::qparamtest);

        return app;
    }

    private void getAllFlightHandler(Context context){
        List<Flight> flightList = flightService.getAllFlights();
        context.json(flightList);
    }
    private void getFlightByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("id"));
        Flight flight = flightService.getFlightById(id);
        if(flight == null){
            context.status(404);
        }else{
            context.json(flight);
        }
    }
    private void postFlightHandler(Context context){
        ObjectMapper om = new ObjectMapper();
        try {
            Flight f = om.readValue(context.body(), Flight.class);
            flightService.saveFlight(f);
//            resource created response
            context.status(201);
        }catch(JsonProcessingException e){
//            if the json couldn't be processed, then the user sent us a faulty JSON,
//            so return a 400
            e.printStackTrace();
            context.status(400);
        }

    }
    private void qparamtest(Context context){
        String departureCity = context.queryParam("departureCity");
        context.json(departureCity);
    }

}

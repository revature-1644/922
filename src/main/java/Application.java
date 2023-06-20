import Controller.Controller;
import Util.ConnectionSingleton;

public class Application {
    public static void main(String[] args) {
//        this line is just for testing that your tables get set up correctly
//        if not, you'll get a stack trace
        ConnectionSingleton.getConnection();
//        this line is for starting the javalin server
        Controller controller = new Controller();
        controller.getAPI().start();
    }
}
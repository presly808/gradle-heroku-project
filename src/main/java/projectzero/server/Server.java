package projectzero.server;

import org.apache.log4j.Logger;
import projectzero.controller.IUserController;
import projectzero.controller.UserControllerImpl;
import projectzero.dao.OrderDao;
import projectzero.dao.UserDao;
import projectzero.exceptions.JoinException;
import projectzero.exceptions.LoginException;
import projectzero.model.AppResponse;
import projectzero.model.User;
import projectzero.utils.JSONUtils;
import projectzero.utils.LogUtils;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Server {

    private IUserController userController;
    private Map<String, User> sessionMap;
    private Logger logger;

    public Server(int port, String pathToUserJson, String pathToOrderJson, String externalStaticFileLocation) {
        this.logger = LogUtils.getLogger(Server.class);

        this.sessionMap = new HashMap<>();

        userController = new UserControllerImpl(
                new UserDao(pathToUserJson), new OrderDao(pathToOrderJson));


        port(port);
        externalStaticFileLocation(externalStaticFileLocation);
        before(
                (request, response) -> logger.info(String.format("Protocol: %s, method: %s, path: %s, body: %s",
                        request.protocol(), request.requestMethod(), request.pathInfo(), request.body())),
                (request, response) -> response.type("application/json")
        );

        initEndpoints();
    }


    private void initEndpoints() {
        ResponseTransformer rs = JSONUtils::toJson; // map returned object to JSON
        post("/login", this::login, rs);
        post("/join", this::join, rs);
        get("/getAll", this::getAll, rs);
    }

    // login logic
    private Object login(Request request, Response response) {
        User loginUser = JSONUtils.fromJson(request.body(), User.class);
        String key;
        try {
            key = userController.login(loginUser);

            response.header("key", key);

            sessionMap.put(key, loginUser);
        } catch (LoginException e) {
            logger.info(e.getMessage());
            return new AppResponse(e);
        }

        return new AppResponse(loginUser.getRole());
    }

    // join logic
    private Object join(Request request, Response response) {
        User newUser = JSONUtils.fromJson(request.body(), User.class);
        try {
            userController.join(newUser.getEmail(), newUser.getPass());
//            EmailUtils.notifyUser(newUser.getEmail(),
//                    "Welcome to projectZero",
//                    "Congratulation with joining up");

            return new AppResponse("Welcome to projectZero");
        } catch (JoinException e) {
            logger.info(e);
            return new AppResponse(e);
        }
    }

    //getAll (key in header)
    private Object getAll(Request request, Response response) {
        String key = request.headers("key");
        return userController.getAll(sessionMap.get(key));
    }

}

package projectzero;

import projectzero.server.Server;
import projectzero.utils.EmailUtils;
import projectzero.utils.LogUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        String appPropertiesPath =
          Main.class.getResource("/app.properties").getFile();

        Properties appProperties = new Properties();
        try (InputStream io = new FileInputStream(appPropertiesPath)) {
            appProperties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        String pathToUsersJSON = appProperties.getProperty("pathToUsersJSON");

        pathToUsersJSON = Main.class.getResource(pathToUsersJSON).getFile();

        String pathToOrdersJSON = appProperties.getProperty("pathToOrdersJSON");
        pathToOrdersJSON = Main.class.getResource(pathToOrdersJSON).getFile();

        String externalStaticFileLocation = appProperties.getProperty("externalStaticFileLocation");
        externalStaticFileLocation = Main.class.getResource(externalStaticFileLocation).getFile();


        String SERVER_PORT = System.getenv("PORT");
        if(SERVER_PORT == null){
            SERVER_PORT = "5000";
        }



        new Server(Integer.parseInt(SERVER_PORT), pathToUsersJSON, pathToOrdersJSON, externalStaticFileLocation);
    }
}

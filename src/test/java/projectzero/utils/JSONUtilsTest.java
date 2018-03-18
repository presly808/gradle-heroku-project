package projectzero.utils;

import org.junit.*;
import projectzero.model.Order;
import projectzero.model.OrderStatus;
import projectzero.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class JSONUtilsTest {

    private User user;
    private Order order;
    private static File userJsonWrite;
    private static File userJsonRead;
    private static File orderJsonWrite;
    private static File orderJsonRead;

    @Before
    public void setUp() {
        user = new User("Les@gmail.com", "pass");
        order = new Order(0, "Title", "descr", 100, OrderStatus.CREATED, user);
    }

    @BeforeClass
    public static void classSetUp() throws IOException{
        userJsonWrite = new File("userToWrite.json");
        userJsonRead = new File("userToRead.json");
        orderJsonWrite = new File("orderToWrite.json");
        orderJsonRead = new File("orderToRead.json");
        try(Writer writer = new FileWriter(userJsonRead)) {
            writer.write("[{\"email\":\"Les@gmail.com\",\"pass\":\"pass\",\"role\":\"USER\"}]");
            writer.flush();
        }
        try(Writer writer = new FileWriter(orderJsonRead)) {
            writer.write("[{" +
                    "  \"id\": 0," +
                    "  \"title\": \"Title\"," +
                    "  \"description\": \"descr\"," +
                    "  \"price\": 100," +
                    "  \"status\": \"Good\"," +
                    "  \"openedTime\": {" +
                    "    \"date\": {" +
                    "      \"year\": 2018," +
                    "      \"month\": 3," +
                    "      \"day\": 1" +
                    "    }," +
                    "    \"time\": {" +
                    "      \"hour\": 18," +
                    "      \"minute\": 31, " +
                    "      \"second\": 35," +
                    "      \"nano\": 123000000" +
                    "    }" +
                    "  }," +
                    "  \"owner\": {" +
                    "    \"email\": \"Les@gmail.com\"," +
                    "    \"pass\": \"pass\"," +
                    "    \"role\": \"USER\"" +
                    "  }" +
                    "}]");
            writer.flush();
        }
    }

    @AfterClass
    public static void tearDown() throws IOException {
        Files.delete(userJsonWrite.toPath());
        Files.delete(userJsonRead.toPath());
        Files.delete(orderJsonWrite.toPath());
        Files.delete(orderJsonRead.toPath());
    }

    @Test
    public void readFromFile() throws Exception {
        User[]userList = JSONUtils.readAllFromFile(userJsonRead.toString(), User[].class);
        Assert.assertEquals(user, userList[0]);
        Order[] orderList = JSONUtils.readAllFromFile(orderJsonRead.toString(), Order[].class);
        Assert.assertEquals(order, orderList[0]);
    }

    @Test
    public void writeListIntoFile() {
        JSONUtils.writeListIntoFile(userJsonWrite.toString(), Collections.singletonList(user));
        Assert.assertTrue(Files.exists(userJsonWrite.toPath()));
        JSONUtils.writeListIntoFile(orderJsonWrite.toString(), Collections.singletonList(order));
        Assert.assertTrue(Files.exists(orderJsonWrite.toPath()));
    }
}
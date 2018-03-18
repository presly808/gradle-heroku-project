package projectzero.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JSONUtils {

    private static Gson gson = new Gson();
    private static Logger logger = LogUtils.getLogger(JSONUtils.class);

    public static <T> T  fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }


    /**runAsync
     * Reading from Json file
     *
     * @param path - path of Json file
     * @return list of stmth in Json file
     */
    public static <T> T readAllFromFile(String path, Class<T> objClass) throws IOException {
        try(Reader reader = new FileReader(path)) {
            return gson.fromJson(reader , objClass);
        }
    }

    /**
     * Write a list of any objects into Json file
     *
     * @param path  - path of Json file
     * @param list - list of adding objects
     */
    public static void writeListIntoFile(String path, List<?> list) {
        try (Writer writer = new FileWriter(path, false)) {
            writer.write(gson.toJson(list));
            writer.flush();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}

package property;

import collection.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:40
 **/
public class PropertiesFactory {

    public static final String FILE_NAME = "application.properties";

    public static final String GUN_PROPERTIES_PREFIX = "gun.";

    public static final String EQUAL_SYMBOL = "=";

    public static final String EMPTY_STR = "";

    private static volatile PropertiesFactory instance;

    private static AppProperties appProperties;

    private PropertiesFactory(){};

    public static PropertiesFactory getInstance() {
        if (instance == null) {
            synchronized (AppProperties.class) {
                if(instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    private static PropertiesFactory init() {
        appProperties = AppProperties.getInstance();
        readFile();
        return new PropertiesFactory();
    }

    private static void readFile() {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach(PropertiesFactory::setProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setProperties(String line) {
        if (line != null && !"".equals(line)) {
            if(line.startsWith(GUN_PROPERTIES_PREFIX)) {
                buildGunInfo(lineToPair(line));
            } else {
                buildProperties(lineToPair(line));
            }
        }
    }

    private static Pair<String, String> lineToPair(String line) {
        if (line.contains(EQUAL_SYMBOL)) {
            String[] splited = line.split(EMPTY_STR);
            return new Pair<>(splited[0], splited[1]);
        } else {
            return new Pair<>(line, EMPTY_STR);
        }
    }

    private static void buildGunInfo(Pair<String, String> propertyLine) {
        String[] gunInfo = propertyLine.getKey().split("\\.");
        if () {

        }
    }


    private static void buildProperties(Pair<String, String> propertyLine) {

    }

    public static String buildValueName() {

    }
}

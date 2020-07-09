package property;

import collection.Pair;
import constant.Constants;
import service.GunFactory;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 19:40
 **/
public class PropertiesFactory {

    public static final String FILE_NAME = "application.properties";

    public static final String GUN_PROPERTIES_PREFIX = "gun.";

    private static volatile PropertiesFactory instance;

    private static AppProperties appProperties;

    private static GunFactory gunFactory;

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
        gunFactory = GunFactory.getInstance();
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
        if (line.contains(Constants.EQUAL_SYMBOL)) {
            String[] splited = line.split(Constants.EMPTY_STR);
            return new Pair<>(splited[0], splited[1]);
        } else {
            return new Pair<>(line, Constants.EMPTY_STR);
        }
    }

    private static void buildGunInfo(Pair<String, String> propertyLine) {
        String[] gunInfo = propertyLine.getKey().split("\\.");
        String gunName = gunInfo[1];
        String gunPropertyName = gunInfo[2];
        if (Constants.RECOIL.equals(gunPropertyName)) {
            gunFactory.buildGun(gunName, Double.parseDouble(propertyLine.getValue()));
        } else if(Constants.RECOIL.equals(gunPropertyName)) {
            String[] splitedRecoil = propertyLine.getValue().split(Constants.SPLIT_SYMBOL);
            Double[] recoils = Arrays.stream(splitedRecoil).map(String::trim).map(Double::parseDouble).toArray(Double[]::new);
            gunFactory.buildGun(gunName, recoils);
        } else {
            throw new RuntimeException("gun property invalid. gunPropertyName = " + gunPropertyName);
        }
    }


    private static void buildProperties(Pair<String, String> propertyLine) {
        String propertyName = buildValueName(propertyLine.getKey());
        set(appProperties, propertyName, propertyLine.getValue());
    }

    public static String buildValueName(String valueName) {
        if (valueName.contains(Constants.DOT_SYMBOL)) {
            String[] splitedName = valueName.split(Constants.DOT_SYMBOL);
            StringBuilder finalNameBuilder = new StringBuilder();
            for (int i = 0; i < splitedName.length; i++) {
                if (i == 0) {
                    finalNameBuilder.append(splitedName[i]);
                } else {
                    finalNameBuilder.append(splitedName[i].substring(0, 1).toUpperCase()).append(splitedName[i].substring(1));
                }
            }
            return finalNameBuilder.toString();
        } else {
            return valueName;
        }
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}

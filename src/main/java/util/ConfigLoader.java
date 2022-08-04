package util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * A utility class providing methods to access fields from the config.json configuration file. Provides methods for
 * using JSON paths to access these fields.
 *
 * See <a href="https://github.com/json-path/JsonPath#operators">this page</a> for more information on JSON paths.
 *
 * @author Robert Engle
 */
public class ConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private static Object document;

    static{

        String json = "{}";
        File file = new File("config.json");
        if(!file.exists()){
            LOGGER.error("config.json does not exist. Exiting.");
            System.exit(1);
        }
        try {
            InputStream is = new FileInputStream("config.json");
            json = IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        try{
            document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        } catch (InvalidJsonException e) {
            LOGGER.error("config.json is not valid JSON.", e);
            LOGGER.error("Exiting");
            System.exit(1);
        }

    }

    private ConfigLoader(){
        throw new UnsupportedOperationException("You cannot instantiate this class");
    }

    /**
     * Gets an {@link Object} from the configuration file given the path.
     *
     * See <a href="https://github.com/json-path/JsonPath#operators">this page</a> for more information on JSON paths.
     *
     * @param path the JSON path to get the field for
     * @return the value at the given path
     */
    public static Object get(String path){
        return JsonPath.read(document, path);
    }

    /**
     * Gets the value of the path from the configuration file, casting it to the given class T
     *
     * See <a href="https://github.com/json-path/JsonPath#operators">this page</a> for more information on JSON paths.
     *
     * @param path the JSON path to get the field for
     * @param <T> the type of the class
     * @return the value of the given key, casted accordingly
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String path, Class<T> clazz){
        return (T)JsonPath.read(document, path);
    }

}

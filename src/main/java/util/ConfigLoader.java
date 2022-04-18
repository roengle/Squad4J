package util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Robert Engle
 *
 * A utility class providing methods to access fields from the config.json configuration file. Provides methods for
 * using JSON paths to access these fields.
 *
 * See <a href="https://github.com/json-path/JsonPath#operators">this page</a> for more information on JSON paths.
 */
public class ConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Object document;

    static{
        String json = "{}";
        try{
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            json = new JSONObject(new JSONTokener(br)).toString();
            br.close();
            in.close();
        }catch (Exception e){
            LOGGER.error("Error reading configuration file.");
            LOGGER.error(e.getMessage());
            System.exit(1);
        }
        document = Configuration.defaultConfiguration().jsonProvider().parse(json);
    }

    private ConfigLoader(){
        throw new UnsupportedOperationException("You cannot instantiate this class");
    }

    /**
     * Gets an {@link Object} from the configuation file given the path.
     *
     * See <a href="https://github.com/json-path/JsonPath#operators">this page</a> for more information on JSON paths.
     *
     * @param path the JSON path to get the field for
     * @return the value at the given path
     */
    public static Object get(String path){
        Object returnVal = JsonPath.read(document, path);
        return returnVal;
    }

    /**
     * Gets the value of the path from the configuration file, casting it to the given Class<T>
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

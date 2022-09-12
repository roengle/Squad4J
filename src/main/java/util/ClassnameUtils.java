package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import model.MapLayer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ClassnameUtils {

    private ClassnameUtils(){
        throw new IllegalStateException("Utility classes cannot be instantiated");
    }


    public static String getMapClassname(String currentLayer) throws IOException {
        return getMap().get(currentLayer).getSqLevelId();
    }

    public static String getLayerClassname(String currentLayer) throws IOException {
        return getMap().get(currentLayer).getLayerId();
    }

    private static HashMap<String, MapLayer> getMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(ClassnameUtils.class.getResource("/maps.json").getFile());

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, MapLayer.class);
        return objectMapper.readValue(file, mapType);
    }
}

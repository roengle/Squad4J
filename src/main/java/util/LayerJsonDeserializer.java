package util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.LayerList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LayerJsonDeserializer extends JsonDeserializer<List<LayerList>> {
    @Override
    public List<LayerList> deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        List<LayerList> layers = new ArrayList<>();

        JsonNode node = jp.getCodec().readTree(jp);
        return null;
    }
}

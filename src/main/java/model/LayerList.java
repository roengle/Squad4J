package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import util.LayerJsonDeserializer;

import java.util.List;

@JsonDeserialize(using = LayerJsonDeserializer.class)
public class LayerList {
    private List<Layer> layers;
}

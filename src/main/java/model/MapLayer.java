package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Jacksonized
@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapLayer implements Serializable {
    @JsonProperty("displayName")
    protected String displayName;
    @JsonProperty("layerId")
    protected String layerId;
    @JsonProperty("UE4LevelName")
    protected String ue4LevelName;
    @JsonProperty("SQLevelID")
    protected String sqLevelId;
    @JsonProperty("SQLevelDisplayName")
    protected String sqLevelDisplayName;
    @JsonProperty("gamemode")
    protected String gamemode;
    @JsonProperty("layerVersion")
    protected String layerVersion;
}

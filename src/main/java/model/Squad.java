package entity;

/**
 * @author Robert Engle
 */
public class Squad {
    private final Integer teamId;
    private final Integer id;
    private final String name;
    private final Integer size;
    private final Boolean isLocked;
    private final String creatorName;
    private final String creatorSteam64id;

    public Squad(Integer teamId, Integer id, String name, Integer size, Boolean isLocked, String creatorName, String creatorSteam64id) {
        this.teamId = teamId;
        this.id = id;
        this.name = name;
        this.size = size;
        this.isLocked = isLocked;
        this.creatorName = creatorName;
        this.creatorSteam64id = creatorSteam64id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorSteam64id() {
        return creatorSteam64id;
    }

}

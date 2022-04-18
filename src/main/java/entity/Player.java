package entity;

import java.util.Optional;

public class Player {
    private final Integer id;
    private final String steam64id;
    private final String name;
    private final Integer teamID;
    private final Integer squadID;
    private final Boolean isSquadLeader;
    private final String role;

    public Player(Integer id, String steam64id, String name, Integer teamID, Integer squadID, Boolean isSquadLeader, String role) {
        this.id = id;
        this.steam64id = steam64id;
        this.name = name;
        this.teamID = teamID;
        this.squadID = squadID;
        this.isSquadLeader = isSquadLeader;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getSteam64id() {
        return steam64id;
    }

    public String getName() {
        return name;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public Optional<Integer> getSquadID() {
        return Optional.ofNullable(squadID);
    }

    public Boolean getSquadLeader() {
        return isSquadLeader;
    }

    public String getRole() {
        return role;
    }

}

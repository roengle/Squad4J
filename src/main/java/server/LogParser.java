package server;

import event.Event;
import event.EventType;
import event.logparser.*;
import event.rcon.ChatMessageEvent;
import event.rcon.PlayerBannedEvent;
import event.rcon.PlayerKickedEvent;
import event.rcon.PlayerWarnedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Map<Pattern, EventType> logPatterns = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(LogParser.class);

    static{
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)\\]\\[([ 0-9]*)\\]LogSquad: ADMIN COMMAND: Message broadcasted <(.+)> from (.+)"), EventType.ADMIN_BROADCAST);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQDeployable::)?TakeDamage\\(\\): ([A-z0-9_]+)_C_[0-9]+: ([0-9.]+) damage attempt by causer ([A-z0-9_]+)_C_[0-9]+ instigator (.+) with damage type ([A-z0-9_]+)_C health remaining ([0-9.]+)"), EventType.DEPLOYABLE_DAMAGED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogWorld: Bringing World \\/([A-z]+)\\/(?:Maps\\/)?([A-z0-9-]+)\\/(?:.+\\/)?([A-z0-9-]+)(?:\\.[A-z0-9-]+) up for play \\(max tick rate ([0-9]+)\\) at ([.0-9]+)-([.0-9]+)"), EventType.NEW_GAME);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogNet: Join succeeded: (.+)"), EventType.PLAYER_CONNECTED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: Player:(.+) ActualDamage=([0-9.]+) from (.+) caused by ([A-z_0-9]+)_C_([0-9]+)"), EventType.PLAYER_DAMAGED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQSoldier::)?Die\\(\\): Player:(.+) KillingDamage=(?:-)*([0-9.]+) from ([A-z_0-9]+) caused by ([A-z_0-9]+)_C_([0-9]+)"), EventType.PLAYER_DIED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogEasyAntiCheatServer: \\[[0-9:]+] \\[[A-z]+] \\[EAC Server] \\[Info] \\[UnregisterClient] Client: ([A-z0-9]+) PlayerGUID: ([0-9]{17})"), EventType.PLAYER_DISCONNECTED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQPlayerController::)?OnPossess\\(\\): PC=(.+) Pawn=([A-z0-9_]+)_C_([0-9]+) FullPath=(?:[A-Za-z0-9._]+) (?:[A-Za-z0-9./_:]+)"), EventType.PLAYER_POSSESS);
        logPatterns.put(Pattern.compile("\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: (.+) has revived (.+)\\."), EventType.PLAYER_REVIVED);
        logPatterns.put(Pattern.compile("\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQPlayerController::)?OnUnPossess\\(\\): PC=(.+)"), EventType.PLAYER_UNPOSSESS);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQSoldier::)?Wound\\(\\): Player:(.+) KillingDamage=(?:-)*([0-9.]+) from ([A-z_0-9]+) caused by ([A-z_0-9]+)_C_([0-9]+)"), EventType.PLAYER_WOUNDED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQGameMode::)?DetermineMatchWinner\\(\\): (.+) won on (.+)"), EventType.ROUND_WINNER);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: USQGameState: Server Tick Rate: ([0-9.]+)"), EventType.SERVER_TICK_RATE);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: (.+) \\(Steam ID: ([0-9]{17})\\) has created Squad (\\d+) \\(Squad Name: (.+)\\) on (.+)"), EventType.SQUAD_CREATED);
        logPatterns.put(Pattern.compile("^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogEasyAntiCheatServer: \\[[0-9:]+] \\[[A-z]+] \\[EAC Server] \\[Info] \\[RegisterClient] Client: (?:[A-z0-9]+) PlayerGUID: ([0-9]{17}) PlayerIP: [0-9]{17} OwnerGUID: [0-9]{17} PlayerName: (.+)"), EventType.STEAMID_CONNECTED);
        logPatterns.put(Pattern.compile("\\[(ChatAll|ChatTeam|ChatSquad|ChatAdmin)] \\[SteamID:([0-9]{17})] (.+?) : (.*)"), EventType.CHAT_MESSAGE);
        logPatterns.put(Pattern.compile("\\[SteamID:([0-9]{17})] (.+?) has possessed admin camera."), EventType.POSSESSED_ADMIN_CAM);
        logPatterns.put(Pattern.compile("\\[SteamID:([0-9]{17})] (.+?) has unpossessed admin camera."), EventType.UNPOSSESSED_ADMIN_CAM);
        logPatterns.put(Pattern.compile("Remote admin has warned player (.*)\\. Message was \"(.*)\""), EventType.PLAYER_WARNED);
        logPatterns.put(Pattern.compile("Kicked player ([0-9]+)\\. \\[steamid=([0-9]{17})] (.*)"), EventType.PLAYER_KICKED);
        logPatterns.put(Pattern.compile("Banned player ([0-9]+)\\. \\[steamid=(.*?)\\] (.*) for interval (.*)"), EventType.PLAYER_BANNED);
    }

    private LogParser(){
        throw new UnsupportedOperationException("You cannot instantiate this class.");
    }

    public static void parseLine(String line){
        AtomicReference<Event> event = new AtomicReference<>(null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss:SSS");

        logPatterns.forEach((pattern, type) -> {
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                try{
                    switch (type){
                        case ADMIN_BROADCAST:
                            event.set(new AdminBroadcastEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case DEPLOYABLE_DAMAGED:
                            event.set(new DeployableDamagedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    matcher.group(5),
                                    matcher.group(6),
                                    matcher.group(7),
                                    Integer.parseInt(matcher.group(8))
                            ));
                            break;
                        case NEW_GAME:
                            event.set(new NewGameEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4),
                                    matcher.group(5),
                                    Integer.parseInt(matcher.group(6))
                            ));
                            break;
                        case PLAYER_CONNECTED:
                            event.set(new PlayerConnectedEvent(
                               formatter.parse(matcher.group(1)),
                               type,
                               Integer.parseInt(matcher.group(3)),
                               matcher.group(4)
                            ));
                            break;
                        case PLAYER_DAMAGED:
                            event.set(new PlayerDamagedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    matcher.group(5),
                                    matcher.group(6),
                                    matcher.group(7)
                            ));
                            break;
                        case PLAYER_DIED:
                            event.set(new PlayerDiedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    matcher.group(5),
                                    matcher.group(6)
                            ));
                            break;
                        case PLAYER_DISCONNECTED:
                            event.set(new PlayerDisconnectedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3)
                            ));
                            break;
                        case PLAYER_POSSESS:
                            event.set(new PlayerPossessEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case PLAYER_REVIVED:
                            event.set(new PlayerRevivedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case PLAYER_UNPOSSESS:
                            event.set(new PlayerUnPossessEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3)
                            ));
                            break;
                        case PLAYER_WOUNDED:
                            event.set(new PlayerWoundedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    matcher.group(5),
                                    matcher.group(6)
                            ));
                            break;
                        case ROUND_WINNER:
                            event.set(new RoundWinnerEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case SERVER_TICK_RATE:
                            event.set(new ServerTickRateEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    Double.parseDouble(matcher.group(3))
                            ));
                            break;
                        case SQUAD_CREATED:
                            event.set(new SquadCreatedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4),
                                    Integer.parseInt(matcher.group(5)),
                                    matcher.group(6),
                                    matcher.group(7)
                            ));
                            break;
                        case STEAMID_CONNECTED:
                            event.set(new SteamidConnectedEvent(
                                    formatter.parse(matcher.group(1)),
                                    type,
                                    Integer.parseInt(matcher.group(2)),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case CHAT_MESSAGE:
                            event.set(new ChatMessageEvent(
                                    new Date(),
                                    type,
                                    matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case PLAYER_BANNED:
                            event.set(new PlayerBannedEvent(
                                    new Date(),
                                    type,
                                    matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4)
                            ));
                            break;
                        case PLAYER_KICKED:
                            event.set(new PlayerKickedEvent(
                                    new Date(),
                                    type,
                                    matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3)
                            ));
                            break;
                        case PLAYER_WARNED:
                            event.set(new PlayerWarnedEvent(
                                    new Date(),
                                    type,
                                    matcher.group(1),
                                    matcher.group(2)
                            ));
                            break;
                    }
                }catch (ParseException e){
                    LOGGER.error("Error parsing date.");
                    LOGGER.error(e.getMessage());
                }
            }
        }); //end logPatterns forEach

        if(event.get() != null){
            EventEmitter.emit(event.get());
        }

    }//end parseLine method
}//end LogParser class

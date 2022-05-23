package server.tailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.LogParser;
import org.apache.commons.io.input.TailerListenerAdapter;

import java.util.regex.Pattern;

public class LogTailer extends TailerListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogTailer.class);

    @Override
    public void handle(String line){
        //Atrocious regex pattern. Is used for optimization
        if(line.matches("^\\[([0-9.:-]+)\\]\\[([ 0-9]*)\\]LogSquad: ADMIN COMMAND: Message broadcasted <(.+)> from (.+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQDeployable::)?TakeDamage\\(\\): ([A-z0-9_]+)_C_[0-9]+: ([0-9.]+) damage attempt by causer ([A-z0-9_]+)_C_[0-9]+ instigator (.+) with damage type ([A-z0-9_]+)_C health remaining ([0-9.]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogWorld: Bringing World \\/([A-z]+)\\/(?:Maps\\/)?([A-z0-9-]+)\\/(?:.+\\/)?([A-z0-9-]+)(?:\\.[A-z0-9-]+) up for play \\(max tick rate ([0-9]+)\\) at ([.0-9]+)-([.0-9]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogNet: Join succeeded: (.+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: Player:(.+) ActualDamage=([0-9.]+) from (.+) caused by ([A-z_0-9]+)_C_([0-9]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQSoldier::)?Die\\(\\): Player:(.+) KillingDamage=(?:-)*([0-9.]+) from ([A-z_0-9]+) caused by ([A-z_0-9]+)_C_([0-9]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogEasyAntiCheatServer: \\[[0-9:]+] \\[[A-z]+] \\[EAC Server] \\[Info] \\[UnregisterClient] Client: ([A-z0-9]+) PlayerGUID: ([0-9]{17})|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQPlayerController::)?OnPossess\\(\\): PC=(.+) Pawn=([A-z0-9_]+)_C_([0-9]+) FullPath=(?:[A-Za-z0-9._]+) (?:[A-Za-z0-9./_:]+)|\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: (.+) has revived (.+)\\.|\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQPlayerController::)?OnUnPossess\\(\\): PC=(.+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQSoldier::)?Wound\\(\\): Player:(.+) KillingDamage=(?:-)*([0-9.]+) from ([A-z_0-9]+) caused by ([A-z_0-9]+)_C_([0-9]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquadTrace: \\[DedicatedServer](?:ASQGameMode::)?DetermineMatchWinner\\(\\): (.+) won on (.+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: USQGameState: Server Tick Rate: ([0-9.]+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogSquad: (.+) \\(Steam ID: ([0-9]{17})\\) has created Squad (\\d+) \\(Squad Name: (.+)\\) on (.+)|^\\[([0-9.:-]+)]\\[([ 0-9]*)]LogEasyAntiCheatServer: \\[[0-9:]+] \\[[A-z]+] \\[EAC Server] \\[Info] \\[RegisterClient] Client: (?:[A-z0-9]+) PlayerGUID: ([0-9]{17}) PlayerIP: [0-9]{17} OwnerGUID: [0-9]{17} PlayerName: (.+)"))
        {
            try{
                LogParser.parseLine(line);
            }catch (Exception e){
                LOGGER.error("Error parsing line.", e);
            }
        }
    }
}

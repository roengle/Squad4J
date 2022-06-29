package plugins;

import a2s.response.A2SInfoResponse;
import a2s.response.A2SRulesResponse;
import connector.MySQLConnector;
import event.a2s.A2SUpdatedEvent;
import event.logparser.*;
import listener.a2s.A2SUpdatedListener;
import listener.logparser.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@NoArgsConstructor
public class DBLog implements A2SUpdatedListener, NewGameListener, PlayerDiedListener, PlayerRevivedListener,
        PlayerWoundedListener, RoundWinnerListener, ServerTickRateListener, SteamidConnectedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBLog.class);

    @Override
    public void onA2SUpdated(A2SUpdatedEvent a2SUpdatedEvent) {
        //TODO: Remove try-catch after debugging
        try{
            A2SInfoResponse info = a2SUpdatedEvent.getResponse().getInfo();
            A2SRulesResponse rules = a2SUpdatedEvent.getResponse().getRules();

            String serverName = info.getName();
            Date time = a2SUpdatedEvent.getTime();

            Integer playerCount = Integer.valueOf(rules.getRuleValue("PlayerCount_i"));
            Integer publicQueue = Integer.valueOf(rules.getRuleValue("PublicQueue_i"));
            Integer reserveQueue = Integer.valueOf(rules.getRuleValue("ReservedQueue_i"));
            Integer match = MySQLConnector.getCurrentMatchId();

            MySQLConnector.insertPlayerCount(time, playerCount, publicQueue, reserveQueue, match);
        }catch (Exception e){
            LOGGER.error("Exception in DBLog#onA2SUpdated. ", e);
        }
    }

    @Override
    public void onNewGame(NewGameEvent newGameEvent) {
        String dlc = newGameEvent.getDlc();
        String map = newGameEvent.getMapName();
        String layer = newGameEvent.getLayerName();
        Date startTime = newGameEvent.getTime();

        //TODO: Properly use map and layer classnames
        MySQLConnector.insertMatch(dlc, "null", "null",
                map, layer, startTime);

    }

    @Override
    public void onPlayerDied(PlayerDiedEvent playerDiedEvent) {

    }

    @Override
    public void onPlayerRevived(PlayerRevivedEvent playerRevivedEvent) {

    }

    @Override
    public void onPlayerWoundedEvent(PlayerWoundedEvent playerWoundedEvent) {

    }

    @Override
    public void onRoundWinner(RoundWinnerEvent roundWinnerEvent) {

    }

    @Override
    public void onServerTickRate(ServerTickRateEvent serverTickRateEvent) {
        //TODO: Remove try-catch after debugging
        try{
            Date time = serverTickRateEvent.getTime();
            Double tickRate = serverTickRateEvent.getTickRate();

            Integer match = MySQLConnector.getCurrentMatchId();

            MySQLConnector.insertTickRate(time, tickRate, match);
        }catch (Exception e){
            LOGGER.error("DBLog onServerTickRate exception", e);
        }
    }

    @Override
    public void onSteamidConnected(SteamidConnectedEvent steamidConnectedEvent) {

    }
}

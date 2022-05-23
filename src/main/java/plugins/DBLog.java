package plugins;

import a2s.response.A2SInfoResponse;
import a2s.response.A2SRulesResponse;
import connector.MySQLConnector;
import event.a2s.A2SUpdatedEvent;
import event.logparser.*;
import listener.a2s.A2SUpdatedListener;
import listener.logparser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.SquadServer;

import java.util.Date;

public class DBLog implements A2SUpdatedListener, NewGameListener, PlayerDiedListener, PlayerRevivedListener, PlayerWoundedListener, RoundWinnerListener, ServerTickRateListener,
        SteamidConnectedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBLog.class);

    @Override
    public void onA2SUpdated(A2SUpdatedEvent a2SUpdatedEvent) {
        A2SInfoResponse info = a2SUpdatedEvent.getResponse().getInfo();
        A2SRulesResponse rules = a2SUpdatedEvent.getResponse().getRules();

        String serverName = info.getName();
        Date time = a2SUpdatedEvent.getTime();

        Integer playerCount = Integer.valueOf(rules.getRuleValue("PlayerCount_i"));
        Integer publicQueue = Integer.valueOf(rules.getRuleValue("PublicQueue_i"));
        Integer reserveQueue = Integer.valueOf(rules.getRuleValue("ReservedQueue_i"));
        Integer match = MySQLConnector.getMatch();

        LOGGER.trace("DBLog: Inserting PlayerCount(time:{}, playerCount:{}, publicQueue:{}, reserveQueue:{}, match:{})",
                time, playerCount, publicQueue, reserveQueue, match);

        MySQLConnector.insertPlayerCount(time, playerCount, publicQueue, reserveQueue, match);

    }

    @Override
    public void onNewGame(NewGameEvent newGameEvent) {
        Date endTime = newGameEvent.getTime();
        String winningFaction = SquadServer.getMostRecentWinner();
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

    }

    @Override
    public void onSteamidConnected(SteamidConnectedEvent steamidConnectedEvent) {

    }
}

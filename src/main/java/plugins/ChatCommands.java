package plugins;

import com.jayway.jsonpath.JsonPathException;
import event.rcon.ChatMessageEvent;
import listener.rcon.ChatMessageListener;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rcon.Rcon;
import util.ConfigLoader;

import java.util.*;

/**
 * @author Robert Engle
 *
 * Implementation of <code>ChatCommands</code> from <a href="https://github.com/Team-Silver-Sphere/SquadJS#chatcommands">SquadJS</a>.
 * ChatCommands is used to either warn or broadcast a message whenever a command is typed in a chat. Warnings will warn
 * the player who typed the message, broadcasts will simply broadcast to the whole server.
 *
 * A prefix for commands can be configured. Each individual command can also be configured to ignore certain chat types.
 */
public class ChatCommands implements ChatMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatCommands.class);
    private static final String PREFIX = ConfigLoader.get("$.plugins.ChatCommands.prefix", String.class);

    private static final Map<String, ChatCommands.Command> warnCommands = new HashMap<>();
    private static final Map<String, ChatCommands.Command> broadcastCommands = new HashMap<>();

    private static class Command{
        private final String response;
        private final List<String> ignoreChats;

        protected Command(String response, List<String> ignoreChats){
            this.response = response;
            this.ignoreChats = ignoreChats;
        }

        public String getResponse() {
            return response;
        }

        public List<String> getIgnoreChats() {
            return ignoreChats;
        }
    }

    static{
        //TODO: Improve this implementation, this is first iteration.
        //TODO: For future versions, include ability to put "variables" into messages. For example, {{server.nextLayer}} could be placed, which would render to the next layer name.
        try{
            Integer numCommands = ConfigLoader.get("plugins.ChatCommands.commands.length()", Integer.class);
            for(int i = 0; i < numCommands; i++){
                Map<Object, Object> entries = ConfigLoader.get(
                        String.format("plugins.ChatCommands.commands[%s]", i), Map.class
                );
                String commandName = (String) entries.get("command");
                String type = (String) entries.get("type");
                String response = (String) entries.get("response");
                List<String> ignoreChats = new ArrayList<>();
                Integer numIgnoreChats = ((JSONArray) entries.get("ignoreChats")).size();
                for(int j = 0; j < numIgnoreChats; j++){
                    ignoreChats.add((String) ((JSONArray)entries.get("ignoreChats")).get(j));
                }

                Command command = new Command(response, ignoreChats);
                switch(type){
                    case "warn":
                        warnCommands.put(commandName, command);
                        break;
                    case "broadcast":
                        broadcastCommands.put(commandName, command);
                        break;
                    default:
                        LOGGER.error("\"type\" must be either \"warn\" or \"broadcast\". It currently is " + type);
                }
            }
        }catch(JsonPathException pathExp){
            LOGGER.error("There was an error parsing a JSON path.");
            LOGGER.error(pathExp.getMessage());
        }
    }


    @Override
    public void onChatMessage(ChatMessageEvent chatMessageEvent) {
        String userSteamid = chatMessageEvent.getSteamid();
        String message = chatMessageEvent.getMessage();
        String chatType = chatMessageEvent.getChatType();

        if(message.matches(PREFIX + "(.+)")){
            message = message.replaceFirst("!", "");
            if(warnCommands.containsKey(message)){
                Command command = warnCommands.get(message);
                List<String> ignoreChats = command.getIgnoreChats();
                if(!ignoreChats.contains(chatType)){
                    String response = command.getResponse();
                    Rcon.command(String.format("AdminWarn %s %s", userSteamid, response));
                }
            }
            if(broadcastCommands.containsKey(message)){
                Command command = broadcastCommands.get(message);
                List<String> ignoreChats = command.getIgnoreChats();
                if(!ignoreChats.contains(chatType)){
                    String response = command.getResponse();
                    Rcon.command(String.format("AdminBroadcast %s", response));
                }
            }
        }
    }
}

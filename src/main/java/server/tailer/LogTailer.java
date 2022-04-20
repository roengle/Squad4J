package server.tailer;

import server.LogParser;
import org.apache.commons.io.input.TailerListenerAdapter;

public class LogTailer extends TailerListenerAdapter {

    @Override
    public void handle(String line){
        LogParser.parseLine(line);
    }
}

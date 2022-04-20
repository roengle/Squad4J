package util.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import util.ConfigLoader;

public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {
    private static final boolean traceBold = ConfigLoader.get("$.logger.colors.trace.bold", Boolean.class);
    private static final String traceColor = ConfigLoader.get("$.logger.colors.trace.color", String.class);
    private static final boolean debugBold = ConfigLoader.get("$.logger.colors.debug.bold", Boolean.class);
    private static final String debugColor = ConfigLoader.get("$.logger.colors.debug.color", String.class);
    private static final boolean infoBold = ConfigLoader.get("$.logger.colors.info.bold", Boolean.class);
    private static final String infoColor = ConfigLoader.get("$.logger.colors.info.color", String.class);
    private static final boolean warnBold = ConfigLoader.get("$.logger.colors.warn.bold", Boolean.class);
    private static final String warnColor = ConfigLoader.get("$.logger.colors.warn.color", String.class);
    private static final boolean errorBold = ConfigLoader.get("$.logger.colors.error.bold", Boolean.class);
    private static final String errorColor = ConfigLoader.get("$.logger.colors.error.color", String.class);

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String output = "";
        Level level = event.getLevel();

        switch (level.toInt()) {
            case Level.TRACE_INT:
                if(traceBold)
                    output += ANSIConstants.BOLD;
                output += getANSIConstantByName(traceColor);
                return output;
            case Level.DEBUG_INT:
                if(debugBold)
                    output += ANSIConstants.BOLD;
                output += getANSIConstantByName(debugColor);
                return output;
            case Level.ERROR_INT:
                if(errorBold)
                    output += ANSIConstants.BOLD;
                output += getANSIConstantByName(errorColor);
                return output;
            case Level.WARN_INT:
                if(warnBold)
                    output += ANSIConstants.BOLD;
                output += getANSIConstantByName(warnColor);
                return output;
            case Level.INFO_INT:
                if(infoBold)
                    output += ANSIConstants.BOLD;
                output += getANSIConstantByName(infoColor);
                return output;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

    private static String getANSIConstantByName(String colorName){
        String name = colorName.toLowerCase();
        switch(name){
            case "black":
                return ANSIConstants.BLACK_FG;
            case "blue":
                return ANSIConstants.BLUE_FG;
            case "cyan":
                return ANSIConstants.CYAN_FG;
            case "green":
                return ANSIConstants.GREEN_FG;
            case "magenta":
                return ANSIConstants.MAGENTA_FG;
            case "red":
                return ANSIConstants.RED_FG;
            case "white":
                return ANSIConstants.WHITE_FG;
            case "yellow":
                return ANSIConstants.YELLOW_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

}
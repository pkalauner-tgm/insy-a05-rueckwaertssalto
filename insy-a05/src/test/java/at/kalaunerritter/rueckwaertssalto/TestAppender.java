package at.kalaunerritter.rueckwaertssalto;


import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Wird verwendet, um die Ausgabe von Log4J zu testen
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class TestAppender extends AppenderSkeleton {

    private final List<LoggingEvent> log = new ArrayList<>();

    @Override
    public boolean requiresLayout() {
        return false;
    }
 
    @Override
    protected void append(final LoggingEvent loggingEvent) {
        log.add(loggingEvent);
    }
 
    @Override
    public void close() {
    }
 
    public List<LoggingEvent> getLog() {
        return new ArrayList<>(log);
    }
}
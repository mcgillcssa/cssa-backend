package ca.mcgillcssa.cssabackend.util;

import java.time.format.DateTimeFormatter;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import ca.mcgillcssa.cssabackend.model.Event;

public class BeforeConvertListener extends AbstractMongoEventListener<Event> {
  @Override
  public void onBeforeConvert(BeforeConvertEvent<Event> event) {
    Event source = event.getSource();
    String eventName = source.getEventName();
    String eventStartDateString = source.getEventStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String id = eventName.concat(eventStartDateString);
    source.setId(id);
  }
}

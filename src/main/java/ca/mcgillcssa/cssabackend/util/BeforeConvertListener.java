package ca.mcgillcssa.cssabackend.util;

import java.time.format.DateTimeFormatter;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;

public class BeforeConvertListener extends AbstractMongoEventListener<CSSAEvent> {
  @Override
  public void onBeforeConvert(BeforeConvertEvent<CSSAEvent> event) {
    CSSAEvent cssaEvent = event.getSource();
    String eventName = cssaEvent.getEventName();
    String eventStartDateString = cssaEvent.getEventStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String id = eventName.concat(eventStartDateString);
    cssaEvent.setId(id);
  }
}

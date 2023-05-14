package ca.mcgillcssa.cssabackend.util;

import org.springframework.boot.context.properties.PropertyMapper.Source;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import ca.mcgillcssa.cssabackend.model.Event;

public class BeforeConvertListener extends AbstractMongoEventListener<Event> {
  @Override
  public void onBeforeConvert(BeforeConvertEvent<Event> event) {
    Event source = event.getSource();
    var eventName = source.getEventName();
    var eventStartDate = source.getEventStartDate();
    var id = eventName.concat(eventStartDate.toString());
    source.setId(Integer.parseInt(id));
  }
}

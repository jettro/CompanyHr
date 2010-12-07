package nl.gridshore.companyhr.app.axon;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventstore.EventSerializer;

import javax.persistence.Id;
import java.nio.charset.Charset;

/**
 * @author Jettro Coenradie
 */
public class EventEntry {
    private String eventIdentifier;
    private String aggregateIdentifier;
    private long sequenceNumber;
    private String timeStamp;
    private String aggregateType;
    private String serializedEvent;

    /**
     * Charset used for the serialization is usually UTF-8, which is presented by this constant
     */
    protected static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Constructor used to create a new event entry to store in Mongo
     *
     * @param aggregateType   String containing the aggregate type of the event
     * @param event           The actual DomainEvent to store
     * @param eventSerializer Serializer to use for the event to store
     */
    EventEntry(String aggregateType, DomainEvent event, EventSerializer eventSerializer) {
        this.eventIdentifier = event.getEventIdentifier().toString();
        this.aggregateType = aggregateType;
        this.aggregateIdentifier = event.getAggregateIdentifier().asString();
        this.sequenceNumber = event.getSequenceNumber();
        this.serializedEvent = new String(eventSerializer.serialize(event));
        this.timeStamp = event.getTimestamp().toString();
    }

    /**
     * Returns the actual DomainEvent from the EventEntry using the provided EventSerializer
     *
     * @param eventSerializer Serializer used to de-serialize the stored DomainEvent
     * @return The actual DomainEvent
     */
    public DomainEvent getDomainEvent(EventSerializer eventSerializer) {
        return eventSerializer.deserialize(serializedEvent.getBytes(UTF8));
    }

    /**
     * getter for the sequence number of the event
     *
     * @return long representing the sequence number of the event
     */
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * getter for the aggregate identifier
     *
     * @return AggregateIdentifier for this EventEntry
     */
    public AggregateIdentifier getAggregateIdentifier() {
        return new StringAggregateIdentifier(aggregateIdentifier);
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public String getSerializedEvent() {
        return serializedEvent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    Entity obtainEntity() {
        Key key = KeyFactory.createKey(aggregateType, eventIdentifier);
        Entity entity = new Entity(key);
        entity.setProperty("aggregateIdentifier",aggregateIdentifier);
        entity.setProperty("sequenceNumber", sequenceNumber);
        entity.setProperty("timeStamp", timeStamp);
        entity.setProperty("serializedEvent", new Text(serializedEvent));

        return entity;
    }
}

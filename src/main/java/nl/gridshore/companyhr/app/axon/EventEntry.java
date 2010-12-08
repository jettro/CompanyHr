package nl.gridshore.companyhr.app.axon;

import com.google.appengine.api.datastore.*;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventstore.EventSerializer;

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

    Entity asEntity() {
        Key key = KeyFactory.createKey(aggregateType, eventIdentifier);
        Entity entity = new Entity(key);
        entity.setProperty("aggregateIdentifier", aggregateIdentifier);
        entity.setProperty("sequenceNumber", sequenceNumber);
        entity.setProperty("timeStamp", timeStamp);
        entity.setProperty("serializedEvent", new Text(serializedEvent));

        return entity;
    }

    /**
     * Returns the gae query used to query google app engine for events for specified aggregate identifier and type
     *
     * @param type                The type of the aggregate to create the mongo DBObject for
     * @param aggregateIdentifier Identifier of the aggregate to obtain the mongo DBObject for
     * @param firstSequenceNumber number representing the first event to obtain
     * @return Created DBObject based on the provided parameters to be used for a query
     */

    static Query forAggregate(String type, String aggregateIdentifier, long firstSequenceNumber) {
        return new Query(type)
                .addFilter("aggregateIdentifier", Query.FilterOperator.EQUAL, aggregateIdentifier)
                .addFilter("sequenceNumber", Query.FilterOperator.GREATER_THAN_OR_EQUAL, firstSequenceNumber)
                .addSort("sequenceNumber", Query.SortDirection.ASCENDING);

    }
}

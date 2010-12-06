package nl.gridshore.companyhr.app.axon;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.SimpleDomainEventStream;
import org.axonframework.eventstore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jettro Coenradie
 */
public class ObjectifyEventStore implements SnapshotEventStore, EventStoreManagement {
    private static final Logger logger = LoggerFactory.getLogger(ObjectifyEventStore.class);
    private static final int EVENT_VISITOR_BATCH_SIZE = 50;

    private final EventSerializer eventSerializer;
    private final ObjectifyFactory objectifyFactory;

    public ObjectifyEventStore(EventSerializer eventSerializer, ObjectifyFactory objectifyFactory) {
        this.eventSerializer = eventSerializer;
        this.objectifyFactory = objectifyFactory;
    }

    public ObjectifyEventStore(ObjectifyFactory objectifyFactory) {
        this(new XStreamEventSerializer(), objectifyFactory);
    }

    @PostConstruct
    public void registerEntityClasses() {
        objectifyFactory.register(EventEntry.class);
    }

    public void appendEvents(String type, DomainEventStream events) {
        List<EventEntry> entries = new ArrayList<EventEntry>();
        while (events.hasNext()) {
            DomainEvent event = events.next();
            EventEntry entry = new EventEntry(type, event, eventSerializer);
            entries.add(entry);
        }
        Objectify objectify = objectifyFactory.beginTransaction();
        try {
            objectify.put(entries);
            objectify.getTxn().commit();
        } finally {
            if (objectify.getTxn().isActive()) {
                logger.info("Transaction to commit new events is rolled back because");
                objectify.getTxn().rollback();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} events of type {} appended", new Object[]{entries.size(), type});
                }
            }
        }
    }

    public DomainEventStream readEvents(String type, AggregateIdentifier identifier) {
        StringBuilder sb = new StringBuilder(250);
        long start = new Date().getTime();

        long snapshotSequenceNumber = -1;
        EventEntry lastSnapshotEvent = loadLastSnapshotEvent(type, identifier);
        if (lastSnapshotEvent != null) {
            snapshotSequenceNumber = lastSnapshotEvent.getSequenceNumber();
        }

        sb.append("snapshot : ").append(new Date().getTime() - start);

        List<DomainEvent> events = readEventSegmentInternal(type, identifier, snapshotSequenceNumber + 1);
        if (lastSnapshotEvent != null) {
            events.add(0, lastSnapshotEvent.getDomainEvent(eventSerializer));
        }

        sb.append(", event : ").append(new Date().getTime() - start);
        if (events.isEmpty()) {
            throw new EventStreamNotFoundException(type, identifier);
        }
        SimpleDomainEventStream simpleDomainEventStream = new SimpleDomainEventStream(events);

        sb.append(", serialize : ").append(new Date().getTime() - start);
        return simpleDomainEventStream;
    }

    public void visitEvents(EventVisitor visitor) {
        int first = 0;
        List<EventEntry> batch;
        boolean shouldContinue = true;
        while (shouldContinue) {
            batch = fetchBatch(first, EVENT_VISITOR_BATCH_SIZE);
            for (EventEntry entry : batch) {
                visitor.doWithEvent(entry.getDomainEvent(eventSerializer));
            }
            shouldContinue = (batch.size() >= EVENT_VISITOR_BATCH_SIZE);
            first += EVENT_VISITOR_BATCH_SIZE;
        }
    }

    public void appendSnapshotEvent(String type, DomainEvent snapshotEvent) {
        EventEntry snapshotEventEntry = new EventEntry(type, snapshotEvent, eventSerializer);
        Objectify objectify = objectifyFactory.beginTransaction();
        try {
            objectify.put(snapshotEventEntry);
            objectify.getTxn().commit();
        } finally {
            if (objectify.getTxn().isActive()) {
                logger.info("Transaction to commit new events is rolled back because");
                objectify.getTxn().rollback();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("1 snapshot event of type {} appended", type);
                }
            }
        }
    }

    private List<DomainEvent> readEventSegmentInternal(String type, AggregateIdentifier identifier,
                                                       long firstSequenceNumber) {
        Objectify objectify = objectifyFactory.begin();

        Query<EventEntry> query = objectify.query(EventEntry.class)
                .filter("aggregateIdentifier", identifier)
                .filter("sequenceNumber >", firstSequenceNumber)
                .filter("aggregateType", type);
        List<EventEntry> eventEntries = query.list();

        List<DomainEvent> events = new ArrayList<DomainEvent>(eventEntries.size());
        for(EventEntry entry : eventEntries) {
            DomainEvent deserialize = eventSerializer.deserialize(entry.getSerializedEvent().getBytes(EventEntry.UTF8));
            events.add(deserialize);
        }
        return events;
    }

    private EventEntry loadLastSnapshotEvent(String type, AggregateIdentifier identifier) {
        // hier moet ik het laatste snapshot event vinden
        return null;
    }

    private List<EventEntry> fetchBatch(int startPosition, int batchSize) {
        return null;
//        DBObject sort = BasicDBObjectBuilder.start()
//                .add(org.axonframework.eventstore.mongo.EventEntry.TIME_STAMP_PROPERTY, -1)
//                .add(org.axonframework.eventstore.mongo.EventEntry.SEQUENCE_NUMBER_PROPERTY, -1)
//                .get();
//        DBCursor batchDomainEvents = mongoTemplate.domainEventCollection().find().sort(sort).limit(batchSize).skip(
//                startPosition);
//        List<org.axonframework.eventstore.mongo.EventEntry> entries = new ArrayList<org.axonframework.eventstore.mongo.EventEntry>();
//        while (batchDomainEvents.hasNext()) {
//            DBObject dbObject = batchDomainEvents.next();
//            entries.add(new org.axonframework.eventstore.mongo.EventEntry(dbObject));
//        }
//        return entries;
    }

}

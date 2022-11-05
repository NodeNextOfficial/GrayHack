package org.grayhack.eventbus;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;
import org.grayhack.event.Event;
import org.grayhack.eventbus.handler.EventHandler;

public class GrayEventBus {

	private final EventHandler handler;
	private final AtomicLong eventsPosted = new AtomicLong();
	
	private final Logger logger;

	public GrayEventBus(EventHandler handler, Logger logger) {
		this.handler = handler;
		this.logger = logger;
	}

	public boolean subscribe(Object object) {
		return handler.subscribe(object);
	}

	public boolean unsubscribe(Object object) {
		return handler.unsubscribe(object);
	}

	public void post(Event event) {
		handler.post(event, logger);
		eventsPosted.getAndIncrement();
	}

	public long getEventsPosted() {
		return eventsPosted.get();
	}
}

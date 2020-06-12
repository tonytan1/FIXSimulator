/*
 * File     : LogMessageSet.java
 *
 
 * 
 * Contents : This class is a Set of LogMessage objects with utility 
 *            methods to access the individual messages.
 */

package com.quickfix.simulator.core;

import java.util.ArrayList;

import quickfix.DataDictionary;
import quickfix.Message;
import quickfix.SessionID;


public class LogMessageSet {
	private ArrayList<LogMessage> messages = null;
	private int messageIndex = 0;
	
	public LogMessageSet() {
		messages = new ArrayList<LogMessage>();
	}
	
	public void add(Message message, boolean incoming, 
                DataDictionary dictionary, SessionID sessionID) {
		messageIndex++;
		LogMessage msg = 
                        new LogMessage(messageIndex, incoming, sessionID,
				message.toString(), dictionary);
                messages.add(msg);
                int limit = 50;
                try {
                    limit = (int)FIXimulator.getApplication().getSettings()
                            .getLong("FIXimulatorCachedObjects");
                } catch ( Exception e ) {}
                while ( messages.size() > limit ) {
                    messages.remove(0);
                }
		//call back to the model to update
	}
	
	public void update () {
    }

	public LogMessage getMessage( int i ) {
		return messages.get(i);
	}
	
	public int getCount(){
		return messages.size();
	}
}

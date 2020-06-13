/*
 * File     : FIXimulator.java
 *
 * Contents : This is the class that initializes the application.
 * 
 */

package com.quickfix.simulator.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Acceptor;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.ScreenLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

public class FIXimulator {
    private Acceptor acceptor = null;
    private static FIXimulatorApplication application = null;
    private static InstrumentSet instruments = null;
    private static LogMessageSet messages = null;

    private static Logger logger = LoggerFactory.getLogger(FIXimulator.class);
    
    public FIXimulator() {
        InputStream inputStream = null;
        try {
            File fixCfg = new File("./config/FIXimulator.cfg");
            logger.info(fixCfg.getAbsolutePath());

            inputStream = new BufferedInputStream(new FileInputStream(fixCfg));
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
        File file = new File("./config/instruments.xml");
        instruments = new InstrumentSet(file);
        messages = new LogMessageSet();
        try {
            SessionSettings settings = new SessionSettings(inputStream);
            application = new FIXimulatorApplication(settings, messages);
            MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new ScreenLogFactory(settings);
            
            MessageFactory messageFactory = new DefaultMessageFactory();
            acceptor = new SocketAcceptor(application, messageStoreFactory, settings, logFactory, messageFactory);
        } catch (ConfigError e) {
            e.printStackTrace();
        }
    }

    public static InstrumentSet getInstruments() {
        return instruments;
    }

    public static FIXimulatorApplication getApplication() {
        return application;
    }

    public static LogMessageSet getMessageSet() {
        return messages;
    }

    public void start() {
        try {
            acceptor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

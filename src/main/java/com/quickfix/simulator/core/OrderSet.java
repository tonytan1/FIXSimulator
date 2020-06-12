/*
 * File     : OrderSet.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a Set of Order objects with a utility 
 *            methods to access the individual orders.
 */

package com.quickfix.simulator.core;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderSet {
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Order> ordersToFill = new ArrayList<Order>();

    public OrderSet() {}
    
    public void add ( Order order, boolean toFill ) {
        orders.add( order );
        if ( toFill ) ordersToFill.add(order);
        int limit = 50;
        try {
            limit = (int)FIXimulator.getApplication().getSettings()
                    .getLong("FIXimulatorCachedObjects");
        } catch ( Exception e ) {}
        while ( orders.size() > limit ) {
            orders.remove(0);
        }
    }
    
    public void update () {
    }

        
    public int getCount() {
        return orders.size();
    }

    public Order getOrder( int i ) {
        return orders.get( i );
    }

    public Order getOrder( String id ) {
        Iterator<Order> iterator = orders.iterator();
        while ( iterator.hasNext() ){
            Order order = iterator.next();
            if ( order.getID().equals(id) || order.getClientID().equals(id))
                return order;
        }
        return null;
    }
    
    public boolean haveOrdersToFill() {
        if ( ordersToFill.size() > 0 ) return true;
        return false;
    }
    
    public Order getOrderToFill() {
        return ordersToFill.remove(0);
    }
}

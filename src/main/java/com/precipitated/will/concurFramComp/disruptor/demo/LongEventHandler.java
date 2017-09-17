package com.precipitated.will.concurFramComp.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * Created by will on 17/9/17.
 */
public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event);
    }
}
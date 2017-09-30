package com.precipitated.will.concurFramComp.disruptor.experiment;

import com.lmax.disruptor.EventFactory;

/**
 * Created by will on 17/9/17.
 */
public class LongEventFactory implements EventFactory<LongEvent>
{
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}
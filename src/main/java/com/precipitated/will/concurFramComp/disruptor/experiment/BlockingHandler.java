package com.precipitated.will.concurFramComp.disruptor.experiment;

import com.lmax.disruptor.EventHandler;

/**
 * @author wangwei02@corp.netease.com
 * @since 2017/9/21
 */
public class BlockingHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}

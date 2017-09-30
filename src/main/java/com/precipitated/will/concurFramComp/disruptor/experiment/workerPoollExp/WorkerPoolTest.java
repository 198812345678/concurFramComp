package com.precipitated.will.concurFramComp.disruptor.experiment.workerPoollExp;

import com.lmax.disruptor.*;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author wangwei02@corp.netease.com
 * @since 2017/9/26
 */
public class WorkerPoolTest {


    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessEachMessageByOnlyOneWorker() throws Exception
    {
        Executor executor = Executors.newCachedThreadPool(DaemonThreadFactory.INSTANCE);
        WorkerPool<AtomicLong> pool = new WorkerPool<AtomicLong>(
                new AtomicLongEventFactory(), new FatalExceptionHandler(),
                new AtomicLongWorkHandler(0), new AtomicLongWorkHandler(1));

        RingBuffer<AtomicLong> ringBuffer = pool.start(executor);

        ringBuffer.next();
        ringBuffer.next();
        ringBuffer.publish(0);
        ringBuffer.publish(1);

        Thread.sleep(500);

        assertThat(ringBuffer.get(0).get(), is(1L));
        assertThat(ringBuffer.get(1).get(), is(1L));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessOnlyOnceItHasBeenPublished() throws Exception
    {
        Executor executor = Executors.newCachedThreadPool(DaemonThreadFactory.INSTANCE);
        WorkerPool<AtomicLong> pool = new WorkerPool<AtomicLong>(
                new AtomicLongEventFactory(), new FatalExceptionHandler(),
                new AtomicLongWorkHandler(0), new AtomicLongWorkHandler(1));

        RingBuffer<AtomicLong> ringBuffer = pool.start(executor);

        ringBuffer.next();
        ringBuffer.next();

        Thread.sleep(1000);

        assertThat(ringBuffer.get(0).get(), is(0L));
        assertThat(ringBuffer.get(1).get(), is(0L));
    }

    private static class AtomicLongWorkHandler implements WorkHandler<AtomicLong>
    {

        int i;

        public AtomicLongWorkHandler(int i) {
            this.i = i;
        }

        @Override
        public void onEvent(AtomicLong event) throws Exception
        {
            System.out.println("worker " + i + "handle event " + event);
            event.incrementAndGet();
        }
    }


    private static class AtomicLongEventFactory implements EventFactory<AtomicLong>
    {
        @Override
        public AtomicLong newInstance()
        {
            return new AtomicLong(0);
        }
    }


}

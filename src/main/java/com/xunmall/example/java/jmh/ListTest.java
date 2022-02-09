package com.xunmall.example.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wangyanjing
 * @date 2020/2/3
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ListTest {

    CopyOnWriteArrayList smallCopyOnWriteList = new CopyOnWriteArrayList();
    ConcurrentLinkedQueue smallConcurrentLinkedQueue = new ConcurrentLinkedQueue();
    CopyOnWriteArrayList bigCopyOnWriteList = new CopyOnWriteArrayList();
    ConcurrentLinkedQueue bigConcurrentLinkedQueue = new ConcurrentLinkedQueue();

    @Setup
    public void setup(){
        for (int i = 0; i < 10; i++){
            smallCopyOnWriteList.add(new Object());
            smallConcurrentLinkedQueue.add(new Object());
        }
        for (int i = 0; i < 1000; i++){
            bigCopyOnWriteList.add(new Object());
            bigConcurrentLinkedQueue.add(new Object());
        }
    }

    @Benchmark
    public void copyOnWriteGet(){
        smallCopyOnWriteList.get(0);
    }

    @Benchmark
    public void copyOnWriteSize(){
        smallCopyOnWriteList.size();
    }

    @Benchmark
    public void concurrentListGet(){
        smallConcurrentLinkedQueue.peek();
    }

    @Benchmark
    public void concurrentListSize(){
        smallConcurrentLinkedQueue.size();
    }

    @Benchmark
    public void smallCopyOnWriteWrite(){
        smallCopyOnWriteList.add(new Object());
        smallCopyOnWriteList.remove(0);
    }

    @Benchmark
    public void smalllConcurrentListWrite(){
        smallConcurrentLinkedQueue.add(new Object());
        smallConcurrentLinkedQueue.remove(0);
    }

    @Benchmark
    public void bigCopyOnWriteWrite(){
        bigCopyOnWriteList.add(new Object());
        bigCopyOnWriteList.remove(0);
    }

    @Benchmark
    public void bigConcurrentListWrite(){
        bigConcurrentLinkedQueue.offer(new Object());
        bigConcurrentLinkedQueue.remove(0);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(ListTest.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }

}

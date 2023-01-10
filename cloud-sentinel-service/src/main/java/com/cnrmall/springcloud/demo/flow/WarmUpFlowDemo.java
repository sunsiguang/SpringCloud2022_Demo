package com.cnrmall.springcloud.demo.flow;

/**
 * @author David
 * @date 2023/1/3 17:48
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.csp.sentinel.util.TimeUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * When {@link FlowRule#controlBehavior} set to {@link RuleConstant#CONTROL_BEHAVIOR_WARM_UP}, real passed qps will
 * gradually increase to {@link FlowRule#count}, other than burst increasing.
 * <p/>
 * Run this demo, results are as follows:
 * <pre>
 * ...
 * 1530497805902, total:1, pass:1, block:0 // run in slow qps
 * 1530497806905, total:3, pass:3, block:0
 * 1530497807909, total:2, pass:2, block:0
 * 1530497808913, total:3, pass:3, block:0
 * 1530497809917, total:269, pass:6, block:263 // request qps burst increase, warm up behavior triggered.
 * 1530497810917, total:3676, pass:7, block:3669
 * 1530497811919, total:3734, pass:9, block:3725
 * 1530497812920, total:3692, pass:9, block:3683
 * 1530497813923, total:3642, pass:10, block:3632
 * 1530497814926, total:3685, pass:10, block:3675
 * 1530497815930, total:3671, pass:11, block:3660
 * 1530497816933, total:3660, pass:15, block:3645
 * 1530497817936, total:3681, pass:21, block:3661 // warm up process end, pass qps increased to {@link FlowRule#count}
 * 1530497818940, total:3737, pass:20, block:3716
 * 1530497819945, total:3663, pass:20, block:3643
 * 1530497820950, total:3723, pass:21, block:3702
 * 1530497821954, total:3680, pass:20, block:3660
 * ...
 * </pre>
 *
 * @author jialiang.linjl
 *
 * Warm Up（RuleConstant.CONTROL_BEHAVIOR_WARM_UP）方式，即预热/冷启动方式。当系统长期处于低水位的情况下，当流量突然增加时，直接把系统拉升到高水位可能瞬间把系统压垮。通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮。
 * 详细文档可以参考 流量控制 - Warm Up 文档，具体的例子可以参见 WarmUpFlowDemo。
 *  com.alibaba.csp.sentinel.slots.block.flow.controller.WarmUpController
 */
public class WarmUpFlowDemo {

    private static final String KEY = "abc";

    private static AtomicInteger pass = new AtomicInteger();
    private static AtomicInteger block = new AtomicInteger();
    private static AtomicInteger total = new AtomicInteger();

    private static volatile boolean stop = false;

    private static final int threadCount = 100;
    private static int seconds = 60 + 40;

    public static void main(String[] args) throws Exception {
        initFlowRule();
        // trigger Sentinel internal init
        Entry entry = null;
        try {
            entry = SphU.entry(KEY);
        } catch (Exception e) {
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

        Thread timer = new Thread(new TimerTask());
        timer.setName("sentinel-timer-task");
        timer.start();

        //first make the system run on a very low condition
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new WarmUpTask());
            t.setName("sentinel-warmup-task");
            t.start();
        }
        Thread.sleep(20000);

        /*
         * Start more thread to simulate more qps. Since we use {@link RuleConstant.CONTROL_BEHAVIOR_WARM_UP} as
         * {@link FlowRule#controlBehavior}, real passed qps will increase to {@link FlowRule#count} in
         * {@link FlowRule#warmUpPeriodSec} seconds.
         */
        for (int i = 0; i < threadCount; i++) {
            Thread t = new Thread(new RunTask());
            t.setName("sentinel-run-task");
            t.start();
        }
    }

    private static void initFlowRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(KEY);
        rule1.setCount(20);  //单机阈值
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);  //流控效果：冷加载
        rule1.setWarmUpPeriodSec(10);  //预热时长

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    static class WarmUpTask implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                Entry entry = null;
                try {
                    entry = SphU.entry(KEY);
                    // token acquired, means pass
                    pass.addAndGet(1);
                } catch (BlockException e1) {
                    block.incrementAndGet();
                } catch (Exception e2) {
                    // biz exception
                } finally {
                    total.incrementAndGet();
                    if (entry != null) {
                        entry.exit();
                    }
                }
                Random random2 = new Random();
                try {
                    TimeUnit.MILLISECONDS.sleep(random2.nextInt(2000));
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }

    static class RunTask implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                Entry entry = null;
                try {
                    entry = SphU.entry(KEY);
                    pass.addAndGet(1);
                } catch (BlockException e1) {
                    block.incrementAndGet();
                } catch (Exception e2) {
                    // biz exception
                } finally {
                    total.incrementAndGet();
                    if (entry != null) {
                        entry.exit();
                    }
                }
                Random random2 = new Random();
                try {
                    TimeUnit.MILLISECONDS.sleep(random2.nextInt(50));
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }

    static class TimerTask implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            System.out.println("begin to statistic!!!");
            long oldTotal = 0;
            long oldPass = 0;
            long oldBlock = 0;
            while (!stop) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }

                long globalTotal = total.get();
                long oneSecondTotal = globalTotal - oldTotal;
                oldTotal = globalTotal;

                long globalPass = pass.get();
                long oneSecondPass = globalPass - oldPass;
                oldPass = globalPass;

                long globalBlock = block.get();
                long oneSecondBlock = globalBlock - oldBlock;
                oldBlock = globalBlock;

                System.out.println(TimeUtil.currentTimeMillis() + ", total:" + oneSecondTotal
                        + ", pass:" + oneSecondPass
                        + ", block:" + oneSecondBlock);
                if (seconds-- <= 0) {
                    stop = true;
                }
            }

            long cost = System.currentTimeMillis() - start;
            System.out.println("time cost: " + cost + " ms");
            System.out.println("total:" + total.get() + ", pass:" + pass.get()
                    + ", block:" + block.get());
            System.exit(0);
        }
    }
}

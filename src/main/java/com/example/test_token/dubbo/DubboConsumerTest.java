package com.example.test_token.dubbo;

import api.DubboTestService;
import bo.TellerBO;
import com.alibaba.dubbo.config.annotation.Reference;

import com.example.test_token.util.DateUtil;
import com.test.wb.test.api.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-19
 */

@RestController
@RequestMapping("/open/test/dubbo")
@Slf4j
public class DubboConsumerTest {

    @Reference
    private DubboTestService dubboTestService;

    @Reference
    private LikeService likeService;

    @GetMapping
    public String sayHello() {
        TellerBO tellerBO = dubboTestService.queryDubboService();
        return "ddd";
    }

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10000);

    @GetMapping("/like")
    public String like(Integer uid, Integer statusid) throws InterruptedException {
        log.info(String.valueOf(uid) + ";" + String.valueOf(statusid));

        Thread.sleep(5000);
        log.info(DateUtil.now());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10000);
        for (int j = 0; j < 10000; j++) {
            threadPool.execute(() -> {
                try {

                    StringBuilder stringUidBuilder = new StringBuilder();

                    for (int i = 0; i < 9; i++) {
                        int some = new Random().nextInt(9);
                        stringUidBuilder.append(some);
                    }
                    int nowStatusid = 1000000010;
                    cyclicBarrier.await();
                    likeService.like(Integer.valueOf(stringUidBuilder.toString()), nowStatusid);

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });
        }


        log.info(DateUtil.now());


        return "ok";
    }

    @GetMapping("/likeone")
    public String likeone(Integer uid, Integer statusid) {
        log.info(String.valueOf(uid) + ";" + String.valueOf(statusid));


        log.info(DateUtil.now());

        int nowStatusid = 1000000010;
        StringBuilder stringUidBuilder = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            int some = new Random().nextInt(9);
            stringUidBuilder.append(some);
        }
        likeService.like(Integer.valueOf(stringUidBuilder.toString()), nowStatusid);




        log.info(DateUtil.now());


        return "ok";
    }

    @GetMapping("/isLike")
    public String isLiked(Integer uid, Integer statusid) {
        log.info(String.valueOf(uid) + ";" + String.valueOf(statusid));
        likeService.isLiked(uid, statusid);
        return "ok";
    }


}

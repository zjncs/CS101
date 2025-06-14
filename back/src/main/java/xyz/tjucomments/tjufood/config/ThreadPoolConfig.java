package xyz.tjucomments.tjufood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean
    @Qualifier("cacheRebuildExecutor")
    public ExecutorService cacheRebuildExecutor() {
        return new ThreadPoolExecutor(
                10, // 核心线程数
                20, // 最大线程数
                60L, // 空闲线程存活时间
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100) // 任务队列
        );
    }
}

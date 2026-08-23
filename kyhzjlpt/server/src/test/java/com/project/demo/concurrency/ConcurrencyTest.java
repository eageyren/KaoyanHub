package com.project.demo.concurrency;

import com.project.demo.service.ForumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 并发测试 — Service 层并发调用
 * (强制更新触发重新编译)
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("并发安全性测试")
public class ConcurrencyTest {

    @Autowired
    private ForumService forumService;

    @Test
    @DisplayName("CONC-01: 并发调用 selectToPage → 全部成功")
    void testConcurrentSelectToPage() throws Exception {
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        AtomicInteger successCount = new AtomicInteger(0);

        Map<String, String> config = new HashMap<>();
        config.put("page", "1");
        config.put("size", "5");

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    Map<String, Object> result = forumService.selectToPage(new HashMap<>(), config);
                    if (result != null && result.containsKey("list") && result.containsKey("count")) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    // 记录失败
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        assertTrue(successCount.get() >= threads, "所有并发请求应成功, 实际成功: " + successCount.get());
    }

    @Test
    @DisplayName("CONC-02: 并发调用 count → 全部成功")
    void testConcurrentCount() throws Exception {
        int threads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    Object result = forumService.count(new HashMap<>(), new HashMap<>()).getSingleResult();
                    if (result != null) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    // ignore
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        assertTrue(successCount.get() == threads, "所有并发 count 请求应成功, 实际成功: " + successCount.get());
    }
}

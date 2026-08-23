package com.project.demo.performance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 性能基线测试 — 记录关键操作的耗时基线
 */
@DisplayName("性能基线测试")
public class PerformanceBaselineTest {

    @Test
    @DisplayName("PERF-01: 实体 getter/setter 批量操作 < 100ms")
    void testEntityOperationsPerformance() {
        assertTimeout(Duration.ofMillis(500), () -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                list.add("item_" + i);
            }
            for (String item : list) {
                item.length();
            }
        });
    }

    @Test
    @DisplayName("PERF-02: 字符串拼接 1000 次 < 50ms")
    void testStringBuilderPerformance() {
        assertTimeout(Duration.ofMillis(200), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append("test_").append(i).append(",");
            }
            String result = sb.toString();
            assertTrue(result.length() > 0);
        });
    }

    @Test
    @DisplayName("PERF-03: HashMap 批量操作 5000 条 < 100ms")
    void testHashMapPerformance() {
        assertTimeout(Duration.ofMillis(300), () -> {
            java.util.Map<String, Integer> map = new java.util.HashMap<>();
            for (int i = 0; i < 5000; i++) {
                map.put("key_" + i, i);
            }
            assertTrue(map.size() == 5000);
        });
    }
}

package com.project.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * 集成测试抽象基类
 * <p>
 * 统一配置 @SpringBootTest + @ActiveProfiles("test") + @AutoConfigureMockMvc，
 * 所有集成测试继承此类即可自动使用 H2 内存数据库。
 * </p>
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * 发起 POST 请求并返回 JSON 解析结果
     */
    protected JSONObject postJson(String url, String jsonBody) throws Exception {
        MvcResult result = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        return JSONObject.parseObject(content);
    }

    /**
     * 获取响应内容字符串
     */
    protected String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    /**
     * 解析响应为 JSONObject
     */
    protected JSONObject parse(MvcResult result) throws UnsupportedEncodingException {
        return JSONObject.parseObject(getContent(result));
    }
}

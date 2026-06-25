package com.project.demo.controller;

import com.project.demo.entity.OnlineQuestions;
import com.project.demo.service.OnlineQuestionsService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 在线提问：(OnlineQuestions)表控制层
 *
 */
@RestController
@RequestMapping("/online_questions")
public class OnlineQuestionsController extends BaseController<OnlineQuestions, OnlineQuestionsService> {

    /**
     * 在线提问对象
     */
    @Autowired
    public OnlineQuestionsController(OnlineQuestionsService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        return success(1);
    }

}

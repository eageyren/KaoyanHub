package com.project.demo.controller;

import com.project.demo.entity.OnlineQa;
import com.project.demo.service.OnlineQaService;
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
 * 在线答疑：(OnlineQa)表控制层
 *
 */
@RestController
@RequestMapping("/online_qa")
public class OnlineQaController extends BaseController<OnlineQa, OnlineQaService> {

    /**
     * 在线答疑对象
     */
    @Autowired
    public OnlineQaController(OnlineQaService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        Map<String, String> mapquestion_no = new HashMap<>();
        mapquestion_no.put("question_no",String.valueOf(paramMap.get("question_no")));
        List listquestion_no = service.select(mapquestion_no, new HashMap<>()).getResultList();
        if (listquestion_no.size()>0){
            return error(30000, "字段问题编号内容不能重复");
        }
        this.addMap(paramMap);
        return success(1);
    }

}

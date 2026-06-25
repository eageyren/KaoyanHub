package com.project.demo.controller;

import com.project.demo.entity.CollegesAndUniversities;
import com.project.demo.service.CollegesAndUniversitiesService;
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
 * 报考院校：(CollegesAndUniversities)表控制层
 *
 */
@RestController
@RequestMapping("/colleges_and_universities")
public class CollegesAndUniversitiesController extends BaseController<CollegesAndUniversities, CollegesAndUniversitiesService> {

    /**
     * 报考院校对象
     */
    @Autowired
    public CollegesAndUniversitiesController(CollegesAndUniversitiesService service) {
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

package com.project.demo.controller;

import com.project.demo.entity.DataType;
import com.project.demo.service.DataTypeService;
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
 * 资料类型：(DataType)表控制层
 *
 */
@RestController
@RequestMapping("/data_type")
public class DataTypeController extends BaseController<DataType, DataTypeService> {

    /**
     * 资料类型对象
     */
    @Autowired
    public DataTypeController(DataTypeService service) {
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

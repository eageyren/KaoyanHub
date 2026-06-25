package com.project.demo.controller;

import com.project.demo.entity.DataSharing;
import com.project.demo.service.DataSharingService;
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
 * 资料分享：(DataSharing)表控制层
 *
 */
@RestController
@RequestMapping("/data_sharing")
public class DataSharingController extends BaseController<DataSharing, DataSharingService> {

    /**
     * 资料分享对象
     */
    @Autowired
    public DataSharingController(DataSharingService service) {
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

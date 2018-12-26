package com.yxbkj.yxb.system.controller;

import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Sensitive")

public class SensitiveController {
    @Autowired
    private SensitiveService sensitiveService;

    @GetMapping("/getSensitive")
    public Result<Map<String, Object>> page(String content){
        return sensitiveService.str(content);
    }
}

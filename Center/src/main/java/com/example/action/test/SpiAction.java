package com.example.action.test;

import entity.code.statuscode.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/23 9:48
 * @Tags
 **/
@RestController
@RequestMapping("/test/spi")
public class SpiAction {

    @GetMapping("/by/{name}")
    public ResultVo getByName(@PathVariable("username") String username) {

        return null;
    }
}

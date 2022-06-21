package com.example.action.product;

import entity.ProductionInfoVo;
import entity.code.statuscode.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/21 10:33
 * @Tags
 **/
@RestController
@RequestMapping("/product/product-info")
public class ProductAction {

    @GetMapping("/get/production")
    public ResultVo getProductionInfo(@Validated ProductionInfoVo productionInfoVo) {
        ProductionInfoVo vo = new ProductionInfoVo();
        return new ResultVo(vo);
    }

}

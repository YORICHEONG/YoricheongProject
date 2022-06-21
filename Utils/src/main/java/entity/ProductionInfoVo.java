package entity;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/21 9:40
 * @Tags
 **/
@Data
@Validated
public class ProductionInfoVo extends BaseInfoVo {

    @NotNull(message = "产品名称不可以为空")
    private String productName;

    @Min(value=0, message = "产品的价格不可以为空")
    private BigDecimal productPrice;

    private Integer productStatus;
}

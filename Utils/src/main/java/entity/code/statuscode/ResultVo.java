package entity.code.statuscode;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/21 9:37
 * @Tags
 **/
@Data
@Getter
@Setter
public class ResultVo {

    private int code;

    private String message;

    private Object data;

    public ResultVo(Object data) {
        this.code = ResultCode.SUCESS.getCode();
        this.message = ResultCode.SUCESS.getMessage();
        this.data = data;
    }

    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

}

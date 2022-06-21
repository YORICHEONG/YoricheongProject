package entity.code.statuscode;

import lombok.Getter;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/21 9:33
 * @Tags
 **/
@Getter
public enum ResultCode implements StatusCode{

    SUCESS(200, "请求成功"),
    FAILURE(400, "请求失败"),
    VALIDATE_ERROR(300, "验证失败");


    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

}

package com.example.advice;

import entity.code.statuscode.ResultCode;
import entity.code.statuscode.ResultVo;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/21 17:55
 * @Tags
 **/
@RestControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler({BindException.class})
    public ResultVo MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultVo(ResultCode.VALIDATE_ERROR.getCode(), objectError.getDefaultMessage());
    }
}

package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.common_utils.R;
import com.atguigu.servicebase.handler.GuliExpection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
//异常处理类
@Slf4j

public class GlobalExcpetionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody  //为了能返回数据
    //全局异常处理
    public R error(Exception e){
        e.printStackTrace();
        return  R.error().message("执行了全局的异常处理111");
    }
    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了能返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return  R.error().message("执行了ArithmeticExpection异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliExpection.class)
    @ResponseBody //为了能返回数据
    public R error(GuliExpection e){
        e.printStackTrace();
        log.error(e.getMsg());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}

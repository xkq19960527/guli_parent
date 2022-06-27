package com.atguigu.servicebase.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliExpection extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
}

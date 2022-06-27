package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.handler.GuliExpection;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.awt.*;
import java.util.List;
import com.atguigu.common_utils.*;



/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-05-14
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@Component
@CrossOrigin


public class EduTeacherController {
    //
    //把service注入
    @Autowired
     private EduTeacherService teacherService;

    //1.查询讲师表中的所有数据
    //rest风格
    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAllTeacher(){
        //调用service的方法实现查询的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2.逻辑删除讲师的方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    //3.分页查询讲师方法
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total",total).data("row",records);

    }

    //4.条件查询加分页功能
    @ApiOperation(value = "按条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody (required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher =new Page<>(current,limit);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //用动态sql的方法来判断条件值是否为空，如果不为空拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //构建条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified",end);
        }

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total",total).data("row",records);

    }

    //添加讲师接口
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据讲师ID进行查询
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        try{
            /*int i=10/0;*/
        }catch(Exception e){
            throw new GuliExpection(20001,"执行了GuliExpection自定义异常");
        }

        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
        public R updateTeacher(@RequestBody EduTeacher eduTeacher){
            boolean b = teacherService.updateById(eduTeacher);
            if(b){
                return R.ok();
            }else{
                return R.error();
            }
        }


}


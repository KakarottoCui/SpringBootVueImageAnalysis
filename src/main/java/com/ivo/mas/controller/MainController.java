package com.ivo.mas.controller;

import com.ivo.mas.pojo.PicCutItem;
import com.ivo.mas.service.PicCutItemService;
import com.ivo.mas.service.MainService;
import com.ivo.mas.system.ResponseFormat.BaseResponse;
import com.ivo.mas.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@BaseResponse
public class MainController {
    @Resource
    MainService mainService;

    /**
     * 切分图片
     */
    @RequestMapping("/cutPic")
    @ResponseBody
    public ResponseResult<Object> cutPic(@RequestParam("file") MultipartFile file,@RequestParam("data") String data,@RequestParam("limit") Double limit){
        return mainService.cutPic(file,data,limit);
    }

    @RequestMapping("/pic/{picId}/{type}")
    @ResponseBody
    public void signPicReceive(@PathVariable Integer picId,@PathVariable String type, HttpServletRequest request, HttpServletResponse response){
        mainService.getPicById(picId,type,request,response);
    }

    @RequestMapping("/getHis")
    @ResponseBody
    public ResponseResult<Object> getHis(){
       return mainService.getHis();
    }
}

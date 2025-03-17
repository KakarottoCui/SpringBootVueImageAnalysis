package com.ivo.mas.controller;
import com.ivo.mas.service.PicMainService;
import com.ivo.mas.pojo.PicMain;
import com.ivo.mas.system.ResponseFormat.BaseResponse;
import com.ivo.mas.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/picMain")
public class PicMainController {

    @Resource
    PicMainService picMainService;
    
    /**
     * 查询多条数据
     *
     * @param picMain 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> queryPicMainList(@RequestBody PicMain picMain){
        return picMainService.queryPicMainList(picMain);
    }
    /**
     * 查询一条数据
     *
     * @param picMain 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> queryPicMainObject(@RequestBody PicMain picMain){
        return picMainService.queryPicMainObject(picMain);
    }
    /**
     * 新增一条数据
     *
     * @param picMain 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addPicMain")
    @ResponseBody
    public ResponseResult<Object> addPicMain(@RequestBody PicMain picMain){
        return picMainService.addPicMain(picMain);
    }
    /**
     * 修改一条数据
     *
     * @param picMain 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editPicMain")
    @ResponseBody
    public ResponseResult<Object> editPicMain(@RequestBody PicMain picMain){
        return picMainService.editPicMain(picMain);
    }
    
}

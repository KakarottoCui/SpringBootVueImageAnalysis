package com.ivo.mas.controller;
import com.ivo.mas.service.PicCutItemService;
import com.ivo.mas.pojo.PicCutItem;
import com.ivo.mas.system.ResponseFormat.BaseResponse;
import com.ivo.mas.system.ResponseFormat.ResponseResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/picCutItem")
public class PicCutItemController {

    @Resource
    PicCutItemService picCutItemService;
    
    /**
     * 查询多条数据
     *
     * @param picCutItem 查询条件
     * @return 对象列表
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public ResponseResult<Object> queryPicCutItemList(@RequestBody PicCutItem picCutItem){
        return picCutItemService.queryPicCutItemList(picCutItem);
    }
    /**
     * 查询一条数据
     *
     * @param picCutItem 查询条件
     * @return 对象
     */
    @RequestMapping("/queryObject")
    @ResponseBody
    public ResponseResult<Object> queryPicCutItemObject(@RequestBody PicCutItem picCutItem){
        return picCutItemService.queryPicCutItemObject(picCutItem);
    }
    /**
     * 新增一条数据
     *
     * @param picCutItem 新增数据实体类
     * @return 新增对象
     */
    @RequestMapping("/addPicCutItem")
    @ResponseBody
    public ResponseResult<Object> addPicCutItem(@RequestBody PicCutItem picCutItem){
        return picCutItemService.addPicCutItem(picCutItem);
    }
    /**
     * 修改一条数据
     *
     * @param picCutItem 修改数据实体类
     * @return 修改后对象
     */
    @RequestMapping("/editPicCutItem")
    @ResponseBody
    public ResponseResult<Object> editPicCutItem(@RequestBody PicCutItem picCutItem){
        return picCutItemService.editPicCutItem(picCutItem);
    }
    
}

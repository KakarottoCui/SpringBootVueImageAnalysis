package com.ivo.mas.service;

import com.ivo.mas.mapper.PicCutItemMapper;
import com.ivo.mas.pojo.PicCutItem;

import com.ivo.mas.system.ResponseFormat.ResponseResult;
import com.ivo.mas.system.ResponseFormat.ResponseCode;
import com.ivo.mas.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class PicCutItemService {

    @Resource
    private PicCutItemMapper picCutItemMapper;
    
    /**
     * 查询多条数据
     *
     * @param picCutItem 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> queryPicCutItemList(PicCutItem picCutItem) {
        QueryWrapper<PicCutItem> queryWrapper = new QueryWrapper<PicCutItem>(picCutItem);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",picCutItemMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param picCutItem 查询条件
     * @return 对象
     */
    public ResponseResult<Object> queryPicCutItemObject(PicCutItem picCutItem) {
        QueryWrapper<PicCutItem> queryWrapper = new QueryWrapper<PicCutItem>(picCutItem);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",picCutItemMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param picCutItem 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addPicCutItem(PicCutItem picCutItem) {
        picCutItem.setId(null);
        picCutItem.setValidFlag(1);
        picCutItem.setCreateTime(new Date());
        picCutItemMapper.insert(picCutItem);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",picCutItem);
    }
    
    /**
     * 修改一条数据
     *
     * @param picCutItem 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editPicCutItem(PicCutItem picCutItem) {
        picCutItemMapper.updateById(picCutItem);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",picCutItem);
    }

}

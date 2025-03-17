package com.ivo.mas.service;

import com.ivo.mas.mapper.PicMainMapper;
import com.ivo.mas.pojo.PicMain;

import com.ivo.mas.system.ResponseFormat.ResponseResult;
import com.ivo.mas.system.ResponseFormat.ResponseCode;
import com.ivo.mas.system.utils.SessionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class PicMainService {

    @Resource
    private PicMainMapper picMainMapper;
    
    /**
     * 查询多条数据
     *
     * @param picMain 查询条件
     * @return 对象列表
     */
    public ResponseResult<Object> queryPicMainList(PicMain picMain) {
        QueryWrapper<PicMain> queryWrapper = new QueryWrapper<PicMain>(picMain);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",picMainMapper.selectList(queryWrapper));
    }
    
    /**
     * 查询一条数据
     *
     * @param picMain 查询条件
     * @return 对象
     */
    public ResponseResult<Object> queryPicMainObject(PicMain picMain) {
        QueryWrapper<PicMain> queryWrapper = new QueryWrapper<PicMain>(picMain);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"查询成功",picMainMapper.selectOne(queryWrapper));
    }
    
    /**
     * 新增一条数据
     *
     * @param picMain 新增数据实体类
     * @return 新增对象
     */
    public ResponseResult<Object> addPicMain(PicMain picMain) {
        picMain.setId(null);
        picMain.setValidFlag(1);
        picMain.setCreateTime(new Date());
        picMainMapper.insert(picMain);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"新增成功",picMain);
    }
    
    /**
     * 修改一条数据
     *
     * @param picMain 修改数据实体类
     * @return 修改后对象
     */
    public ResponseResult<Object> editPicMain(PicMain picMain) {
        picMainMapper.updateById(picMain);
        return new ResponseResult<Object>(ResponseCode.SUCCESS,"修改成功",picMain);
    }

}

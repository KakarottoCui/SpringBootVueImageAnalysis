package com.ivo.mas.pojo;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * PicCutItem表实体类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicCutItem implements Serializable {
    private static final long serialVersionUID = 909581551492602606L;

/**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

/**
     * 主图片id
     */    
    private Integer picFk;
/**
     * 分割后图片路径
     */    
    private String cutPicPath;
/**
     * 创建时间
     */    
    private Date createTime;
/**
     * 有效标识
     */    
    private Integer validFlag;


}

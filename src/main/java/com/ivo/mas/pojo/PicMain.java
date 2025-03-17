package com.ivo.mas.pojo;

import java.beans.Transient;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * PicMain表实体类
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PicMain implements Serializable {
    private static final long serialVersionUID = -17617832240525257L;

/**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

/**
     * 图片地址
     */    
    private String picPath;
/**
     * 创建时间
     */    
    private Date createTime;
/**
     * 有效标识
     */    
    private Integer validFlag;

    @TableField(exist = false)
    private List<PicCutItem> list;
}

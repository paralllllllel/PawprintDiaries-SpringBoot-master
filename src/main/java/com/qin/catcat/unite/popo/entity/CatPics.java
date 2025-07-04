package com.qin.catcat.unite.popo.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cat_pics")
public class CatPics {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 猫咪ID
     */
    private Integer catId;
    
    /**
     * 图片URL地址
     */
    private String url;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 是否删除：0否 1是
     */
    private Integer isDeleted;
    
    /**
     * 更新人ID
     */
    private Integer updateUserId;
    
    /**
     * 图片排序号
     */
    private Integer sortNumber;
}

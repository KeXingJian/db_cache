package com.kxj.db_cache.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kxj
 * @since 2024-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productSerialNumber;

    private BigDecimal price;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @TableLogic(value = "0", delval = "1") // 0表示未删除，1表示已删除
    private Boolean isDeleted;

    public String getPrice1(){
        return "'"+price+"'";
    }

}

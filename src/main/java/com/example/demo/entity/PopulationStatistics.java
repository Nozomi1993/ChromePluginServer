package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 人员统计表
 * </p>
 *
 * @author DuQian
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("population_statistics")
public class PopulationStatistics implements Serializable {

    @ApiModelProperty(value = "日期")
    @TableId("data")
    private LocalDateTime data;

    @ApiModelProperty(value = "新增人数")
    @TableField("people_of_increase")
    private Integer peopleOfIncrease;

    @ApiModelProperty(value = "卸载人数")
    @TableField("people_of_uninstall")
    private Integer peopleOfUninstall;

    @ApiModelProperty(value = "剩余总计")
    @TableField("total_remaining")
    private Integer totalRemaining;


}

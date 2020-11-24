package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 人员统计详情

 * </p>
 *
 * @author DuQian
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("population_statistics_detail")
@ApiModel(value="PopulationStatisticsDetail对象", description="人员统计详情")
public class PopulationStatisticsDetail implements Serializable {


    @ApiModelProperty(value = "日期")
    @TableId("date")
    private String date;

    @ApiModelProperty(value = "ip")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "国家")
    @TableField("country")
    private String country;

    @ApiModelProperty(value = "操作")
    @TableField("type")
    private String type;


}

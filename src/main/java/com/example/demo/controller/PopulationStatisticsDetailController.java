package com.example.demo.controller;

import com.example.demo.base.BaseAdminController;
import com.example.demo.entity.PopulationStatisticsDetail;
import com.example.demo.service.IPopulationStatisticsDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 人员统计详情表 前端控制器
 * </p>
 *
 * @author DuQian
 * @since 2020-11-23
 */
@Controller
@RequestMapping("/populationStatisticsDetail")
@ResponseBody
public class PopulationStatisticsDetailController extends BaseAdminController<PopulationStatisticsDetail, IPopulationStatisticsDetailService> {

}

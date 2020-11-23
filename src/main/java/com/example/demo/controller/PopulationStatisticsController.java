package com.example.demo.controller;


import com.example.demo.base.BaseAdminController;
import com.example.demo.entity.PopulationStatistics;
import com.example.demo.service.IPopulationStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 人员统计表 前端控制器
 * </p>
 *
 * @author DuQian
 * @since 2020-11-23
 */
@Controller
@RequestMapping("/populationStatistics")
@CrossOrigin(origins = "*", maxAge = 3600)
@ResponseBody
public class PopulationStatisticsController extends BaseAdminController<PopulationStatistics, IPopulationStatisticsService> {

}

package com.ismyself.service;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-07  16:20
 * @description：
 */
public interface ReportService {
    Map<String, Object> findBusinessReportMap() throws Exception;

    Map<String, Object> findSexReportMap();

    Map<String, Object> findAgeReportMap() throws Exception;
}

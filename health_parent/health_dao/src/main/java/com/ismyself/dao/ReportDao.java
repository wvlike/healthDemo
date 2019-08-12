package com.ismyself.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    /**
     * 按会员性别获取会员数量
     *
     * @return
     */
    List<Map<String, String>> findsexNumber();

    /**
     * 根据年龄段查询会员数量
     *
     * @param map
     * @return
     */
    Integer findageNumber(Map<String, String> map);
}

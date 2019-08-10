package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.Utils.DateUtils;
import com.ismyself.dao.MemberDao;
import com.ismyself.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-07  16:20
 * @description：
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> findBusinessReportMap() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("reportDate",DateUtils.parseDate2String(new Date()));
        //新增会员数
        Integer todayMember = memberDao.findTodayMemberNum(DateUtils.parseDate2String(new Date()));
        map.put("todayNewMember", todayMember);
        //总会员数
        Integer totalMember = memberDao.findTotalMember();
        map.put("totalMember", totalMember);
        //本周新增会员数
        Date thisWeekMonday = DateUtils.getThisWeekMonday();//本周1
        Integer thisWeekNewMember = memberDao.findThisWeekNewMember(DateUtils.parseDate2String(thisWeekMonday));
        map.put("thisWeekNewMember", thisWeekNewMember);
        //本月新增会员数
        Date thisMonthFirstDay = DateUtils.getFirstDay4ThisMonth();//本月1号
        Integer thisMonthNewMember = memberDao.findThisMonthNewMember(DateUtils.parseDate2String(thisMonthFirstDay));
        map.put("thisMonthNewMember", thisMonthNewMember);
        //今日预约数
        Integer todayOrderNumber = orderDao.findTodayOrderNumber(DateUtils.parseDate2String(new Date()));
        map.put("todayOrderNumber", todayOrderNumber);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findTodayVisitsNumber(DateUtils.parseDate2String(new Date()));
        map.put("todayVisitsNumber", todayVisitsNumber);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(DateUtils.parseDate2String(thisWeekMonday));
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(DateUtils.parseDate2String(thisWeekMonday));
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(DateUtils.parseDate2String(thisMonthFirstDay));
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(DateUtils.parseDate2String(thisMonthFirstDay));
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        //查询集合
        List<Map<String, Object>> hotSetmeal = orderDao.findHotOrderSetmeal();
        map.put("hotSetmeal", hotSetmeal);


        return map;
    }
}

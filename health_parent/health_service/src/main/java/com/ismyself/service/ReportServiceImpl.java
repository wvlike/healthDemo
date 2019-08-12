package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.Utils.DateUtils;
import com.ismyself.dao.MemberDao;
import com.ismyself.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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
        map.put("reportDate", DateUtils.parseDate2String(new Date()));
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

    @Override
    public Map<String, Object> findSexReportMap() {
        Map<String, Object> resMap = new HashMap<>();
        List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        resMap.put("sexNames", sexList);
        List<Map> sexPercentList = new ArrayList<>();
        for (int i = 0; i < sexList.size(); i++) {
            Map<String, Object> sexPercentMap = new HashMap<>();
            String sexId;
            if ("男".equals(sexList.get(i))) {
                sexId = "1";
            } else {
                sexId = "2";
            }
            sexPercentMap.put("name", sexList.get(i));
            Integer sexCount = memberDao.findCountBySexId(sexId);
            sexPercentMap.put("value", sexCount);
            sexPercentList.add(sexPercentMap);
        }
        resMap.put("sexCount", sexPercentList);
        return resMap;
    }

    //按照会员的年龄段（可以指定几个年龄段，例如0-18、18-30、30-45、45以上）来展示各个年龄段的占比
    @Override
    public Map<String, Object> findAgeReportMap() throws Exception {
        Map<String, Object> resMap = new HashMap<>();
        List<String> ageList = new ArrayList<>();
        ageList.add("0-18");
        ageList.add("18-30");
        ageList.add("30-45");
        ageList.add("45以上");
        resMap.put("ageNames", ageList);
        List<Map> agePercentList = new ArrayList<>();
        for (String ageString : ageList) {
            Map<String, Object> map = new HashMap<>();
            String minAge = null;
            String maxAge = null;
            if (ageString.contains("-")) {
                minAge = ageString.split("-")[0];
                maxAge = ageString.split("-")[1];
            } else {
                minAge = ageString.substring(0, 2);
            }
            String todayDate = DateUtils.parseDate2String(new Date());
            Calendar calendar = Calendar.getInstance();
            int thisYear = calendar.get(Calendar.YEAR);
            String minBirthDay = String.valueOf(thisYear - Integer.parseInt(minAge)) + todayDate.substring(4);
            String maxBirthDay = null;
            if (maxAge != null) {
                maxBirthDay = String.valueOf(thisYear - Integer.parseInt(maxAge)) + todayDate.substring(4);
            }
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("minBirthDay", minBirthDay);
            queryMap.put("maxBirthDay", maxBirthDay);


            Integer count = memberDao.findAgeCountByMap(queryMap);
            map.put("name", ageString);
            map.put("value", count);
            agePercentList.add(map);
        }
        System.out.println(agePercentList+"*********************************************************");
        resMap.put("ageCount", agePercentList);
        return resMap;
    }
}

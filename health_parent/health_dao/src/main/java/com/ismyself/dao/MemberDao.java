package com.ismyself.dao;

import com.ismyself.pojo.Member;

import java.util.Date;
import java.util.Map;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-02  21:03
 * @description：
 */
public interface MemberDao {

    Member findMemberByTel(String telephone);

    void saveMember(Member member);

    Integer findMemberCountByMap(Map<String, String> map);

    Integer findTodayMemberNum(String date);

    Integer findTotalMember();

    Integer findThisWeekNewMember(String thisWeekMonday);

    Integer findThisMonthNewMember(String thisMonthFirstDay);

    Integer findCountBySexId(String sexId);

    Integer findAgeCountByMap(Map<String, String> queryMap);
}

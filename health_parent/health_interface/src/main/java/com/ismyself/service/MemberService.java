package com.ismyself.service;

import com.ismyself.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-03  14:46
 * @description：
 */
public interface MemberService {
    Member findMemberByTel(String telephone);

    void saveMember(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);

}

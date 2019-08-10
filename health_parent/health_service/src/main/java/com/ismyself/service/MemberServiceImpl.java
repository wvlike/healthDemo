package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.Utils.MD5Utils;
import com.ismyself.dao.MemberDao;
import com.ismyself.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-03  14:46
 * @description：
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTel(String telephone) {
        return memberDao.findMemberByTel(telephone);
    }

    @Override
    public void saveMember(Member member) {
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.saveMember(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> memberCount = new ArrayList<>();
        for (String timeStr : list) {
            Map<String, String> map = new HashMap<>();
            String fristDate = timeStr + ".1";
            String lastDate = timeStr + ".31";
            map.put("frist", fristDate);
            map.put("last", lastDate);
            Integer count = memberDao.findMemberCountByMap(map);
            memberCount.add(count);
        }
        return memberCount;
    }
}

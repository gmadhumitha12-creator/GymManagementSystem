package com.gym.dao;

import com.gym.bean.Member;
import com.gym.util.DBUtil;
import java.sql.*;
import java.util.*;

public class MemberDAO {

    public Member findMember(String memberID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps =
            con.prepareStatement("SELECT * FROM MEMBER_TBL1 WHERE MEMBER_ID=?");
        ps.setString(1, memberID);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) return null;

        Member m = new Member();
        m.setMemberID(rs.getString("MEMBER_ID"));
        m.setFullName(rs.getString("FULL_NAME"));
        m.setDob(rs.getDate("DATE_OF_BIRTH"));
        m.setPhone(rs.getString("PHONE"));
        m.setEmail(rs.getString("EMAIL"));
        m.setStatus(rs.getString("STATUS"));
        return m;
    }

    public List<Member> viewAllMembers() throws Exception {
        List<Member> list = new ArrayList<>();
        Connection con = DBUtil.getDBConnection();
        ResultSet rs = con.createStatement()
                          .executeQuery("SELECT * FROM MEMBER_TBL1");

        while (rs.next()) {
            Member m = new Member();
            m.setMemberID(rs.getString(1));
            m.setFullName(rs.getString(2));
            m.setDob(rs.getDate(3));
            m.setPhone(rs.getString(4));
            m.setEmail(rs.getString(5));
            m.setStatus(rs.getString(6));
            list.add(m);
        }
        return list;
    }
}
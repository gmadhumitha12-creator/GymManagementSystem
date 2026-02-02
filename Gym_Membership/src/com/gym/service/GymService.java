package com.gym.service;

import com.gym.bean.Member;
import com.gym.bean.Membership;
import com.gym.dao.MemberDAO;
import com.gym.dao.MembershipDAO;
import com.gym.util.DBUtil;
import com.gym.util.ValidationException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class GymService {

    private MemberDAO memberDAO = new MemberDAO();
    private MembershipDAO membershipDAO = new MembershipDAO();

    // 🔹 View single member
    public Member viewMemberDetails(String memberID) throws Exception {
        if (memberID == null || memberID.trim().isEmpty()) {
            throw new ValidationException();
        }
        return memberDAO.findMember(memberID);
    }

    // 🔹 View all members
    public List<Member> viewAllMembers() throws Exception {
        return memberDAO.viewAllMembers();
    }

    // 🔹 Enroll membership (TRANSACTIONAL)
    public boolean enrollMembership(String memberID,
                                    String membershipType,
                                    Date startDate,
                                    Date endDate) throws Exception {

        if (memberID == null || membershipType == null ||
            startDate == null || endDate == null ||
            startDate.after(endDate)) {
            throw new ValidationException();
        }

        // Check member exists
        Member member = memberDAO.findMember(memberID);
        if (member == null) {
            return false;
        }

        Connection con = DBUtil.getDBConnection();
        try {
            Membership m = new Membership();
            m.setMembershipID(generateMembershipID());
            m.setMemberID(memberID);
            m.setMembershipType(membershipType);
            m.setStartDate(startDate);
            m.setEndDate(endDate);
            m.setStatus("ACTIVE");

            boolean result = membershipDAO.insertMembership(m);
            con.commit();
            return result;

        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }

    // 🔹 Cancel membership (TRANSACTIONAL)
    public boolean cancelMembership(int membershipID) throws Exception {

        if (membershipID <= 0) {
            throw new ValidationException();
        }

        Connection con = DBUtil.getDBConnection();
        try {
            boolean result = membershipDAO.cancelMembership(membershipID);
            con.commit();
            return result;

        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }

    // 🔹 Simple ID generator (no SQL changes)
    private int generateMembershipID() {
        return (int) (System.currentTimeMillis() % 100000000);
    }
}
package com.gym.dao;

import com.gym.bean.Membership;
import com.gym.util.DBUtil;
import java.sql.*;

public class MembershipDAO {

    public boolean insertMembership(Membership m) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps =
            con.prepareStatement(
                "INSERT INTO MEMBERSHIP_TBL1 VALUES (?,?,?,?,?,?)");

        ps.setInt(1, m.getMembershipID());
        ps.setString(2, m.getMemberID());
        ps.setString(3, m.getMembershipType());
        ps.setDate(4, new java.sql.Date(m.getStartDate().getTime()));
        ps.setDate(5, new java.sql.Date(m.getEndDate().getTime()));
        ps.setString(6, m.getStatus());

        return ps.executeUpdate() > 0;
    }

    public boolean cancelMembership(int membershipID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps =
            con.prepareStatement(
                "UPDATE MEMBERSHIP_TBL1 SET STATUS='CANCELLED' WHERE MEMBERSHIP_ID=?");
        ps.setInt(1, membershipID);
        return ps.executeUpdate() > 0;
    }
}
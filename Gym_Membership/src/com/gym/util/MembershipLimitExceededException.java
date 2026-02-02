package com.gym.util;

public class MembershipLimitExceededException extends Exception {

    @Override
    public String toString() {
        return "Membership limit exceeded for the selected membership type";
    }
}
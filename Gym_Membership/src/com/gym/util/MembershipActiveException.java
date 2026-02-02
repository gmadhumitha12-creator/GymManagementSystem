package com.gym.util;

public class MembershipActiveException extends Exception {

    @Override
    public String toString() {
        return "Cannot remove member: Active membership exists";
    }
}
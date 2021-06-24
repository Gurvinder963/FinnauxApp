package com.finnauxapp.ApiRequest;

public class SaveCustomerMemberRequest {

    public int MemberId;
    public int CustomerId;
    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int LoginUserId;

    public String MemberName;

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberOccupation() {
        return MemberOccupationType;
    }

    public void setMemberOccupation(String memberOccupation) {
        MemberOccupationType = memberOccupation;
    }

    public String getMemberRelation() {
        return MemberReletion;
    }

    public void setMemberRelation(String memberRelation) {
        MemberReletion = memberRelation;
    }

    public String getMemberGender() {
        return MemberGender;
    }

    public void setMemberGender(String memberGender) {
        MemberGender = memberGender;
    }

    public String getMemberDOB() {
        return MemberDOB;
    }

    public void setMemberDOB(String memberDOB) {
        MemberDOB = memberDOB;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        MemberAddress = memberAddress;
    }

    public String getMemberWorkAddress() {
        return MemberWorkAddress;
    }

    public void setMemberWorkAddress(String memberWorkAddress) {
        MemberWorkAddress = memberWorkAddress;
    }

    public String getMemberContactNumber() {
        return MemberContactNumber;
    }

    public void setMemberContactNumber(String memberContactNumber) {
        MemberContactNumber = memberContactNumber;
    }

    public int getMember_HowMuchEarn() {
        return Member_HowMuchEarn;
    }

    public void setMember_HowMuchEarn(int member_HowMuchEarn) {
        Member_HowMuchEarn = member_HowMuchEarn;
    }

    public String MemberOccupationType;
    public String MemberReletion;
    public String MemberGender;
    public String MemberDOB;

    public int getMemberAge() {
        return MemberAge;
    }

    public void setMemberAge(int memberAge) {
        MemberAge = memberAge;
    }

    public int MemberAge;
    public String MemberAddress;
    public String MemberWorkAddress;
    public String MemberContactNumber;
    public int Member_HowMuchEarn;
}

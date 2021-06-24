package com.finnauxapp.ApiResponse;

public class CustomerMemberListResponse {


    /**
     * MemberId : 1
     * CustomerId : 1
     * MemberName : Mr. Ganpat Singh Tandi
     * MemberReletion : Father
     * MemberGenderAge : M 52
     * MemberAddress : 191 Loins Lane Khatipura Srisi Road Jaiour 302012
     * MemberOccupationType : JOB
     * MemberWorkAddress : 191 Loins Lane Khatipura Srisi Road Jaiour 302012
     * MemberContactNumber : 888888888
     * MemberHowMuchEarn : 20000
     */

    private int MemberId;
    private int CustomerId;
    private String MemberName;
    private String MemberReletion;
    private String MemberGenderAge;
    private String MemberAddress;
    private String MemberOccupationType;
    private String MemberWorkAddress;
    private String MemberContactNumber;
    private int MemberHowMuchEarn;

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int MemberId) {
        this.MemberId = MemberId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String MemberName) {
        this.MemberName = MemberName;
    }

    public String getMemberReletion() {
        return MemberReletion;
    }

    public void setMemberReletion(String MemberReletion) {
        this.MemberReletion = MemberReletion;
    }

    public String getMemberGenderAge() {
        return MemberGenderAge;
    }

    public void setMemberGenderAge(String MemberGenderAge) {
        this.MemberGenderAge = MemberGenderAge;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String MemberAddress) {
        this.MemberAddress = MemberAddress;
    }

    public String getMemberOccupationType() {
        return MemberOccupationType;
    }

    public void setMemberOccupationType(String MemberOccupationType) {
        this.MemberOccupationType = MemberOccupationType;
    }

    public String getMemberWorkAddress() {
        return MemberWorkAddress;
    }

    public void setMemberWorkAddress(String MemberWorkAddress) {
        this.MemberWorkAddress = MemberWorkAddress;
    }

    public String getMemberContactNumber() {
        return MemberContactNumber;
    }

    public void setMemberContactNumber(String MemberContactNumber) {
        this.MemberContactNumber = MemberContactNumber;
    }

    public int getMemberHowMuchEarn() {
        return MemberHowMuchEarn;
    }

    public void setMemberHowMuchEarn(int MemberHowMuchEarn) {
        this.MemberHowMuchEarn = MemberHowMuchEarn;
    }
}

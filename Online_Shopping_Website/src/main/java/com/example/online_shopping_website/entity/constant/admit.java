package com.example.online_shopping_website.entity.constant;

public class admit {
    public static final int registrationUnderReview = 0;    //商店注册审核中
    public static final int normal = 1;                     //商店正常
    public static final int registrationRejected = 2;       //商店注册被拒绝
    public static final int deletionUnderReview = 3;        //商店删除审核中
    public static final int deletionRejected = 4;           //商店删除被拒绝
    public static final int shopDeleted = 5;                //商店被删除
    public static final int adminApproveRegistration = 0;   //管理员同意注册申请
    public static final int adminApproveDeletion = 3;       //管理员同意删除申请
    public static final int adminRejectRegistration = 0;    //管理员拒绝注册申请
    public static final int adminRejectDeletion = 3;        //管理员拒绝删除申请
}

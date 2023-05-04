package com.example.online_shopping_website.mapper;

import com.example.online_shopping_website.entity.User;

import java.math.BigDecimal;
import java.util.List;

//@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user
     * @return 受影响的行数
     */
    Integer Insert(User user);

    /**
     * 注册用户
     *
     * @param user
     * @return 插入信息时，影响的行数
     */
    Integer Register(User user);

    /**
     * 根据 用户名 查询用户
     *
     * @param Username
     * @return 找到时返回用户数据；没有找到时返回null
     */
    User SearchByUsername(String Username);
    /**
     * 根据 电话号码 查询用户
     *
     * @param phone
     * @return 找到时返回用户数据；没有找到时返回null
     */
    User SearchByPhone(String phone);

    /**
     * 根据email查询用户
     *
     * @param email
     * @return 找到时返回用户数据；没有找到时返回null
     */
    User SearchByEmail(String email);

    /**
     * 根据 身份证号 查询用户
     *
     * @param idnum
     * @return 找到时返回用户数据；没有找到时返回null
     */
    User SearchByIdnum(String idnum);

    /**
     * 用户上传头像
     *
     * @param username avatar
     * @return 找到时返回用户数据；没有找到时返回null
     */
    void AvatarUpdate(String username,byte[] avatar);

    /**
     *  根据旧用户名寻找，更新用户名
     * @param oldusername
     * @param Newusername
     * @return 受影响的行数
     */
    int UpdateNewusernameByOldusername(String oldusername, String Newusername);

    /**
     *  根据旧用户名查找，更新密码
     * @param oldusername
     * @param Newpassword
     * @return 受影响的行数
     */
    int UpdateNewpasswordByOldusername(String oldusername, String Newpassword);

    /**
     * 根据旧用户名查找，更新手机号
     * @param oldusername
     * @param Newphone
     * @return 受影响的行数
     */
    int UpdateNewphoneByOldusername(String oldusername, String Newphone);

    /**
     * 根据旧用户名查找，更新电子邮箱
     * @param oldusername
     * @param Newemail
     * @return 受影响的行数
     */
    int UpdateNewemailByOldusername(String oldusername, String Newemail);

    int GetUserTypeByUsername(String username);

    BigDecimal GetPrivateAccountByUsername(String username);

    BigDecimal GetShopAccountByUsername(String username);

    BigDecimal GetProfitAccountByUsername(String username);

    BigDecimal GetIntermediaryAccountByUsername(String username);

    int RechargePrivateAccountByUsername(String username, BigDecimal credit);

    int RechargeShopAccountByUsername(String username, BigDecimal credit);

    int RechargeProfitAccountByUsername(String username, BigDecimal credit);

    int RechargeIntermediaryAccountByUsername(String username, BigDecimal credit);

    BigDecimal GetProfitAccount();
    BigDecimal GetIntermediaryAccount();
    void AvatarDelete(String username);

    int GetUidByShopname(String shopname);
    String DeleteShopAccountANDTransferBalanceToPrivateAccount(int uid);

    BigDecimal GetShopAccountByUid(int uid);

    void DeleteShopAccountByUid(int uid);

    void TransferShopAccountBalanceToPrivateAccount(int uid, BigDecimal ShopAccountBalance);
}


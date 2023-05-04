package com.example.online_shopping_website.mapper;
import com.example.online_shopping_website.entity.Shop;

import java.math.BigDecimal;
import java.util.List;

public interface ShopMapper {
    Shop ShopSearchByIdnum(String idnum);
    /**
     * 暂时没什么用
     */
    void updateUserShopID(int shopid,int uid);
    /**
     * 根据 身份证号 查询用户
     *
     * @param shopname
     * @return 找到时返回商店数据；没有找到时返回null
     */
    Shop SearchByShopname(String shopname);
    /**
     * 注册用户
     *
     * @param shop
     * @return 插入信息时，影响的行数
     */
    int shopOpening(Shop shop);
    /**
     * 更改开店权限
     * tested
     * @param shopname
     * @return 插入信息时，影响的行数
     */
    void updateIsAdmitted(String shopname);
    /**
     * 显示所有商店
     * tested
     * @param
     * @return 返回所有商店信息
     */

    List<Shop> ShowAllShop();
    /**
     * 显示所有商店
     * tested
     * @param
     * @return 返回所有商店信息
     */
    List<Shop> ShowAllAdmittedShop();
    /**
     * 显示所有被批准的商店
     * tested
     * @param
     * @return 返回已经被批准开店的所有商店信息
     */
    List<Shop> ShowAllNotAdmittedShop();
    /**
     * 显示所有没有被批准的商店
     * tested
     * @param
     * @return 返回没有被批准开店的所有商店信息
     */
    Shop ShopSearchByUid(int uid);
    /**
     * 通过uid寻找商店
     * tested
     * @param
     * @return 返回找到的商店
     */

    void TransferCapitalToIntemediaryAccount(BigDecimal capital);

    void TransferCapitalFromIntemediaryToProfitAccount(BigDecimal capital);

    void UnregisterShopByShopname(String shopname);
    void AvatarUpdate(String shopname,byte[] avatar);
    void AvatarDelete(String shopname);
    int GetShopIsAdmitted(String shopname);
    void SetShopNormal(String shopname);
    void SetShopDeleted(String shopname);
    void SetShopRegistrationRejected(String shopname);
    void SetShopdeletionRejected(String shopname);
    void CancelShopUnregistyerByShopname(String shopname, int is_admitted);
    void ClearShopInfoByShopname(String shopname);

    int GetShopAdmittedByShopname(String shopname);

    List<Shop> GetAllDeletedShop();

    BigDecimal GetCapitalByShopname(String shopname);

    void TransferCapitalFromIntemediary(BigDecimal capital);

    void TransferCapitalToProfitAccount(BigDecimal capital);

    List<Shop> GetAllDeletingShop();
}

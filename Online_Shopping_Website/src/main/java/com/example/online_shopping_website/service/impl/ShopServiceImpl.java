package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.entity.ShopInfo;
import com.example.online_shopping_website.mapper.ShopMapper;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.mapper.UserMapper;
import com.example.online_shopping_website.service.IShopService;
import com.example.online_shopping_website.service.ex.*;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.admit.*;
import static javax.security.auth.callback.ConfirmationCallback.*;

@Service
public class ShopServiceImpl implements IShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void open(Shop shop){
        String shopname = shop.getShopname();
        Shop nameresult = shopMapper.SearchByShopname(shopname);
        if (nameresult != null) {
            throw new ShopnameDuplicateException("尝试注册的店名[" + shopname + "]已经被占用");
        }
        Shop idresult = shopMapper.ShopSearchByIdnum(shop.getIDnum());

        if (idresult !=null){
            throw new idDuplicateException("该身份证号已经注册");
        }
        shopMapper.shopOpening(shop);

        //注册申请时，把注册资金转账到商城中间账户
        BigDecimal capital = new BigDecimal(shop.getCapital());
        shopMapper.TransferCapitalToIntemediaryAccount(capital);
    }
    @Override
    public Shop shop_admitted(String shopname){
        Shop shop = shopMapper.SearchByShopname(shopname);
        shopMapper.updateIsAdmitted(shopname);
        //注册资金转账到商城利润账号
        BigDecimal capital = new BigDecimal(shop.getCapital());
        shopMapper.TransferCapitalFromIntemediaryToProfitAccount(capital);
        return shop;
    }

    @Override
    public List<Shop> GetAllShop(){
        List<Shop> ShopList = shopMapper.ShowAllShop();
        return ShopList;
    }

    @Override
    public List<Shop> GetAllAdmittedShop(){
        List<Shop> ShopList = shopMapper.ShowAllAdmittedShop();
        return ShopList;
    }
    @Override
    public ShopInfo ShopAdmitAdmittanceCheck(String shopname){
        Shop shop = shopMapper.SearchByShopname(shopname);
        if(shop.getIs_admitted()==0) return null;
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopname(shopname);
        shopInfo.setIntroduction(shop.getIntroduction());
        shopInfo.setGoodstype(shop.getGoodstype());
        return shopInfo;
    }
    @Override
    public List<Shop> GetAllNotAdmittedShop(){
        List<Shop> ShopList_a = shopMapper.ShowAllNotAdmittedShop();
        List<Shop> ShopList_b = shopMapper.GetAllDeletingShop();
        List<Shop> shoplist = new ArrayList<>();
        shoplist.addAll(ShopList_a);
        shoplist.addAll(ShopList_b);
        return shoplist;
    }
    @Override
    public Shop GetShopInformationFromUser(String username){
        User user = userMapper.SearchByUsername(username);
        if(user ==null){
            throw new UserNotFoundException("用户名不存在！");
        }
        Shop shop = shopMapper.ShopSearchByUid(user.getUid());
        if(shop ==null){
            throw new ShopNotExistException("用户没有商店！");
        }
        return shop;
    }

    @Override
    public JsonResult shopUnregister(String shopname){
        JsonResult result = new JsonResult<>(YES, "提交成功，请等待管理员申请");
        //TODO:检查商店是否有未完成的订单 (Lab3不要求)

        shopMapper.UnregisterShopByShopname(shopname);
        return result;
    }

    @Override
    public JsonResult cancelRegister(String shopname, int cancelType){
        JsonResult result = new JsonResult<>(YES,"撤销成功");
        int admitted_now = shopMapper.GetShopIsAdmitted(shopname);

        switch (cancelType){
            case registrationUnderReview:                        //1.用户提交了注册申请 2.注册申请被管理员拒绝
            case registrationRejected:                           //此时可以取消注册申请,清空该用户提交的商店注册信息
                if(admitted_now == registrationUnderReview || admitted_now == registrationRejected) {
                    shopMapper.ClearShopInfoByShopname(shopname);
                }else {             //商店现在的is_admitted 不符合删除条件
                    result.setState(NO);
                    result.setMessage("撤销失败");
                }
                break;                                           //1.用户提交了删除申请 2.删除申请被管理员拒绝
            case deletionUnderReview:                            //此时可以取消删除申请,商店is_admitted设为正常
            case deletionRejected:
                if(admitted_now == deletionUnderReview || admitted_now == deletionRejected) {
                    shopMapper.CancelShopUnregistyerByShopname(shopname, cancelType);
                }else{              //商店现在的is_admitted 不符合删除条件
                    result.setState(NO);
                    result.setMessage("撤销失败");
                }
                break;
            default:
                result.setState(NO);
                result.setMessage("撤销失败");
                break;
        }

        return result;
    }

    @Override
    public JsonResult getShopInfoByShopname(String shopname){
        JsonResult result = new JsonResult<>(YES);
        Shop shop = shopMapper.SearchByShopname(shopname);
        if(shop == null)
            result.setState(NO);
        else
            result.setData(shop);
        return result;
    }
    @Override
    public void UpdateAvatar(String shopname,byte[] avatar){
        shopMapper.AvatarUpdate(shopname,avatar);
    }
    @Override
    public String GetAvatar(String shopname){
        Shop shop = shopMapper.SearchByShopname(shopname);
        byte[] imageData = shop.getAvatar();
        if(imageData==null) return null;
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }
    @Override
    public void deleteShopAvatar(String shopname){
        shopMapper.AvatarDelete(shopname);
    }

    @Override
    public JsonResult shopApplicationApproved(String shopname, int approveType){
        JsonResult result = new JsonResult<>(YES,"批复成功");
        int stateOfAdmit = shopMapper.GetShopIsAdmitted(shopname);
        switch (stateOfAdmit){
            case registrationUnderReview:
                if(approveType == adminApproveRegistration) {
                    //管理员同意申请时，把注册资金从中间账户转账到商城利润账号
                    BigDecimal capital = shopMapper.GetCapitalByShopname(shopname);
                    shopMapper.TransferCapitalFromIntemediary(capital);
                    shopMapper.TransferCapitalToProfitAccount(capital);

                    shopMapper.SetShopNormal(shopname);
                }
                else{
                    result.setState(NO);
                    result.setMessage("批复失败，请重试");
                }
                break;
            case deletionUnderReview:
                if(approveType == adminApproveDeletion) {
                    int uid = userMapper.GetUidByShopname(shopname);        //删除成功，商店账户自动注销，资金转移至商户个人账户
                    BigDecimal ShopAccountBalance = userMapper.GetShopAccountByUid(uid);
                    userMapper.DeleteShopAccountByUid(uid);
                    userMapper.TransferShopAccountBalanceToPrivateAccount(uid, ShopAccountBalance);
                    //userMapper.DeleteShopAccountANDTransferBalanceToPrivateAccount(uid);
                    shopMapper.SetShopDeleted(shopname);
                }
                else{
                    result.setState(NO);
                    result.setMessage("批复失败，请重试");
                }
                break;
            default:
                result.setState(NO);
                result.setMessage("批复失败，请重试");
        }
        return result;
    }

    @Override
    public JsonResult shopApplicationRejected(String shopname, int rejectType){
        JsonResult result = new JsonResult<>(YES,"批复成功");
        int stateOfAdmit = shopMapper.GetShopIsAdmitted(shopname);
        switch (stateOfAdmit){
            case registrationUnderReview:
                if(rejectType == adminRejectRegistration)
                    shopMapper.SetShopRegistrationRejected(shopname);
                else{
                    result.setState(NO);
                    result.setMessage("批复失败，请重试");
                }
                break;
            case deletionUnderReview:
                if(rejectType == adminRejectDeletion)
                    shopMapper.SetShopdeletionRejected(shopname);
                else{
                    result.setState(NO);
                    result.setMessage("批复失败，请重试");
                }
                break;
            default:
                result.setState(NO);
                result.setMessage("批复失败，请重试");
        }
        return result;
    }
}

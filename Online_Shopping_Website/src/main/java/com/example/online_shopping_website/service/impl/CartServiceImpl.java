package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.GoodReturn;
import com.example.online_shopping_website.entity.GoodSortByShop;
import com.example.online_shopping_website.entity.pic;
import com.example.online_shopping_website.mapper.CartMapper;
import com.example.online_shopping_website.mapper.GoodMapper;
import com.example.online_shopping_website.mapper.PicMapper;
import com.example.online_shopping_website.mapper.ShopMapper;
import com.example.online_shopping_website.service.ICartService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private PicMapper picMapper;
    @Override
    public JsonResult setCartGoodsNum(String username, int goodsId, int num){
        JsonResult result =new JsonResult<>(YES);
        int goodsStock = goodMapper.GetGoodsStockByGoodsId(goodsId);
        if(num > goodsStock)    //情况1：添加到购物车的数量大于库存数量
            result.setState(NO);
        else{
            if(cartMapper.IsGoodsInCart(username,goodsId))  //情况2：用户名下的购物车已经有相应的商品了
                cartMapper.updateCartGoodsNum(username, goodsId, num);  //购物车已有，更新数据库
            else
                cartMapper.insertCartGoodsNum(username, goodsId, num);  //情况3：购物车没有，插入数据库
        }
        return result;
    }

    @Override
    public JsonResult addToCart(String username, int goodsId, int addNum){
        JsonResult result = new JsonResult<>(YES);

        int originalNum;
        Integer oNum = cartMapper.getGoodsNumberInCart(username, goodsId);
        if(oNum == null)
            originalNum = 0;
        else
            originalNum = oNum;
        //取出购物车中该商品的数量。判断
        if(originalNum == 0 && addNum > 0){   //购物车中没有相应商品，插入
            int newNum = addNum;
            cartMapper.InsertNewGoodsIntoCart(username, goodsId, newNum);
        }else if (originalNum > 0 && addNum > 0 ){   //增加购物车中商品数量
            int newNum = originalNum + addNum;
            cartMapper.UpdateGoodsNumInCart(username, goodsId, newNum);
        }else{  //异常
            result.setState(NO);
        }
        //移除购物车中数量为0的商品
        cartMapper.DeleteZeroGoodsInCart(username);
        return result;
    }

    @Override
    public JsonResult getValidCart(String username){
        JsonResult result = new JsonResult<>(YES);

        List<GoodSortByShop> ListgoodSortByShop = new ArrayList<>();    //包装类，包装shopname和List<GoodReturn>

        List<Integer> allGoodsId = cartMapper.GetAllGoodsIdInCartByUsername(username);
        for(Integer id : allGoodsId){
            Boolean ShopOfGoodAlreadyInlist = Boolean.FALSE;        //判断商店所属的商品是否在ListgoodSortByShop中

            String shopname = goodMapper.GetShopnamByGoodsId(id);
            int shopAdmitted = shopMapper.GetShopAdmittedByShopname(shopname);
            int goodStatus = goodMapper.GetGoodStatusByGoodsId(id);
            if(shopAdmitted == 1 && goodStatus == 1){   //商店状态和商品状态都正常
                Good good = goodMapper.getGoodsByGoodsId(id);
                List<String> piclist = new ArrayList<>();
                GoodReturn goodReturn = new GoodReturn();
                goodReturn.setGoodsPrice(good.getGoodsPrice());
                goodReturn.setGoodsStock(good.getGoodsStock());
                goodReturn.setGoodsId(good.getGoodsId());
                goodReturn.setGoodsname(good.getGoodsname());
                goodReturn.setIntroduction(good.getIntroduction());
                goodReturn.setShopname(good.getShopname());
                goodReturn.setStatus(good.getStatus());
                goodReturn.setRegisterStatus(good.getRegisterStatus());
                goodReturn.setModifyStatus(good.getModifyStatus());
                goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
                for(pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())){
                    //System.out.println(good.getGoodsId());
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    //System.out.println(base64Image.substring(0,100));
                    piclist.add(base64Image);
                }
                goodReturn.setGoodsAvatar(piclist);

                int NumOfGoodsInCart = cartMapper.getGoodsNumberInCart(username, id);

                //如果goodReturn的商品名，与ListgoodSortByShop中某一项的商店名一样，就加入该商店，否则就新建一个goodSortByShop对象
                for(int i = 0; i < ListgoodSortByShop.size(); i++){
                    if( ListgoodSortByShop.get(i).getShopname().equals(goodReturn.getShopname()) ) {
                        ListgoodSortByShop.get(i).getGoodReturnList().add(goodReturn);
                        ListgoodSortByShop.get(i).getGoodNums().add(NumOfGoodsInCart);
                        ShopOfGoodAlreadyInlist = Boolean.TRUE;
                        break;
                    }
                }
                if(!ShopOfGoodAlreadyInlist){   //商店名没出现过，新加商店名
                    String NewShopname = goodReturn.getShopname();
                    List<GoodReturn> ListGoodReturn = new ArrayList<>();
                    ListGoodReturn.add(goodReturn);
                    List<Integer> goodsNumInCart = new ArrayList<>();
                    goodsNumInCart.add(NumOfGoodsInCart);

                    GoodSortByShop goodSortByShop = new GoodSortByShop(NewShopname, ListGoodReturn, goodsNumInCart);
                    ListgoodSortByShop.add(goodSortByShop);
                    ShopOfGoodAlreadyInlist = Boolean.FALSE;
                }
            }else{
                ;
            }
        }

        result.setData(ListgoodSortByShop);
        return result;
    }

    @Override
    public JsonResult getInvalidCart(String username){
        JsonResult result = new JsonResult<>(YES);

        List<GoodSortByShop> ListgoodSortByShop = new ArrayList<>();    //包装类，包装shopname和List<GoodReturn>

        //List<GoodReturn> invalidGoodsInCart = new ArrayList<>();
        List<Integer> allGoodsId = cartMapper.GetAllGoodsIdInCartByUsername(username);
        for(Integer id : allGoodsId){
            Boolean ShopOfGoodAlreadyInlist = false;

            String shopname = goodMapper.GetShopnamByGoodsId(id);
            int shopAdmitted = shopMapper.GetShopAdmittedByShopname(shopname);
            int goodStatus = goodMapper.GetGoodStatusByGoodsId(id);
            if(shopAdmitted == 1 && goodStatus == 1){   //商店状态和商品状态都正常

            }else{
                Good good = goodMapper.getGoodsByGoodsId(id);

                List<String> piclist = new ArrayList<>();
                GoodReturn goodReturn = new GoodReturn();
                goodReturn.setGoodsPrice(good.getGoodsPrice());
                goodReturn.setGoodsStock(good.getGoodsStock());
                goodReturn.setGoodsId(good.getGoodsId());
                goodReturn.setGoodsname(good.getGoodsname());
                goodReturn.setIntroduction(good.getIntroduction());
                goodReturn.setShopname(good.getShopname());
                goodReturn.setStatus(good.getStatus());
                goodReturn.setRegisterStatus(good.getRegisterStatus());
                goodReturn.setModifyStatus(good.getModifyStatus());
                goodReturn.setGoodsCategory(Arrays.asList(good.getGoodsCategory().split(";")));
                for(pic pics : picMapper.searchPicByGoodsId(good.getGoodsId())){
                    //System.out.println(good.getGoodsId());
                    byte[] imageData = pics.getPic();
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    //System.out.println(base64Image.substring(0,100));
                    piclist.add(base64Image);
                }
                goodReturn.setGoodsAvatar(piclist);
                //获取购物车中商品数量
                int NumOfGoodsInCart = cartMapper.getGoodsNumberInCart(username, id);


                //如果goodReturn的商店名，与ListgoodSortByShop中某一项的商店名，就加入该商店，否则就新建一个goodSortByShop对象
                for(int i = 0; i < ListgoodSortByShop.size(); i++){
                    if( ListgoodSortByShop.get(i).getShopname().equals(goodReturn.getShopname()) ) {
                        ListgoodSortByShop.get(i).getGoodReturnList().add(goodReturn);
                        ListgoodSortByShop.get(i).getGoodNums().add(NumOfGoodsInCart);
                        ShopOfGoodAlreadyInlist = true;
                        break;
                    }
                }
                if(!ShopOfGoodAlreadyInlist){   //商店名没出现过，新加商店名
                    String NewShopname = goodReturn.getShopname();
                    List<GoodReturn> ListGoodReturn = new ArrayList<>();
                    ListGoodReturn.add(goodReturn);
                    List<Integer> goodsNumInCart = new ArrayList<>();
                    goodsNumInCart.add(NumOfGoodsInCart);

                    GoodSortByShop goodSortByShop = new GoodSortByShop(NewShopname, ListGoodReturn,goodsNumInCart);
                    ListgoodSortByShop.add(goodSortByShop);
                    ShopOfGoodAlreadyInlist = false;
                }

                //invalidGoodsInCart.add(goodReturn);
            }
        }
        result.setData(ListgoodSortByShop);
        return result;
    }
    @Override
    public JsonResult deleteGoodsFromCart(String username, int goodsId){
        cartMapper.DeleteGoodsInCart(username, goodsId);
        JsonResult result = new JsonResult<>(YES);
        return result;
    }

    @Override
    public JsonResult deleteGoodsListFromCart(String username, List<Integer> goodsList){
        for(Integer goodsId : goodsList){
            cartMapper.DeleteGoodsInCart(username, goodsId);
        }
        JsonResult result = new JsonResult<>(YES);
        return result;
    }
}

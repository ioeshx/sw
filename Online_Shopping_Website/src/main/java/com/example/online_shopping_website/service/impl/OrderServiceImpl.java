package com.example.online_shopping_website.service.impl;

import com.example.online_shopping_website.entity.Good;
import com.example.online_shopping_website.entity.Order;
import com.example.online_shopping_website.entity.constant.AccountType;
import com.example.online_shopping_website.mapper.*;
import com.example.online_shopping_website.service.IOrderService;
import com.example.online_shopping_website.service.TransactionService;
import com.example.online_shopping_website.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.online_shopping_website.entity.constant.AccountType.*;
import static com.example.online_shopping_website.entity.constant.OrderState.*;
import static com.example.online_shopping_website.entity.constant.TransactionType.*;
import static com.example.online_shopping_website.entity.constant.TransactionType.refundToPrivateAccount;
import static javax.security.auth.callback.ConfirmationCallback.NO;
import static javax.security.auth.callback.ConfirmationCallback.YES;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private TransactionService transactionService;


    @Override
    public JsonResult orderCreating(String username, int addressId, List<Integer> allGoodsId, List<Integer> allGoodsNum){
        JsonResult result = new JsonResult<>(YES,"成功创建订单");
        //检查商品库存
        List<Integer> OutofStock = new ArrayList<>();
        for(int i = 0; i < allGoodsNum.size(); i++)
            if( goodMapper.GetGoodsStockByGoodsId(allGoodsId.get(i)) < allGoodsNum.get(i))
                OutofStock.add(i);
        if( !OutofStock.isEmpty() ) {
            result.setState(NO);
            result.setMessage("");
            for (int j : OutofStock) {
                String msg = goodMapper.GetGoodsNameByGoodsId(j) + ":库存不足;";
                result.addMessage(msg);
            }
            return result;
        }
        //创建订单
        Date orderTime = new Date();
        List<Integer> orderIdList = new ArrayList<>();
        for(int i = 0; i < allGoodsId.size(); i++){
            Good good = goodMapper.getGoodsByGoodsId(allGoodsId.get(i));
            BigDecimal unitPrice = new BigDecimal(good.getGoodsPrice());
            BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(allGoodsNum.get(i)));
            //实付金额要另行计算
            BigDecimal actualPayment = totalPrice;
            Order order = new Order(username, addressId, good.getShopname(), orderTime,
                    good.getGoodsId(), good.getGoodsname(), new BigDecimal(good.getGoodsPrice()),
                    allGoodsNum.get(i), totalPrice, actualPayment, pendingPayment);
            orderMapper.creatOrder(order);
            orderIdList.add(order.getOrderId());
        }
        result.setData(orderIdList);
        return  result;
    }

    @Override
    public JsonResult getOrderByOrderId(int orderId){
        JsonResult result = new JsonResult<>(YES,"成功");
        Order order = orderMapper.getOrderByOrderId(orderId);
        if(order == null){
            result.setState(NO);
            result.setMessage("失败");
            return result;
        }
        result.setData(order);
        return result;
    }

    @Override
    public JsonResult getAllOrders(String username){
        List<Order> allOrders = orderMapper.getAllOrdersByUsername(username);
        if(allOrders.isEmpty())
            return new JsonResult<>(NO,"没有订单！",allOrders);
        else
            return new JsonResult<>(YES,"成功",allOrders);
    }

    @Override
    public JsonResult payment(List<Integer> orderIdList){
        JsonResult result = new JsonResult<>(YES,"订单支付成功");

        String username = orderMapper.getUsernameByOrderId(orderIdList.get(0));
        BigDecimal privateAccount = userMapper.GetPrivateAccountByUsername(username);
        BigDecimal totalPayment = new BigDecimal(0);
        for(int orderId : orderIdList)
            totalPayment = totalPayment.add(orderMapper.getActualPaymentByOrderId(orderId));

        if(privateAccount.compareTo(totalPayment) < 0) {  //余额不足：个人账户余额小于订单费用
            result.setState(NO);
            String msg = "余额不足！账户余额是：" + privateAccount.toString() + " 总支付费用是：" + totalPayment.toString();
            result.setMessage(msg);
        }else{                                           //余额充足，个人账户余额减去订单费用，并设置订单状态为“待发货”
            privateAccount = privateAccount.subtract(totalPayment);
            userMapper.UpdatePrivateAccount(username, privateAccount);
            for(int orderId : orderIdList)
                orderMapper.SetOrderToPendingDeliveryByOrderId(orderId);
            //金额转入商城中间账户
            userMapper.TransferTotalPaymentToIntermediaryAccount(totalPayment);
            //插入流水记录
            transactionService.InsertTransaction(username, "管理员", AccountType.privateAccount, adminIntermediaryAccount,
                    paymentToIntermediaryAccount, totalPayment);
        }

        return result;
    }

    @Override
    public JsonResult cancelOrder(List<Integer> orderIdList){
        JsonResult result = new JsonResult<>(YES,"订单撤销成功");
        //对orderIdList中的order逐个检查，并且设置为撤销
        List<Integer> irrevocableOrders = new ArrayList<>();
        for(int orderId : orderIdList) {
            if(orderMapper.GetOrderStatusByOrderId(orderId) == pendingPayment)
                orderMapper.CancelOrderByOrderId(orderId);
            else{
                irrevocableOrders.add(orderId);
            }
        }
        //不可撤销的订单
        if( !irrevocableOrders.isEmpty() ){
            result.setState(NO);
            result.setMessage("");
            for (Integer orderId : irrevocableOrders ){
                String msg = "订单编号" + orderId.toString() + "不可撤销;";
                result.addMessage(msg);
            }
        }
        return result;
    }

    @Override
    public JsonResult confirmReceipt(List<Integer> orderIdList){
        JsonResult result = new JsonResult<>(YES,"确认收货成功！");
        List<Integer> nonConfirmReceipt = new ArrayList<>();
        for (int orderId : orderIdList){
            if(orderMapper.GetOrderStatusByOrderId(orderId) == pendingReception){
                //订单是“待收货”状态，把它设置成“已完成”
                orderMapper.SetOrderToFinishedByOrderId(orderId);
                //把订单费用转给商户
                BigDecimal actualPayment = orderMapper.getActualPaymentByOrderId(orderId);
                BigDecimal commission = actualPayment.multiply(new BigDecimal(0.05));   //佣金
                BigDecimal ShopGain = actualPayment.subtract(commission);                   //商店收入
                String shopName = orderMapper.GetShopNameByOrderId(orderId);
                int uid = shopMapper.GetUidByShopName(shopName);
                String merchantName = userMapper.GetUserByUid(uid).getUsername();
                //从中间账户减去订单费用，佣金转入商城利润账户 剩下的传到商店账户
                userMapper.SubtractFromIntermediaryAccount(actualPayment);
                userMapper.AddToProfitAccount(commission);
                userMapper.AddToShopAccount(uid, ShopGain);
                //插入“订单利润转入商店账户”的流水记录
                transactionService.InsertTransaction("管理员", merchantName, adminIntermediaryAccount, shopAccount,
                        profitToShopAccount, ShopGain);
                //插入”佣金转入利润账户“
                transactionService.InsertTransaction("管理员", "管理员", adminIntermediaryAccount, adminProfitAccount,
                        commissionToProfitAccount, commission );
                //增加商品销量
                int goodsId = orderMapper.GetGoodsIdByOrderId(orderId);
                int orderSales = orderMapper.GetGoodsNumByOrderId(orderId);
                goodMapper.AddToGoodsSales(goodsId, orderSales);
            }else{
                nonConfirmReceipt.add(orderId);
            }
        }

        if( !nonConfirmReceipt.isEmpty()) {
            result.setState(NO);
            result.setMessage("");
            for (Integer orderId : nonConfirmReceipt) {
                String msg = "订单编号" + orderId.toString() + "无法确认收货!";
                result.addMessage(msg);
            }
        }

        return  result;
    }

    @Override
    public JsonResult makingDelivery(List<Integer> orderIdList){
        JsonResult result = new JsonResult(YES,"发货成功");

        List<Integer> WrongOrderId = new ArrayList<>();
        for(Integer orderId : orderIdList){
            if(orderMapper.GetOrderStatusByOrderId(orderId) == pendingDelivery)
                orderMapper.SetOrderToPendingReception(orderId);
            else
                WrongOrderId.add(orderId);
        }
        if( !WrongOrderId.isEmpty()){
            result.setState(NO);
            result.setMessage("");
            for(Integer orderId : WrongOrderId){
                String msg = "订单编号" + orderId.toString() + "不可发货;";
                result.addMessage(msg);
            }
        }
        return result;
    }

    @Override
    public JsonResult getOrdersByStatus(String username, int status){
        List<Order> orders = orderMapper.GetOrdersByStatus(username, status);
        if(!orders.isEmpty())
            return new JsonResult(YES,"成功",orders);
        else
            return new JsonResult<>(NO,"失败");
    }

    @Override
    public JsonResult deleteOrder(List<Integer> orderIdList){
        JsonResult result = new JsonResult(YES,"删除成功");
        List<Integer> WrongOrderIdList = new ArrayList<>();
        for(Integer orderId : orderIdList){
            Integer status = orderMapper.GetOrderStatusByOrderId(orderId);
            if( status == finished || status == canceled || status == refunded)
                orderMapper.SetOrderToDeleted(orderId);
            else
                WrongOrderIdList.add(orderId);
        }
        if( !WrongOrderIdList.isEmpty()){
            result.setState(NO);
            result.setMessage("");
            for(Integer orderId : orderIdList){
                String msg = "订单编号" + orderId.toString() + "无法删除!";
                result.addMessage(msg);
            }
        }
        return  result;
    }

    @Override
    public JsonResult refundOrder(List<Integer> orderIdList){
        JsonResult result = new JsonResult(YES,"申请退款成功！");
        List<Integer> WrongOrderIdList = new ArrayList<>();
        for(Integer orderId : orderIdList){
            Integer status = orderMapper.GetOrderStatusByOrderId(orderId);
            if( status == pendingDelivery || status == pendingReception )
                orderMapper.SetOrderToPendingRefund(orderId);   //把订单状态改为"待退款"
            else
                WrongOrderIdList.add(orderId);
        }
        if( !WrongOrderIdList.isEmpty()){
            result.setState(NO);
            result.setMessage("");
            for(Integer orderId : orderIdList){
                String msg = "订单编号" + orderId.toString() + "无法申请退款!";
                result.addMessage(msg);
            }
        }
        return  result;
    }

    @Override
    public JsonResult agreeToRefund(List<Integer> orderIdList){
        JsonResult result = new JsonResult(YES,"退款成功！");
        List<Integer> WrongOrderIdList = new ArrayList<>();
        for(Integer orderId : orderIdList){
            Integer status = orderMapper.GetOrderStatusByOrderId(orderId);
            if( status == pendingRefunding ){
                //把订单状态改成"已退款"
                orderMapper.SetOrderToRefunded(orderId);
                //把钱从中间账号转回个人账号
                BigDecimal actualPayment = orderMapper.getActualPaymentByOrderId(orderId);
                String username = orderMapper.getUsernameByOrderId(orderId);
                userMapper.SubtractFromIntermediaryAccount(actualPayment);
                userMapper.AddToPrivateAccount(username, actualPayment);
                //插入流水记录
                transactionService.InsertTransaction("管理员", username, adminIntermediaryAccount, privateAccount,
                        refundToPrivateAccount, actualPayment);
            }
            else
                WrongOrderIdList.add(orderId);
        }
        if( !WrongOrderIdList.isEmpty()){
            result.setState(NO);
            result.setMessage("");
            for(Integer orderId : orderIdList){
                String msg = "订单编号" + orderId.toString() + "无法同意退款!";
                result.addMessage(msg);
            }
        }
        return  result;
    }

    @Override
    public JsonResult getAllOrdersOfShop(String shopName){
        List<Order> orders = orderMapper.GetOrdersByShopName(shopName);
        if(!orders.isEmpty())
            return new JsonResult<>(YES, "成功！", orders);
        else
            return new JsonResult<>(NO, "失败!");
    }

    @Override
    public JsonResult getOrdersOfShopByStatus(String shopName, int status){
        List<Order> orders = orderMapper.GetOrdersByShopNameAndStatus(shopName, status);
        if(!orders.isEmpty())
            return new JsonResult<>(YES, "成功！", orders);
        else
            return new JsonResult<>(NO, "失败!");
    }

}

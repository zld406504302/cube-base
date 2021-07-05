package cn.cube.base.third.keruyun.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:OrderResponse
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
public class OrderResponse {

    @Data
    public static class OrderInfo {
        //订单ID
        private Long orderId;
        //订单编号
        private String tradeNo;
        //交易类型；1:SELL:售货；2:REFUND:退货；3:SPLIT:拆单；4:反结账(已收款退货时新生成的订单)；5:反结账退货(已收款退货时新生成的反向订单,金额是销货单的负数)
        private Integer tradeType;
        //订单状态；4:FINISH:已完成(全部支付)；5:RETURNED:已退货； 10:已反结账；11:已挂账
        private Integer tradeStatus;
        //下单时间
        private Long orderTime;
        //结帐时间
        private Long checkOutTime;
        //订单来源；1:ANDROID收银终端；2:IPAD自助终端；3:ipad收银终端；31:微信官微；32:微信商微；33:微信快捷支付；41:百度外卖；51:百度直达号；61:百度糯米点菜；71:百度地图；81:呼叫中心；91:必胜客自助；92:味千；111:商户官网；131:loyal；141:OnMobile 客如云；142 onmobile os；151:熟客；161:饿了么；171:点评正餐；181:美团外卖菜；191:安卓自助
        private Long source;
        //订单来源名称
        private String sourceName;
        //商户实收金额，单位：分
        private Long receivedAmount;
        //用户实付金额 ，单位：分
        private Long custRealPay;
        //订单原始金额 ，单位：分
        private Long tradeAmount;
        //优惠总金额 ，单位：分
        private Long privilegeAmount;
    }


    @Data
    public static class OpenOrderExportDetailVO {
        //是 基本信息
        private BaseInfo baseInfo;
        //否 配送信息
        private DeliveryInfo deliveryInfo;
        //否 菜品信息
        private List<DishInfo> dishInfos;
        //否 附加金额
        private List<ExtraCharge> extraCharges;
        //否 订单结算信息
        private OrderInfo orderInfo;
        //否 优惠信息
        private List<PrivilegeInfo> privilegeInfos;
        //否 会员信息
        private List<TradeCustomerInfo> tradeCustomerInfos;
        //否 活动信息
        private List<TradePlanActivity> tradePlanActivities;
    }

    @Data
    public static class BaseInfo {
       //订单Id
       private Long id;
       //订单编号，门店下唯一
       private String tradeNo;
       //门店ID
       private Long shopId;
       //门店名称
       private String shopName;
       //订单来源
       private String sourceName;
       private Integer deliverType;
       //订单状态(1:未处理;2:挂单;3:已确认;4:已完成(全部支付);5:已退货;6:已作废;7:已拒绝;8:已取消;10:已反结账;)
       private Integer tradeStatus;
       private String deviceInfo;

    }

    @Data
    public static class DeliveryInfo {
        //否 地址
        private String deliveryAddress;
        //否 配送方
        private String deliveryPlatformName;
        //否 配送状态(0:默认，等待送餐;1:正在配送;2:送餐完成;3:已清账;4:取消配送;)
        private String deliveryStatus;
        //否 收货人
        private String receiverName;
        //否 收货人电话
        private String receiverPhone;
        //否 配送费
        private String deliverFee;
        //否 期望配送时间
        private String expectTime;
    }

    @Data
    public static class DishInfo {
        //是  菜品编码
        private String dishCode ;
        //是 菜品uuid
        private String uuid ;
        //是 菜品名称
        private String dishName ;
        //是  单价
        private BigDecimal price ;
        //是  数量
        private BigDecimal quantity ;
        //是  单位
        private String unitName ;
        //是  总额
        private BigDecimal amount ;
        //否 商品规格属性列表
        private List<DishProperty> dishProperties ;
        //否 套餐子菜
        private List<ChildNode> childNodes ;
        //是  菜品项目id
        private String itemId ;

    }

    @Data
    public static class DishCookingWay{
        //否 制作方式名称
        private String dishCookingWayName;
        //否 单价
        private Integer price;
        //否 数量
        private BigDecimal quantity;
        //否 金额
        private BigDecimal amount;
    }

    @Data
    public static class DishProperty{

    }

    @Data
    public static class ChildNode{
        //是 菜品编码
        private String dishCode;
        //是 菜品uuid
        private String uuid;
        //是 菜品名称
        private String dishName;
        //是 单价
        private BigDecimal price;
        //是 数量
        private BigDecimal quantity;
        //是 单位
        private String unit;
        //是 总额
        private BigDecimal amount;
        //是 菜品项目id
        private String itemId;
        //否 商品规格属性列表
        private List<DishProperty> dishProperties;
    }

    @Data
    public static class PrivilegeInfo {
        //优惠金额
        private BigDecimal privilegeAmount;
        //优惠内容
        private String privilegeValue;
        //优惠细项
        private String typeDetailName;
        //优惠类别
        private String typeName;
        //优惠券id
        private Long promoId;
        //优惠券模板ID
        private Long couponId;
    }

    @Data
    public static class TradeCustomerInfo {
        private Integer customerType;
        private Long customerId;
        private String customerName;
        private Integer customerSex;
        private Long memberId;
        private String entitycardNum;
    }

    @Data
    public static class TradePlanActivity {
        //活动ID
        private String ruleId;
        //活动名称
        private String ruleName;
    }
    @Data
    public static class ExtraCharge{
        //金额
        private BigDecimal amount;
        //名称
        private String name;
        //单价
        private BigDecimal privilegeAmount;
    }

}

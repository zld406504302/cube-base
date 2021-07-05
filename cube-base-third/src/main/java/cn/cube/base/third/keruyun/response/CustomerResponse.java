package cn.cube.base.third.keruyun.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:CustomerResponse
 * Author:zhanglida
 * Date:2021/3/21
 * Email:406504302@qq.com
 */
public class CustomerResponse {
    @Data
    public static class CustomerInfo{
        //是品牌ID
        private Long brandId;
        //是顾客标识ID
        private Long memberId;
        //是顾客ID
        private Long customerId;
        //是顾客主ID（CustomerMainId 是 顾客的主ID，是微信账号ID、手机号账号ID、支付宝账号ID 合并后的ID，如果没有合并CustomerMainId = CustomerId）
        private Long customerMainId;
        //否顾客昵称
        private String customerName;
        //是顾客所属门店名称
        private String commercialName;
        //是性别(-1:未知;0:女;1:男;)
        private Integer sex;
        //是等级Id
        private Long levelId;
        //是等级数1-5
        private Long level;
        //否等级名称
        private String levelName;
        //否备注
        private String memo;
        //否口味喜好
        private String interest;
        //否发票抬头
        private String invoiceTitle;
        //否地址
        private String address;
        //否会员卡号(非实体卡)
        private String entityCard;
        //否顾客分组
        private String groupId;
        //否分组名称
        private String groupName;
        //是生日
        private Long birthday;
        //是是否停用(1:停用;2:未停用;)
        private Integer isDisable;
        //是顾客来源(1:CALM;2:手机app;3:其他系统倒入;4:微信;5:支付宝;6:商家官网;7:百度;8:后台;9:百度外卖;10:饿了么;11:美图外卖;12:大众点评;13:熟客;14:糯米点菜;15:os mobile;16:开放平台;)
        private Integer source;
        //是登录类型(0:手机注册客户;1:微信注册用户;2:座机号;101:微信会员卡卡号;)
        private Integer loginType;
        //是手机Id\微信openId
        private String loginId;
        //是数据最后修改时间
        private Long modifyDateTime;
        //是会员创建时间
        private Long upgradeTime;
        //否微信openID
        private String openId;
        //否同步标识. 32位的唯一值
        private String synFlag;
        //否当前虚拟会员储值余额
        private BigDecimal remainValue;
        //否当前积分
        private Long integral;
        //是实体卡数量
        private Integer cardCount;
        //否优惠券数量
        private Integer coupCount;
        //否可挂账总额度
        private BigDecimal creditableValue;
        //是可挂账余额
        private BigDecimal remainCreditValue;
        //否已挂账金额
        private BigDecimal usedCreditValue;
        //否国家英文名称
        private String nation;
        //否国家中文名称
        private String country;
        //否电话国际区码
        private String nationalTelCode;
    }
}

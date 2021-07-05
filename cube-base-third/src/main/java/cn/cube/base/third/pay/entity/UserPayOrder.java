package cn.cube.base.third.pay.entity;

import cn.cube.base.core.db.ExcludeField;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Table
@Data
public class UserPayOrder {
    //支付状态 1：等待支付 2：支付成功 3:支付失败 4:退款
    @ExcludeField
    public static final   Integer STATUS_WAITING = 1;
    @ExcludeField
    public static final   Integer STATUS_SUCCESS = 2;
    @ExcludeField
    public static final   Integer STATUS_FAILURE = 3;
    @ExcludeField
    public static final   Integer STATUS_REFUND = 4;

    @Id
    private Long id;
    private Long uid;
    private String appId;
    private Integer amount;
    private Integer channel ;
    private Integer platform;
    private String tradeNo;
    private Integer status=STATUS_WAITING;
    private Date expireTime;
    private String error ;
    private Boolean share;
    private Date createTime;
    private Date updateTime;
}
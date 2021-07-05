package cn.cube.base.third.pay.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Description:PayConfig
 * Author:zhanglida
 * Date:2021/1/18
 * Email:406504302@qq.com
 */
@Table
@Data
public class PayConfig {
    @Column(name = "org_id")
    private String id;
    private String app;
    private String merchant ;
    private Integer channel ;
    private Integer platform ;
    private String appId;
    private String path ;
    private String secret ;
    private String mchId;
    private String certPath ;
    private String certPassword ;
    private String key ;
    private String notifyUrl;
}

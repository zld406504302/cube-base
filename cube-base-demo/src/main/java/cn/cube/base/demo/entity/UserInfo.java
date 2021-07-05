package cn.cube.base.demo.entity;

import cn.cube.base.core.entity.IdEntity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table
@Data
public class UserInfo implements IdEntity<Long> {
    @Id
    private Long id;
    private String appId;
    private String phone;
    private String name;
    private String nick;
    private String head;
    private Integer sex;
    private String country;
    private Date birth;
    private String province;
    private String city;
    private Integer admin;
    private Date createTime;
    private Date updateTime;
}
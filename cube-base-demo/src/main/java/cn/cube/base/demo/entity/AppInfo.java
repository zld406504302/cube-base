package cn.cube.base.demo.entity;

import cn.cube.base.core.entity.IAppInfo;
import com.google.common.collect.Maps;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Map;

@Table
@Data
public class AppInfo implements IAppInfo {
    @Id
    private String appId;
    private String appName;
    private String signKey;
    private String aesKey;
    private Integer merchant;
    private Date createTime;
    private Date updateTime;

    public enum MerchantEnum {
        CUSTOMER(1),
        MERCHANT(2);
        public final int type;
        private static Map<Integer, MerchantEnum> map = Maps.newHashMap();

        static {
            for (MerchantEnum merchant : MerchantEnum.values()) {
                map.put(merchant.type, merchant);
            }
        }

        MerchantEnum(int type) {
            this.type = type;
        }

        public static MerchantEnum typeOf(Integer type) {
            return map.get(type);
        }
    }
}
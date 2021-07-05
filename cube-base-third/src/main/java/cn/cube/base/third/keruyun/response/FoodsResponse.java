package cn.cube.base.third.keruyun.response;

import lombok.Data;

/**
 * Description:FoodsResponse
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
public class FoodsResponse {

    @Data
    public static class Category{
        private Long id ;
        private String aliasName ;
        private String name ;
        private Long sort ;
    }
}

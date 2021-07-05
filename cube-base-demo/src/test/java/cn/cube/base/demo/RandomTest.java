package cn.cube.base.demo;

import cn.cube.base.core.util.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Description:Rundom
 * Author:zhanglida
 * Date:2020/4/4
 * Email:406504302@qq.com
 */
public class RandomTest {

    public static void main(String[] args) {

        List<RandomItem> items = Lists.newArrayList();
        RandomItem one = new RandomItem(4000, 1);
        RandomItem two = new RandomItem(6, 2);
        RandomItem three = new RandomItem(2, 3);
        RandomItem four = new RandomItem(195992, 4);
        items.add(one);
        items.add(two);
        items.add(three);
        items.add(four);
        Map<Integer, Integer> typeMap = Maps.newHashMap();
        for (int i = 1; i <= 826; i++) {
            RandomItem random = RandomUtil.random(items);
            if (typeMap.containsKey(random.getType())) {
                Integer integer = typeMap.get(random.getType()) + 1;
                typeMap.put(random.getType(), integer);
            } else {
                typeMap.put(random.getType(), 1);
            }
        }

        Map<Integer, RandomItem> integerRandomItemMap = CollectionUtils.toMap(items, RandomItem::getType);
        typeMap.forEach((key, value) -> {
            RandomItem item = integerRandomItemMap.get(key);
            System.out.println("type:" + key + " real:" + value+" target:"+item.getWeight());
        });
    }

    public static class RandomUtil {

        public static RandomItem random(List<RandomItem> items) {

            items.sort(Comparator.comparing(RandomItem::getWeight).reversed());

            Map<Integer, RandomItem> integerRandomItemMap = CollectionUtils.toMap(items, RandomItem::getType);
            int totalWeight = 0 ;
            List<RandomItem> itemsCopy = Lists.newArrayList();
            for (RandomItem item:items) {
                totalWeight+= item.getWeight();
                RandomItem newItem = new RandomItem(totalWeight,item.getType());
                itemsCopy.add(newItem);
            }

            int randomValue = new Random().nextInt(totalWeight);
            List<RandomItem> randomItems = itemsCopy.stream().filter(item -> randomValue <= item.getWeight()).collect(Collectors.toList());
            if(randomItems.size()==0){
                System.out.println(1);
            }
            return randomItems.get(0);

        }

        public static int getTotalWeight(List<RandomItem> items) {
            int total = 0;
            for (RandomItem item : items) {
                total += item.getWeight();
            }
            return total;
        }
    }

    public static class RandomItem {
        private Integer weight;
        private Integer type;

        public RandomItem(Integer weight, Integer type) {
            this.weight = weight;
            this.type = type;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getWeight() {
            return weight;
        }

        public Integer getType() {
            return type;
        }

    }

}

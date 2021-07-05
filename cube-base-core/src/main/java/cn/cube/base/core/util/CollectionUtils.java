package cn.cube.base.core.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:集合容器辅助类
 * Author:zhanglida
 * Date:2017/3/23
 * Email:406504302@qq.com
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * 从List<T>中循环获取每个T的property对应的value
     *
     * @param list   T的property对应的value 列表
     * @param mapper Function = 类名::函数名
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<V> uniqValues(List<T> list, Function<? super T, ? extends V> mapper) {
        return values(list, mapper, true);
    }

    /**
     * 从List<T>中循环获取每个T的property对应的value
     *
     * @param list   T的property对应的value 列表
     * @param mapper Function = 类名::函数名
     * @param uniq   是否去重
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<V> values(List<T> list, Function<? super T, ? extends V> mapper, boolean uniq) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        if (uniq) {
            Map<V, T> map = list.stream().filter(t -> mapper.apply(t) != null).collect(Collectors.toMap(mapper, Function.identity(), (key1, key2) -> key2));
            return Lists.newArrayList(map.keySet());
        }

        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 求first对second的差集,即first中有，但second中没有的
     *
     * @param first
     * @param second
     * @return
     */

    public static List diff(List first, List second) {
        List list = new ArrayList(Arrays.asList(new Object[first.size()]));
        Collections.copy(list, first);
        list.removeAll(second);
        return list;
    }

    /**
     * 求first对second的交集
     *
     * @param first
     * @param second
     * @return
     */

    public static List same(List first, List second) {
        List list1 = new ArrayList(Arrays.asList(new Object[first.size()]));
        List list2 = new ArrayList(Arrays.asList(new Object[first.size()]));
        Collections.copy(list1, first);
        Collections.copy(list2, first);
        list1.removeAll(second);
        list2.removeAll(list1);
        list1.clear();
        return list2;
    }

    public static void main(String[] args) {
        List<Integer> first = Arrays.asList(1,3,5);
        List<Integer> second = Arrays.asList(3,5,7);
        System.out.println(same(first,second));
    }
    /**
     * 求2个集合的交集
     *
     * @param ls
     * @param ls2
     * @return
     */
    public static List intersect(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list;
    }

    /**
     * 求2个集合的并集
     *
     * @param ls
     * @param ls2
     * @return
     */
    public static List union(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);// 将ls的值拷贝一份到list中
        list.removeAll(ls2);
        list.addAll(ls2);
        return list;
    }

    /**
     * 从集合中随机生成要求数量的新集合
     *
     * @return
     * @param1 list
     * @param2 count
     */
    public static <T> List<T> randomList(List<T> list, int count) {
        if (isEmpty(list) || list.size() <= count) {
            return list;
        }

        List<T> copyList = new ArrayList<T>(list);
        List<T> randomList = new ArrayList<T>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(copyList.size());
            randomList.add(copyList.get(randomIndex));
            copyList.remove(randomIndex);
        }

        return randomList;
    }

    public static <K, V> Map<K, V> toMap(List<V> list, Function<? super V, ? extends K> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }

        return list.stream().filter(t -> mapper.apply(t) != null).collect(Collectors.toMap(mapper, Function.identity(), (key1, key2) -> key2));

    }

    public static <T> List<List<T>> splitList(List<T> list, int groupSize) {
        int length = list.size();
        int num = (length + groupSize - 1) / groupSize;
        List<List<T>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            int fromIndex = i * groupSize;
            int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }

    public static <T> void merge(List<T> from, List<T> to) {
        to.addAll(from);
    }

    public static <T, V> Map<V, List<T>> toGroupMap(List<T> list, Function<? super T, ? extends V> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }

        Map<V, List<T>> map = Maps.newHashMap();
        list.stream().filter(t -> mapper.apply(t) != null).forEach(t -> {
            V key = mapper.apply(t);
            if (!map.containsKey(key)) {
                map.put(key, Lists.newArrayList());
            }
            map.get(key).add(t);
        });

        return map;
    }
}

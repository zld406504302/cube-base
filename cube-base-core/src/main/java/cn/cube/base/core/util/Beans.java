package cn.cube.base.core.util;


import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.ResolvableType;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


public final class Beans {

    private static Logger logger = LoggerFactory.getLogger(Beans.class);

    private static CaseFormat[] toFormat = {
            CaseFormat.LOWER_CAMEL, CaseFormat.UPPER_CAMEL, CaseFormat.LOWER_HYPHEN, CaseFormat.LOWER_UNDERSCORE, CaseFormat.UPPER_UNDERSCORE};

    private Beans() {
    }

    public static void set(Object bean, String property, Object value) {
        BeanWrapper wrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(bean);

        wrapper.setPropertyValue(property, value);
    }

    @SuppressWarnings("unchecked")
    public static <R> R get(Object bean, String property) {
        BeanWrapper wrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(bean);
        return (R) wrapper.getPropertyValue(property);
    }

    public static <T, R> R copy(T from, Class<R> klass,
                                String... excludedProperties) {
        R to = null;
        try {
            to = klass.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error("get {} instance fail", klass);
            return null;
        }
        return copy(from, to, excludedProperties);
    }

    /**
     * 浅copy, 不逐级复制.
     *
     * @param from
     * @param to
     * @param excludedProperties
     * @return
     */
    public static <T, R> R copy(T from, R to, String... excludedProperties) {
        BeanWrapper wrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(from);
        BeanWrapper toWrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(to);
        Arrays.sort(excludedProperties);

        for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
            if (descriptor.getWriteMethod() == null
                    || descriptor.getReadMethod() == null) {
                continue;
            }

            String name = descriptor.getName();
            if(!toWrapper.isWritableProperty(name)){
                continue;
            }
            boolean found = Arrays.binarySearch(excludedProperties, name) >= 0;
            if (found) {
                continue;
            }

            toWrapper.setPropertyValue(name, wrapper.getPropertyValue(name));
        }
        return to;
    }

    public static <T, R> T extend(T target, R source, String... includedProperties) {
        return extend(target, source, false, includedProperties);
    }

    /**
     * @param target
     * @param source
     * @param ignoreNullProperty 是否忽略source里值为空的属性
     * @param includedProperties 未指定时extend所有属性
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> T extend(T target, R source, boolean ignoreNullProperty,
                                  String... includedProperties) {
        BeanWrapper targetWrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(target);
        BeanWrapper sourceWrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(source);

        Arrays.sort(includedProperties);

        for (PropertyDescriptor descriptor : sourceWrapper
                .getPropertyDescriptors()) {
            if (descriptor.getWriteMethod() == null
                    || descriptor.getReadMethod() == null) {
                continue;
            }

            String name = descriptor.getName();
            boolean found = includedProperties.length == 0
                    || Arrays.binarySearch(includedProperties, name) >= 0;
            if (!found) {
                continue;
            }

            if (ignoreNullProperty && sourceWrapper.getPropertyValue(name) == null) {
                continue;
            }

            if (!targetWrapper.isWritableProperty(name)) {
                continue;
            }

            targetWrapper.setPropertyValue(name,
                    sourceWrapper.getPropertyValue(name));
        }
        return target;
    }


    public static <T> List<T> mapToBeanList(List<Map> value, Class<?> type) {
        List list = new ArrayList(value.size());
        for (Map map : value) {
            list.add(mapToBean(map, type));
        }
        return list;
    }

    public static <T> T mapToBean(Map<Object, Object> data, Class<T> klass) {
        try {
            T bean = klass.newInstance();
            for (Map.Entry<Object, Object> entry : data.entrySet()) {
                if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                    String fieldName = entry.getKey().toString();
                    Object value = entry.getValue();
                    PropertyDescriptor propertyDescriptor = getPropertyDescriptor(klass, fieldName);
                    if (propertyDescriptor != null) {
                        Class<?> fieldType = propertyDescriptor.getPropertyType();
                        if (!fieldType.isAssignableFrom(Map.class) && value instanceof List && ((List) value).size() > 0 && ((List) value).get(0) instanceof Map) {
                            if (fieldType.isAssignableFrom(List.class)) {
                                try {
                                    fieldType = (Class) ((ParameterizedType) klass.getDeclaredField(propertyDescriptor.getName()).getGenericType()).getActualTypeArguments()[0];
                                    value = mapToBeanList(((List) value), fieldType);
                                } catch (Exception e) {
                                    logger.warn("未能成功转换List类型的属性:" + fieldName, e);
                                }
                            }
                        }
                        if (value == null) {
                            logger.warn("在{}类上,{}类型的字段({})的数据为null。已经忽略此字段的赋值操作。", klass, propertyDescriptor.getPropertyType(), propertyDescriptor.getName());
                        } else if (value.getClass().isAssignableFrom(propertyDescriptor.getPropertyType())) {
                            propertyDescriptor.getWriteMethod().invoke(bean, value);
                        } else if (String.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
                            propertyDescriptor.getWriteMethod().invoke(bean, value.toString());
                            logger.warn("在{}类上,已将{}类型的值({})转为String后赋值给String类型的字段({})。", klass, value.getClass(), value, propertyDescriptor.getName());
                        } else {
                            logger.warn("在{}类上,不能将{}类型的值({})赋值给{}类型的字段({})。已经忽略此字段的赋值操作。", klass, value.getClass(), value, propertyDescriptor.getPropertyType(), propertyDescriptor.getName());
                        }
                    } else {
                        logger.warn("在类{}上,没有与{}对应的字段,已经忽略相应值:{}。", klass, fieldName, value);
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("将map转为" + klass.getName() + "时出现错误", e);
        }
    }

    private static <T> PropertyDescriptor getPropertyDescriptor(Class<T> klass, String fieldName) {
        PropertyDescriptor propertyDescriptor = Beans.getPropertyDescriptor(klass, fieldName);
        if (propertyDescriptor == null) {
            CaseFormat from = CaseFormat.LOWER_CAMEL;
            if (fieldName.contains("-")) {
                from = CaseFormat.LOWER_HYPHEN;
            } else if (fieldName.contains("_")) {
                if (fieldName.toUpperCase().equals(fieldName)) {
                    from = CaseFormat.UPPER_UNDERSCORE;
                } else {
                    from = CaseFormat.LOWER_UNDERSCORE;
                }
            } else if (fieldName.substring(0, 1).toUpperCase().equals(fieldName.substring(0, 1))) {
                from = CaseFormat.UPPER_CAMEL;
            }
            for (CaseFormat to : toFormat) {
                propertyDescriptor = Beans.getPropertyDescriptor(klass, from.to(to, fieldName));
                if (propertyDescriptor != null) {
                    break;
                }
            }
        }
        return propertyDescriptor;
    }

    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> map = Maps.newHashMap();
        if (obj == null) {
            return null;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            logger.error("beanToMap fail", e);
        }

        return map;

    }


    public static <P> boolean isPrimitive(Class<P> targetClass) {

        if (targetClass.isPrimitive()) {
            return true;
        }
        return (targetClass == Integer.class || targetClass == Long.class
                || targetClass == String.class || targetClass == Boolean.class
                || targetClass == Short.class || targetClass == Float.class
                || targetClass == Double.class || targetClass == BigInteger.class
                || targetClass == BigDecimal.class);
    }

    public static <V> Class<V> getGenericType(Class clazz,Integer index){
        ResolvableType resolvableType = ResolvableType.forClass(clazz).getSuperType();
        ResolvableType[] types = resolvableType.getGenerics();
        return (Class<V>) types[index].resolve();
    }

    public static <V> Class<V> getGenericType(Class clazz){
        return getGenericType(clazz,0);
    }

}

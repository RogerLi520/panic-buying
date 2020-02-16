package com.wenyanwen123.buy.commons.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体转换器
 */
public class AdapterUtil {


    /**
     * 实体转换器
     *
     * @param source 原数据
     * @param target 目标类型
     * @param <T>    返回类型
     * @return 转换后对象
     */
    public static <T> T adapter(Object source, Class<T> target) {
        try {
            return adapter(source, target, target.newInstance(), null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实体转换器
     *
     * @param source              原数据
     * @param target              目标类型
     * @param defaultObjectEntity 寄主容器
     * @param <T>                 返回类型
     * @return 转换后对象
     */
    public static <T> T adapter(Object source, Class<T> target, T defaultObjectEntity) {
        return adapter(source, target, defaultObjectEntity, null);
    }

    /**
     * 实体转换器
     *
     * @param source              原数据
     * @param target              目标类型
     * @param defaultObjectEntity 寄主容器
     * @param ignoreProperties    忽略字段
     * @param <T>                 返回类型
     * @return 转换后对象
     */
    public static <T> T adapter(Object source, Class<T> target, T defaultObjectEntity, String[] ignoreProperties) {
        try {
            if (source == null)
                return defaultObjectEntity;
            T entity;
            if (defaultObjectEntity == null) {
                entity = target.newInstance();
            } else {
                entity = defaultObjectEntity;
            }
            Field[] fields = source.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                //是否ignore
                if (ignoreProperties != null && ignoreProperties.length > 0) {
                    boolean find = false;
                    for (String ignore : ignoreProperties) {
                        if (ignore.equalsIgnoreCase(name)) {
                            find = true;
                            break;
                        }
                    }
                    if (find)
                        continue;
                }

                //如果第二个字母是大写字符，第一个字母不大写
                // if (!name.substring(1, 2).equals(name.substring(1, 2).toUpperCase()))
                name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                Class type = field.getType();    //获取属性的类型
                //获取目标set方法
                Class[] parameterTypes = {type};
                Method set;
                try {
                    set = target.getMethod("set" + name, parameterTypes);
                } catch (NoSuchMethodException noMethodE) {
                    continue;
                }
                if (set != null) {
                    //获取源的get方法
                    Method get;
                    try {
                        get = source.getClass().getMethod("get" + name);
                    } catch (NoSuchMethodException noMethodE) {
                        if (type == boolean.class || type.getName().equals("java.lang.Boolean")) {
                            try {
                                get = source.getClass().getMethod("is" + name);
                            } catch (NoSuchMethodException noMethodE2) {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                    if (get != null) {
                        Object[] v = {get.invoke(source)};
                        //为目标设置值
                        set.invoke(entity, v);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            return defaultObjectEntity;
        }
    }


    /**
     * 实体转换器
     *
     * @param sourceList 原数据
     * @param target     目标类型
     * @param <T>        返回类型
     * @return 转换后对象
     */
    public static <T, V> List<T> adapter(List<V> sourceList, Class<T> target) {
        return adapter(sourceList, target, new ArrayList<T>(), null);
    }

    /**
     * 实体转换器
     *
     * @param sourceList           原数据
     * @param target               目标类型
     * @param defaultObjectEntitys 寄主容器
     * @param <T>                  返回类型
     * @return 转换后对象
     */
    public static <T, V> List<T> adapter(List<V> sourceList, Class<T> target, List<T> defaultObjectEntitys) {
        return adapter(sourceList, target, defaultObjectEntitys, null);
    }

    /**
     * 实体转换器
     *
     * @param sourceList           原数据
     * @param target               目标类型
     * @param defaultObjectEntitys 寄主容器
     * @param ignoreProperties     忽略字段
     * @param <T>                  返回类型
     * @return 转换后对象
     */
    public static <T, V> List<T> adapter(List<V> sourceList, Class<T> target, List<T> defaultObjectEntitys, String[] ignoreProperties) {
        try {
            if (sourceList == null)
                return defaultObjectEntitys;
            List<T> entityList;
            if (defaultObjectEntitys == null)
                entityList = new ArrayList<T>();
            else
                entityList = defaultObjectEntitys;
            if (sourceList.size() > 0) {
                Field[] fields = sourceList.get(0).getClass().getDeclaredFields();
                for (V source : sourceList) {
                    T entity = target.newInstance();
                    for (Field field : fields) {
                        String name = field.getName();
                        //是否ignore
                        if (ignoreProperties != null && ignoreProperties.length > 0) {
                            boolean find = false;
                            for (String ignore : ignoreProperties) {
                                if (ignore.equalsIgnoreCase(name)) {
                                    find = true;
                                    break;
                                }
                            }
                            if (find)
                                continue;
                        }
                        //如果第二个字母是大写字符，第一个字母不大写
                        //if (!name.substring(1, 2).equals(name.substring(1, 2).toUpperCase()))
                        name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                        Class type = field.getType();    //获取属性的类型
                        //获取目标set方法
                        Class[] parameterTypes = {type};
                        Method set;
                        try {
                            set = target.getMethod("set" + name, parameterTypes);
                        } catch (NoSuchMethodException noMethodE) {
                            continue;
                        }
                        if (set != null) {
                            //获取源的get方法
                            Method get;
                            try {
                                get = source.getClass().getMethod("get" + name);
                            } catch (NoSuchMethodException noMethodE) {
                                continue;
                            }
                            if (get != null) {
                                Object[] v = {get.invoke(source)};
                                //为目标设置值
                                set.invoke(entity, v);
                            }
                        }
                    }
                    //插入列表
                    entityList.add(entity);
                }
            }
            return entityList;
        } catch (Exception e) {
            return defaultObjectEntitys;
        }
    }
}

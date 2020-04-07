package com.huangxj.common.core.utils;


import com.huangxj.common.core.model.OptionVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.*;


/**
 * @ClassName BeanConverter
 * @Author: huangxj
 * @Create: 2018/11/24
 **/

public class BeanConverter {
    private static final Logger logger = LoggerFactory.getLogger(BeanConverter.class);

    /**
     * 单个对象转换
     */
    public static <DO, VO> VO convert(DO from, Class<VO> clazz, String... ignoreProperties) {
        if (from == null) {
            return null;
        }
        VO to = null;
        try {
            to = clazz.newInstance();
        } catch (Exception e) {
            logger.error("初始化{}对象失败。", clazz, e);
        }
        BeanUtils.copyProperties(from, to, ignoreProperties);
        return to;
    }

    /**
     * 单个对象转换
     */
    public static <DO, VO> VO convert(DO from, VO to, String... ignoreProperties) {
        if (from == null) {
            return null;
        }
        try {
            if (to == null) {
                Class<VO> clazz = (Class<VO>) to.getClass();
                to = clazz.newInstance();
            }
        } catch (Exception e) {
            logger.error("初始化{}对象失败。", to.getClass(), e);
        }
        BeanUtils.copyProperties(from, to, ignoreProperties);
        return to;
    }

    /**
     * 批量对象转换
     */
    public static <DO, VO> List<VO> convert(List<DO> fromList, Class<VO> clazz, String... ignoreProperties) {
        if (CollectionUtils.isEmpty(fromList)) {
            return new ArrayList<VO>();
        }
        List<VO> toList = new ArrayList<VO>();
        for (DO from : fromList) {
            toList.add(convert(from, clazz, ignoreProperties));
        }
        return toList;
    }


    /**
     * 将列表转为树
     * Vo中必须有id,parentId,List<Vo> children
     */
    public static <Vo> List listToTree(Integer parent, List<Vo> list, Class<Vo> clazz) {
        if (list == null) {
            return null;
        }
        List<Vo> ret = new ArrayList<>();
        try {
            Field idField = clazz.getDeclaredField("id");
            Field parentIdField = clazz.getDeclaredField("parentId");
            Field childrenField = clazz.getDeclaredField("children");
            parentIdField.setAccessible(true);
            childrenField.setAccessible(true);
            idField.setAccessible(true);
            for (Vo item : list) {
                Integer parentId = (Integer) parentIdField.get(item);
                Integer id = (Integer) idField.get(item);
                if (Objects.equals(parent, parentId)) {
                    childrenField.set(item, listToTree(id, list, clazz));
                    ret.add(item);
                }
            }
        } catch (Exception e) {
            return null;
        }
        if (ret.size() > 0) {
            return ret;
        } else {
            return null;
        }
    }

    /**
     * fjr
     * 转换二级菜单实体类
     *
     * @param list
     * @param clazz
     * @param value
     * @param label
     * @param parentValue
     * @return
     */
    public static List<OptionVo> convertVo(List list, Class clazz, String value, String label, String parentValue) {
        if (list == null) {
            return null;
        }

        List<OptionVo> listOptionVo = new ArrayList<>();
        Field valueField = null;
        Field labelField = null;
        Field parentValueField = null;
        try {

            if (!StringUtils.isEmpty(value)) {
                valueField = clazz.getDeclaredField(value);
                valueField.setAccessible(true);
            }
            if (!StringUtils.isEmpty(label)) {
                labelField = clazz.getDeclaredField(label);
                labelField.setAccessible(true);
            }
            if (!StringUtils.isEmpty(parentValue)) {
                parentValueField = clazz.getDeclaredField(parentValue);
                parentValueField.setAccessible(true);
            }

            for (Object o : list) {
                OptionVo optionVo = new OptionVo();

                if (!StringUtils.isEmpty(value)) {
                    Object oValueField = valueField.get(o);
                    optionVo.setValue(oValueField);
                }
                if (!StringUtils.isEmpty(label)) {
                    Object oLabelField = labelField.get(o);
                    optionVo.setLabel(oLabelField);
                }
                if (!StringUtils.isEmpty(parentValue)) {
                    Object oParentValueField = parentValueField.get(o);
                    optionVo.setParentValue(oParentValueField);
                }
                listOptionVo.add(optionVo);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (listOptionVo.size() > 0) {
            return listOptionVo;
        } else {
            return null;
        }
    }

    /**
     * fjr
     * 两个父子关系的List集合合并成一个List
     *
     * @param listRoot
     * @param listchildren
     * @return
     */
    public static List<OptionVo> converToListOptionVo(List<OptionVo> listRoot, List<OptionVo> listchildren) {
        //将两个父子关系的List集合合并成一个List
        List<OptionVo> listRootVo = new ArrayList<>();
        int i = 0;
        for (OptionVo oRoot : listRoot) {
            OptionVo optionVo = new OptionVo();
            optionVo.setId(i++);
            optionVo.setValue(oRoot.getValue());
            optionVo.setLabel(oRoot.getLabel());
            List<OptionVo> listChildrenVo = new ArrayList<>();
            for (OptionVo children : listchildren) {
                if (Objects.equals(oRoot.getValue(), children.getParentValue())) {
                    OptionVo childrenVo = new OptionVo();
                    childrenVo.setId(i++);
                    childrenVo.setValue(children.getValue());
                    childrenVo.setLabel(children.getLabel());
                    listChildrenVo.add(childrenVo);
                }
            }
            if (listChildrenVo.size() > 0) {
                optionVo.setChildren(listChildrenVo);
            }
            listRootVo.add(optionVo);
        }
        if (listRootVo.size() > 0) {
            return listRootVo;
        } else {
            return null;
        }
    }

    /**
     * 将实体类转化为map
     *
     * @param obj
     * @return
     */
    public static Map convertObjToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (Objects.isNull(obj)) {
            return null;
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                Field f = obj.getClass().getDeclaredField(field.getName());
                f.setAccessible(true);
                Object o = f.get(obj);
                map.put(field.getName(), o);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 单个对象转换,忽略空值
     */
    public static <DO, VO> VO convertIgnoreNull(DO from, VO to) {
        return convert(from, to, getNullPropertyNames(from));
    }

}

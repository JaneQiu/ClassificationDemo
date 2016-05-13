package jane.mall.util;

import java.util.Collection;
import java.util.Map;

import jane.mall.base.BaseEntity;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/13 11:55
 *         description
 *         vsersion
 */
public class Checker {
    public static final boolean isEmpty(Collection<?> data) {
        return data == null || data.size() == 0;
    }

    public static final boolean isNull(Object o) {
        return o == null;
    }

    public static final boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }


    public static final <T> boolean isEmpty(T[] t) {
        return t == null || t.length == 0;
    }


    public static final <T extends BaseEntity> boolean isSuccess(T t) {
        return t != null && "200".equals(t.getCode());
    }

}

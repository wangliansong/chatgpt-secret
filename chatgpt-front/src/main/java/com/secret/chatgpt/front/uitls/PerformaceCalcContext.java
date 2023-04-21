package com.secret.chatgpt.front.uitls;

import java.util.HashMap;
import java.util.Map;

/**
 * 利用ThreadLocal实现全局上下文
 */
public  class PerformaceCalcContext {
    private static final ThreadLocal<Map<Object, Object>> performaceCalcContext = new ThreadLocal<Map<Object, Object>>() {
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }

    };

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static Object getValue(Object key) {
        if(performaceCalcContext.get() == null) {
            return null;
        }
        return performaceCalcContext.get().get(key);
    }

    /**
     * 存储
     * @param key
     * @param value
     * @return
     */
    public static Object setValue(Object key, Object value) {
        Map<Object, Object> cacheMap = performaceCalcContext.get();
        if(cacheMap == null) {
            cacheMap = new HashMap<Object, Object>();
            performaceCalcContext.set(cacheMap);
        }
        return cacheMap.put(key, value);
    }

    /**
     * 根据key移除值
     * @param key
     */
    public static void removeValue(Object key) {
        Map<Object, Object> cacheMap = performaceCalcContext.get();
        if(cacheMap != null) {
            cacheMap.remove(key);
        }
    }

    /**
     * 重置
     */
    public static void reset() {
        if(performaceCalcContext.get() != null) {
            performaceCalcContext.get().clear();
        }
    }


}


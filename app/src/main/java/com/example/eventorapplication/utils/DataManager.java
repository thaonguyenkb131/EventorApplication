package com.example.eventorapplication.utils;

import com.example.models.Thesukien;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static DataManager instance;
    private Map<String, Object> cache = new HashMap<>();
    private Map<String, Long> cacheTimestamps = new HashMap<>();
    private static final long CACHE_DURATION = 5 * 60 * 1000; // 5 phút

    private DataManager() {}

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void putData(String key, Object data) {
        cache.put(key, data);
        cacheTimestamps.put(key, System.currentTimeMillis());
    }

    public Object getData(String key) {
        Long timestamp = cacheTimestamps.get(key);
        if (timestamp != null && System.currentTimeMillis() - timestamp < CACHE_DURATION) {
            return cache.get(key);
        }
        // Xóa cache cũ nếu hết hạn
        if (timestamp != null) {
            cache.remove(key);
            cacheTimestamps.remove(key);
        }
        return null;
    }

    public void clearCache() {
        cache.clear();
        cacheTimestamps.clear();
    }

    public void removeData(String key) {
        cache.remove(key);
        cacheTimestamps.remove(key);
    }

    // Các key constants
    public static final String KEY_OUTSTANDING_EVENTS = "outstanding_events";
    public static final String KEY_TRENDING_EVENTS = "trending_events";
    public static final String KEY_FOR_YOU_EVENTS = "for_you_events";
    public static final String KEY_SEARCH_RESULTS = "search_results";
    public static final String KEY_MY_EVENTS = "my_events";
    public static final String KEY_NOTIFICATIONS = "notifications";
    
    // Fragment keys
    public static final String KEY_SAVED_EVENTS = "saved_events";
    public static final String KEY_MY_TICKETS = "my_tickets";
    public static final String KEY_POSTED_EVENTS = "posted_events";
} 
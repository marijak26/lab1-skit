package main;

import java.util.HashMap;
import java.util.Map;

public class MapValueDifferences {
    public static Map<String, Integer> computeValueDifferences(Map<String, Integer> map1, Map<String, Integer> map2){
        if (map1 == null || map2 == null) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new HashMap<>();
        for(Map.Entry<String, Integer> entry : map1.entrySet()){
            if(map2.containsKey(entry.getKey())){
                result.put(entry.getKey(), Math.abs(entry.getValue() - map2.get(entry.getKey())));
            }
        }
        return result;
    }
}

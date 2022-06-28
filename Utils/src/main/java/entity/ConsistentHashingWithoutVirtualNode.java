package entity;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/28 10:36
 * @Tags
 **/
public class ConsistentHashingWithoutVirtualNode {

    /**
     * key : ip:port hash value, value : server name
     */
    private static SortedMap<Integer, String> sortMap = new TreeMap<Integer, String>();

    /**
     * init server
     * @param servers
     */
    public static void init(String[] servers) {
        for (int i = 0; i < servers.length; i++) {
            int hash = getHash(servers[i]);
            System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
            sortMap.put(hash, servers[i]);
        }
    }

    /**
     * Input the server name and return hash value
     * @param server
     * @return
     */
    public static int getHash(String server) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(int i = 0; i < server.length(); i++) {
            hash = (hash ^ server.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        return hash < 0 ? Math.abs(hash) : hash;
    }

    public static String getServer(String server) {
        int hash = getHash(server);
        SortedMap<Integer, String> subMap = sortMap.tailMap(hash);
        Integer i = subMap.firstKey();
        return subMap.get(i);
    }
}

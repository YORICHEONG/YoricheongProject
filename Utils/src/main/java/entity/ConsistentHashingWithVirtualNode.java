package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/28 10:36
 * @Tags
 **/
public class ConsistentHashingWithVirtualNode {

    /**
     * key : ip:port hash value, value : server name
     */
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * every real node have how much virtual nodes
     */
    private static final int VIRTUAL_NODES = 5;

    /**
     * Real server node list
     */
    private static List<String> realServerNodes = new LinkedList<>();

    /**
     * init server
     * @param servers
     */
    public static void init(String[] servers) {
        // store the real node
        for(String server : servers) {
            realServerNodes.add(server);
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = server + "&&VN" + String.valueOf(i);
                int hash = getHash(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
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
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        Integer i = subMap.firstKey();
        String virtualNodeName = subMap.get(i);
        System.out.println("load the virtualNodeName : " + virtualNodeName);
        return virtualNodeName.substring(0, virtualNodeName.indexOf("&&"));
    }
}

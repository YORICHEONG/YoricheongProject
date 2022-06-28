import entity.ConsistentHashingWithVirtualNode;
import entity.ConsistentHashingWithoutVirtualNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/24 16:35
 * @Tags
 **/
@SpringBootTest(classes = CollectionTest.class)
public class CollectionTest {

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            Integer value = ThreadLocalRandom.current().nextInt(300);
            System.out.println(value);
        }
    }

    @Test
    public void test02() {
        // server list
        String[] servers = {
                "192.168.0.0:1111",
                "192.168.0.1:1111",
                "192.168.0.2:1111",
                "192.168.0.3:1111",
                "192.168.0.4:1111"};

        ConsistentHashingWithVirtualNode.init(servers);
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++) {
            System.out.println("[" + nodes[i] + "]的hash值为" + ConsistentHashingWithVirtualNode.getHash(nodes[i]) + ", 被路由到结点[" + ConsistentHashingWithVirtualNode.getServer(nodes[i]) + "]");
        }

    }
}

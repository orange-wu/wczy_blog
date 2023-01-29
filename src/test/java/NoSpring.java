import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;

/**
 * @description: 方便测试
 * @author: wczy9
 * @createTime: 2023-01-21  17:26
 */
public class NoSpring {
    public static void main(String[] args) {
        String s = JSON.toJSONString(CollUtil.toList(1,
                2,
                6,
                7,
                8,
                9,
                10,
                3,
                11,
                12,
                202,
                13,
                201,
                213,
                14,
                15,
                16,
                4,
                214,
                209,
                17,
                18,
                205,
                206,
                208,
                210,
                215,
                216,
                217,
                218,
                19,
                20,
                5));
        System.out.printf(s);
    }
}
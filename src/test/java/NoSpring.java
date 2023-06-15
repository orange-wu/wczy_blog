import cn.dev33.satoken.stp.StpUtil;

import java.util.Date;

/**
 * @description: 方便测试
 * @author: wczy9
 * @createTime: 2023-01-21  17:26
 */
public class NoSpring {
    public static void main(String[] args) {
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(1);
        System.out.println(new Date().getTime()/1000);
    }
}

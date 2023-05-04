package com.example.online_shopping_website.service;

import com.example.online_shopping_website.entity.Shop;
import com.example.online_shopping_website.entity.ShopInfo;
import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.util.JsonResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/* 单元测试方法——不需要启动整个项目就可以进行测试
需要满足的4个条件
1.被@Test注解修饰
2.返回值void
3.方法的参数列表不指定任何类型
4.public
* */

// 标注当前类为测试类，不会随着项目一起打包发送
@SpringBootTest
// 启动这个单元测试类 参数是固定的
@RunWith(SpringRunner.class)
public class UserServiceTests {
    // 测试时需要连接数据库接口
    @Autowired
    private IUserService userService;
    @Test
    public void login() {
        JsonResult<User> loginResult = userService.login("test","123456");
        System.out.println(loginResult.getState());
        System.out.println(loginResult.getMessage());
        System.out.println(loginResult.getData());
    }

    @Test
    public void Register() {
        User user = new User(2, "test", "123456", "987654321",
                            "123456@qq.com", "123456", 0, "123");
        JsonResult<User> registerResult = userService.register(user);
        System.out.println(registerResult.getState());
        System.out.println(registerResult.getMessage());
        System.out.println(registerResult.getData());
    }
}

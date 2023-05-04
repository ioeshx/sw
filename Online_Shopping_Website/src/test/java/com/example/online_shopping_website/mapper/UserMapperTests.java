package com.example.online_shopping_website.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.online_shopping_website.entity.User;

import java.util.List;
/* 
单元测试方法——不需要启动整个项目就可以进行测试
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
public class UserMapperTests {
    // 测试时需要连接数据库接口
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("test1123");
        user.setPassword("123456");

        Integer rows = userMapper.Insert(user);
        System.out.println(rows);
    }

    @Test
    public void Register() {
        User user = new User(2, "test", "123456", "9876547984321",
                            "123asd456@qq.com", "1289413456", 0, "123");
        int row = userMapper.Register(user);
        System.out.println(row);
    }

    @Test
    public void adminRegister() {
        User admin = new User(0, "admin1", "123456", "1", "1", "1",0, "");
        int row = userMapper.Register(admin);
        System.out.println(row);
    }

    @Test
    public void SearchByUsername(){
        User user = userMapper.SearchByUsername("lisaitie");
        System.out.println(user);
    }

    @Test
    public void SearchByPhone() {
        User user = userMapper.SearchByPhone("987654321");
        System.out.println(user);
    }

    @Test
    public void SearchByEmail() {
        User user = userMapper.SearchByEmail("123456@qq.com");
        System.out.println(user);
    }

    @Test
    public void SearchByIdnum() {
        User user = userMapper.SearchByIdnum("123456");
        System.out.println(user);
    }

    @Test
    public void AvatarUpdate(){

    }

    @Test
    public void UpdateNewusernameByOldusername(){

    }

    @Test
    public void UpdateNewpasswordByOldusername(){

    }

    @Test
    public void UpdateNewphoneByOldusername(){

    }

    @Test
    public void UpdateNewemailByOldusername(){

    }

}

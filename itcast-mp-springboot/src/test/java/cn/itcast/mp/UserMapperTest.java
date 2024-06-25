package cn.itcast.mp;

import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testselect() {//查询tb_user记录
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testFindById() {//查询tb_user记录
        User user = userMapper.findById(2L);
        System.out.println(user);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("曹操");
        user.setPassword("111111");
        user.setAge(20);
        user.setEmail("test6@itcast.cn");
        user.setUserName("caocao");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("1990-01-01 00:00:00", dateTimeFormatter);
        user.setBirthday(localDateTime);
        int i = userMapper.insert(user);
        System.out.println(i);
    }

    @Test
    public void testUpdate() {
        User user = new User();//更新记录的主键值
        user.setId(100L);
        user.setAge(100);//要更新的值
        user.setPassword("12222");//要更新的值//只将对象中不为NULL的值更新到数据库中
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //根据条件进行更新
    @Test
    public void testUpdate2() {
        User user = new User();
        user.setAge(200);//要更新的值
        user.setPassword("12222");
        // 要更新的值
        // 设置条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "曹操");//只将对象中不为NULL的值更新到数据库中
        int i = userMapper.update(user, queryWrapper);
        System.out.println(i);
    }

    //根据条件进行更新，可以将为NUL1的值更新到数据库
    @Test
    public void testUpdate3() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "曹操").set("birthday", null);//只将对象中不为NULL的值更新到数据库中
        int i = userMapper.update(null, updateWrapper);
    }

    @Test
    public void testDelete() {
        //设置条件
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("age", 200);//根据条件删除
//        int delete = userMapper.delete(queryWrapper);
//        System.out.println(delete);

        User user = new User();
        user.setAge(200);
        user.setName("曹操1");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        int delete = userMapper.delete(queryWrapper);
        System.out.println(delete);
    }

    @Test
    public void testDelete2() {
        int delete = userMapper.deleteBatchIds(Arrays.asList(200,100));
        System.out.println(delete);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "曹操");
        //根据条件查询只能查询一条记录
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "刘备");
        queryWrapper.gt("age", "100");//大于？
        //根据条件查询只能查询一条记录
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void testSelectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "刘备");
        queryWrapper.gt("age", 100);//大于？
        //用构造方法设置当前页码，每页记录数
        int pageIndex= 2;//当前页码
        int size = 3;//每页记录数
        Page<User> page =new Page<>(pageIndex,size);
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        long pages = userIPage.getPages();//总页数
        long total = userIPage.getTotal();//总记录数
        // 记录列表
        List<User> records =userIPage.getRecords();
        System.out.println(records);
        System.out.println("总页数:"+pages);
        System.out.println("总记录数:"+total);
    }

    @Test
    public void testEq() {
        //条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //等于
        queryWrapper.eq("name", "刘备");
        queryWrapper.gt("age", 20);//大于?
        queryWrapper.in("user_name", "liubei");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testEq2() {
        //条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //等于
        queryWrapper.eq(User::getName, "刘备");
        queryWrapper.gt(User::getAge, 20);//大于?
        queryWrapper.in(User::getUserName, "liubei");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testEg3() {
        //条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String name = "刘备";
        Integer age = 100;
        queryWrapper.eq(name != null && !name.equals(""), User::getName, name);//等于
        queryWrapper.gt(age != null, User::getAge, age);//大于?
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //SELECT id, user name,password, name, age, email FROM tb_user WHERE name LIKE?
        // Parameters:%曹%(String)
        wrapper.likeRight("name", "刘");
        wrapper.select("id", "name");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users)
            System.out.println(user);
    }
}
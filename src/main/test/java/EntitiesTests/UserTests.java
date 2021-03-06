package EntitiesTests;

import junit.framework.Assert;
import org.junit.Test;
import ua.com.learninghub.model.dao.UserCategoryDao;
import ua.com.learninghub.model.dao.UserDao;
import ua.com.learninghub.model.entities.User;

import java.util.List;

/**
 * Created by vasax32 on 15.07.14.
 */
public class UserTests {
    UserDao userDao = new UserDao();

    @Test
    public void selectAll(){
        List<User> list = userDao.selectAll();
        for (User usr: list)
            System.out.println(usr);
    }

    @Test
    public void selectByIdTest(){
        User user = userDao.selectById(4);
        System.out.println(user);
    }

    @Test
    public void updateUsersCategoryTest(){
        User user = userDao.selectById(1);
        user.setCategory((new UserCategoryDao().selectById(1)));
        userDao.update(user);
    }

    @Test
    public void insertFullyNewUser(){
        User user = new User();
        user.setLogin("Insert OK");
        user.setPass("SomePass");
        user.setEmail("some@mail.com");
        user.setMoney(1000);
        user.setCategory((new UserCategoryDao().selectById(2)));
        userDao.insert(user);
    }

    @Test
    public void InsertExistingUser(){
        User user = new User();
        user.setLogin("root");
        user.setPass("pook");
        user.setEmail("mail@mail.com");
        user.setMoney(0);
        user.setCategory((new UserCategoryDao().selectById(1)));
        boolean b = userDao.insert(user);
    }

    @Test
    public void findTestExist(){
        User urs = userDao.findByLoginPass("root", "pook");
        Assert.assertEquals(urs.getEmail(), "mail@mail.com");
    }

    @Test
    public void findTestNotExist(){
        User urs = userDao.findByLoginPass("root", "pook2");
        Assert.assertNull(urs);
    }

    @Test
    public void RussianTextTest(){
        User user = new User();
        user.setLogin("test");
        user.setPass("test");
        user.setEmail("Проверка текста");
        user.setMoney(0);
        user.setCategory((new UserCategoryDao().selectById(1)));
        boolean b = userDao.insert(user);
    }

    @Test
    public void firstLetterTest(){
        String login = "русский";
        String pass = "3242";
        User usr = userDao.findByLoginPass(login, pass);
        System.out.println(usr);
    }
}

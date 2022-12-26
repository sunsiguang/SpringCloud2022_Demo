import com.cnrmall.springcloud.dao.UserDao;
import com.cnrmall.springcloud.entity.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author David
 * @date 2022/12/26 16:07
 */

@RunWith(SpringRunner.class)
public class SpringDataJpaTest {

    @Autowired
    private UserDao userDao ;
    @Before
    public void before() {
        UserDto user = new UserDto();
        user.setId(1);
        user.setName("风清扬");
        user.setAccount("fengqy");
        user.setPwd("123456");
        userDao.save(user); //保存

        user = new UserDto();
        user.setId(2);
        user.setName("东方不败");
        user.setAccount("bubai");
        user.setPwd("123456");
        userDao.save(user);

        user = new UserDto();
        user.setId(3);
        user.setName("向问天");
        user.setAccount("wentian");
        user.setPwd("123456");
        userDao.save(user);
    }

    @Test
    public void testAdd() {
        UserDto user = new UserDto();
        user.setId(1);
        user.setName("任我行");
        user.setAccount("renwox");
        user.setPwd("123456");
        userDao.save(user);
        user = new UserDto();
        user.setId(2);
        user.setName("令狐冲");
        user.setAccount("linghuc");
        user.setPwd("123456");
        userDao.save(user);
    }
}

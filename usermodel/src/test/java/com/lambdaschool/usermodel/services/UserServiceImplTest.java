package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Before
    public void setUp() throws Exception {
        //mocks = fake data
        //stubs -> fake methods
        MockitoAnnotations.initMocks(this);
        List<User> mylist= userService.findAll();
        //print out test data for hehe's and haha's
        for (User u : mylist)
        {
            System.out.println(u.getUsername()+ " " + u.getUserid());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void afindUserById() {
        assertEquals(4,userService.findUserById(4).getUserid());
    }


    @Test(expected = EntityNotFoundException.class)
    public void abfindUserByIdFail()
    {
        assertEquals("test eagle cafe", userService.findUserById(99999).getUsername());
    }

    @Test
    public void bfindByNameContaining() {
        assertEquals(1,userService.findByNameContaining("cin").size());
    }

    @Test
    public void cfindAll() {
        assertEquals(30,userService.findAll().size());
    }



    @Test
    public void efindByName() {
        assertEquals(11,userService.findByName("Test barnbarn").getUserid());
    }
    @Test(expected = EntityNotFoundException.class)
    public void eafindByNameNotFound()
    {
        assertEquals("cinnamon", userService.findByName("cinnamon").getUsername());
    }



    @Test
    public void fdelete() {
        userService.delete(11);
        assertEquals(29, userService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void fadeletenotfound()
    {
        userService.delete(99999);
        assertEquals(29, userService.findAll().size());
    }

    @Test
    public void gsave() {
        List<UserRoles> thisRole = new ArrayList<>();
        User newUser = new User("Number 1 Test User", "pass", "test@lambdaschool.local", thisRole);
        newUser.getUseremails().add(new Useremail(newUser, "newTest@lambdaschool.local"));
        User addUser = userService.save(newUser);

        assertNotNull(addUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals(addUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void hupdate() {
        List<UserRoles> thisRole = new ArrayList<>();
        thisRole.add(new UserRoles());
        thisRole.get(0).setRole(new Role());
        thisRole.get(0).getRole().setRoleid(2);
        thisRole.get(0).setUser(new User());
        User newUser = new User("Number 9999 Test User", "pass", "test3@lambdaschool.local", thisRole);

        newUser.getUseremails().add(new Useremail(newUser, "newTest99@lambdaschool.local"));

        User updateUser = userService.update(newUser, 13);
        assertEquals("number 9999 test user", updateUser.getUsername());
    }

    @Test
    public void igetCountUserEmails() {
        assertEquals(29,userService.getCountUserEmails().size());
    }


    @Test
    public void deleteUserRole() {
    }

    @Test
    public void addUserRole() {
    }
}
package com.ua.sutty.hibernate.repository.impl;

import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateUserService;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateUserDaoTest {

    private static IDatabaseTester databaseTester = null;
    private HibernateUserService hibernateUserDao;

    @Before
    public void importDataSet() throws Exception {
        hibernateUserDao = new HibernateUserService();
        IDataSet dataSet = readDataSet();
        beforeStart(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("dataset.xml"));
    }

    private void beforeStart(IDataSet dataSet) throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        String driver = configuration.getProperty("hibernate.connection.driver_class");
        String url = configuration.getProperty("hibernate.connection.url");
        String username = configuration.getProperty("hibernate.connection.username");
        String password = configuration.getProperty("hibernate.connection.password");
        configuration.buildSessionFactory().openSession();
        databaseTester = new JdbcDatabaseTester(driver, url, username, password);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test(expected = NullPointerException.class)
    public void testCreateNull() {
        hibernateUserDao.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNull() {
        hibernateUserDao.update(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNull() {
        hibernateUserDao.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFindByEmailNull() {
        hibernateUserDao.findByEmail(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFindByLoginNull() {
        hibernateUserDao.findByLogin(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWhereIdNull() {
        User user = User.builder()
                .id(null)
                .login("login")
                .password("password")
                .email("email")
                .firstName("firstname")
                .lastName("lastname")
                .birthday(new java.util.Date())
                .role(new Role(1L, "USER"))
                .build();
        hibernateUserDao.remove(user);
    }

    @Test
    public void testCreate() throws Exception {
        User user = User.builder()
                .id(4L)
                .login("fourthUser")
                .password("123")
                .email("fourthUser@gmail.com")
                .firstName("fourth")
                .lastName("user")
                .birthday(new java.util.Date())
                .role(new Role(3L, "MANAGER"))
                .build();
        hibernateUserDao.create(user);
        assertEquals(4, databaseTester.getConnection().createDataSet()
                .getTable("user").getRowCount());
        assertEquals(databaseTester.getConnection().createDataSet()
                .getTable("user")
                .getValue(3, "login"), user.getLogin());
        assertEquals(databaseTester.getConnection().createDataSet()
                .getTable("user")
                .getValue(3, "email"), user.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = User.builder()
                .id(3L)
                .login("thirdUser")
                .password("123")
                .email("thirdUser@gmail.com")
                .firstName("Third")
                .lastName("User")
                .birthday(new java.util.Date())
                .role(new Role(3L, "MANAGER"))
                .build();
        hibernateUserDao.update(user);
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(2, "login"), user.getLogin());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(2, "email"), user.getEmail());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(2, "first_name"), user.getFirstName());
    }

    @Test
    public void testRemove() throws Exception {
        User user = User.builder()
                .id(2L)
                .login("secondUser")
                .password("123")
                .email("secondUser@gmail.com")
                .firstName("second")
                .lastName("user")
                .birthday(new java.util.Date())
                .role(new Role(2L, "ADMIN"))
                .build();
        hibernateUserDao.remove(user);
        assertEquals(2,
                databaseTester.getConnection().createDataSet().getTable("user").getRowCount());
    }

    @Test
    public void testFindByLogin() throws Exception {
        String userName = String.valueOf(databaseTester.getConnection()
                .createDataSet().getTable("user").getValue(0, "login"));
        User user = hibernateUserDao.findByLogin(userName);
        assertNotNull("Test find user by login", user);
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "email"), user.getEmail());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "login"), user.getLogin());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "first_name"), user.getFirstName());
    }

    @Test
    public void testFindByEmail() throws Exception {
        String userMail = String.valueOf(databaseTester.getConnection()
                .createDataSet().getTable("user").getValue(0, "email"));
        User user = hibernateUserDao.findByEmail(userMail);
        assertNotNull("Test find user by email", user);
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "email"), user.getEmail());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "login"), user.getLogin());
        assertEquals(databaseTester.getConnection().createDataSet().getTable("user")
                .getValue(0, "first_name"), user.getFirstName());
    }

}

package com.ua.sutty.hibernate.repository.impl;

import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.service.HibernateRoleService;
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

public class HibernateRoleDaoTest {

    private static IDatabaseTester databaseTester = null;
    private HibernateRoleService hibernateRoleService;

    @Before
    public void importDataSet() throws Exception {
        hibernateRoleService = new HibernateRoleService();
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
        hibernateRoleService.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNull() {
        hibernateRoleService.update(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNull() {
        hibernateRoleService.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFindByNameNull() {
        hibernateRoleService.findByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWhereIdNull() {
        Role role = new Role();
        role.setId(null);
        role.setName("role");
        hibernateRoleService.remove(role);
    }

    @Test
    public void testCreate() throws Exception {
        Role someRole = new Role("createRole");
        hibernateRoleService.create(someRole);
        assertEquals(4, databaseTester.getConnection().createDataSet()
                .getTable("role").getRowCount());
        assertEquals(someRole.getName(), databaseTester.getConnection()
                .createDataSet().getTable("role").getValue(3, "name"));
    }

    @Test
    public void testUpdate() throws Exception {
        Role role = new Role(3L, "updatedRole");
        hibernateRoleService.update(role);
        assertEquals(3, databaseTester.getConnection().createDataSet()
                .getTable("role").getRowCount());
        assertEquals("Test update user ", databaseTester.getConnection()
                .createDataSet().getTable("role")
                .getValue(2, "name"), role.getName());
    }

    @Test
    public void testFindByName() throws Exception {
        String roleName = String.valueOf(databaseTester.getConnection().createDataSet().
                getTable("role").getValue(1, "name"));
        Role role = hibernateRoleService.findByName(roleName);
        assertNotNull(role);
        assertEquals(new Long(2), role.getId());
        assertEquals(roleName, role.getName());
    }

}

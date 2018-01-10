package com.holystone.controller;

/**
 * Copyright (c) 2015, HOLYSTONE Technologies, Inc.
 * All right reserved.
 *
 * @desc:
 * @author:fengt
 * @date:2018/1/9
 * @Time:下午6:06
 */

import com.holystone.model.Address;
import com.holystone.model.Customer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Controller注解：采用注解的方式，可以明确地定义该类为处理请求的Controller类；
 */

@Controller
public class IndexController {

    private final static Logger logger = Logger.getLogger(IndexController.class);
    /**
     * @return 处理完该请求后返回的页面，此请求返回 index.jsp页面
     * @RequestMapping()注解：用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post；
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        logger.debug("index start");
        logger.debug("index end");
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        logger.debug("home start");
        logger.debug("home end");
        return "home";
    }

    /**
     * InitialContext()的理解
     * Context initial = new InitialContext();
       Object objref = initial.lookup("java:comp/env/ejb/SimpleConverter");

     * @param response
     */
    @RequestMapping(value = "/datasource", method = RequestMethod.GET)
    public void datasource(HttpServletResponse response) {
        logger.debug("datasource start");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try{
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/DSTest");
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from act_ge_property limit 10");
            String result=this.returnResultSet(rs);
            PrintWriter pw = response.getWriter();
            pw.write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.debug("datasource end");
    }

    /**
     *
     * @param response
     */
    @RequestMapping(value = "/createDb", method = RequestMethod.GET)
    public void createDb(HttpServletResponse response) {
        logger.debug("createDb start");
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("eclipselink");
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            for (int i = 0; i < 3; i++) {
                Customer customer = new Customer();
                customer.setName("customer" + i);
                customer.setEmail("customer" + i + "@my.com");
                customer.setBirthday(Calendar.getInstance().getTime());
                em.persist(customer);

                Address addressHome = new Address();
                addressHome.setName("Home");
                addressHome.setCustomer(customer);
                em.persist(addressHome);

                Address addressOffice = new Address();
                addressOffice.setName("Office");
                addressOffice.setCustomer(customer);
                em.persist(addressOffice);

            }
            em.getTransaction().commit();
            em.close();


//            em = factory.createEntityManager();
//            String sql = "select c from Customer c";
//            TypedQuery<Customer> q = em.createQuery(sql, Customer.class);
//            List<Customer> customers = q.getResultList();
//            for(Customer c: customers) {
//                System.out.println(c);
//            }
//            em.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.debug("createDb end");
    }

    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private String returnResultSet(ResultSet resultSet) throws SQLException {
               ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int num = resultSetMetaData.getColumnCount();
                StringBuffer sb = new StringBuffer();
                while (resultSet.next()) {
                     for (int i = 1; i <= num; i++) {
                         sb.append(resultSetMetaData.getCatalogName(i) + " "
                                 + resultSet.getString(i)+"<br/>");
                     }
                 }
        return sb.toString();
    }


}

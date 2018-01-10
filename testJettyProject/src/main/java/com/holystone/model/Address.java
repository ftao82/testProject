package com.holystone.model;

import javax.persistence.*;

/**
 * Copyright (c) 2015, HOLYSTONE Technologies, Inc.
 * All right reserved.
 *
 * @desc:
 * @author:fengt
 * @date:2018/1/10
 * @Time:上午11:45
 */
@Entity
public class Address {
    /**
     * jpa 表 生成器 generationtype.table
     *              将当前主键的值单独保存到一个数据库的表中，
     *              主键的值每次都是从指定的表中查询来获得，
     *              这种生成主键的方式也是很常用的。
     *              这种方法生成主键的策略可以适用于任何的数据库，
     *              不必担心不同数据库不兼容造成的问题。
     *              使用以下sql脚本创建一个表“tb_generator”，并插入两条数据，sql脚本如下所示。

     create table  tb_generator (

     id int(20) unsigned not null auto_increment,

     gen_name varchar(255) not null,

     gen_value int(20) not null,

     primary key  (id)

     )
     insert into tb_generator ( gen_name ,gen_value ) values ( 'customer_pk',1);

     insert into tb_generator ( gen_name ,gen_value ) values ( 'contact_pk',100);
     *                             SEQUENCE
     *                             IDENTITY
     *                             AUTO
     *                             Hibernate 能够出色地自动生成主键。Hibernate/EBJ 3 注释也可以为主键的自动生成提供丰富的支持，允许实现各种策略。
     其生成规则由@GeneratedValue设定的.这里的@id和@GeneratedValue都是JPA的标准用法, JPA提供四种标准用法,由@GeneratedValue的源代码可以明显看出.
     JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO.
     TABLE：使用一个特定的数据库表格来保存主键。
     SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
     IDENTITY：主键由数据库自动生成（主要是自动增长型）
     AUTO：主键由程序控制。
     在指定主键时，如果不指定主键生成策略，默认为AUTO。
     @Id
     相当于
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)

     identity:
     使用SQL Server 和 MySQL 的自增字段，这个方法不能放到 Oracle 中，Oracle 不支持自增字段，要设定sequence（MySQL 和 SQL Server 中很常用）。
     Oracle就要采用sequence了.

     同时,也可采用uuid,native等其它策略.(相关用法,上网查询)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToOne(cascade=CascadeType.ALL)
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this);
    }
}

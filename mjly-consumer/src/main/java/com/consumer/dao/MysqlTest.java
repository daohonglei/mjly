package com.consumer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class MysqlTest {
	public void test(String text,Connection conn) throws SQLException {
		PreparedStatement st=conn.prepareStatement("insert into mytable (text,name,age,address,a,b) values (?,?,?,?,?,?);");
		st.setString(1, "test");
		st.setBytes(2, "name name".getBytes());
		st.setString(3, "age  age   age  age  age");
		st.setString(4, "address address address");
		st.setString(5, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		st.setString(6, "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		st.execute();
		if(st!=null)st.close();
	}
}

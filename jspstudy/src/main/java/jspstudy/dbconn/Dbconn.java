package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	//접속정보
		String url="jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";
		String user="root";
		String password = "1234";		
		
		public Connection getConnection() {
			Connection conn = null;
			try {
			//해당패키지에 있는 클래스를 가져온다
			Class.forName("com.mysql.cj.jdbc.Driver");
			//접속정보를 활용해서 연결객체를 만든다
			conn = DriverManager.getConnection(url, user, password);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return conn;
		}
}

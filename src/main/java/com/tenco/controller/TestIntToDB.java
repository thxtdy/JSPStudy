package com.tenco.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.catalina.connector.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/temp")
public class TestIntToDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection conn = null; // 모든 메소드에서 Connection을 사용할거야.....ㅠ
    public TestIntToDB() {

    }
    @Override
    public void init() throws ServletException {
    	// init() 메소드는 Servlet의 생명 주기 중 단 한번만 동작하기 때문에 이때 DataBase와 연결하는게 적절하다고 생각한다.
    	System.out.println("init 작동");
    	
		String url = "jdbc:mysql://localhost:3306/db_todo?serverTimezone=Asia/Seoul";
		String username = "root";
		String password = "asd123";
    	
    	try { 
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			throw new ServletException("JDBC 드라이버를 찾을 수 없습니다", e);
		}
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String method = request.getMethod();
    	System.out.println(method);
    	
    	if(method.equals("GET")) {
    		doGet(request, response);
    	} else if (method.equals("POST")) {
    		doPost(request, response);
    	}
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	
    	String locate = "/testServletText.html";
    	InputStream inputStream = getServletContext().getResourceAsStream(locate);
    	
    	if(inputStream == null) {
    		response.getWriter().write("찾을 수 없는 파일입니다. 404");
    	}
    	// 기억하자 단일 스레드 환경에서는 StringBuilder // 멀티 스레드 환경에서는 StringBuffer
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    	StringBuffer buffer = new StringBuffer();
    	String line = new String();
    	while((line = reader.readLine()) != null) {
    		buffer.append(line);
    	}
    	reader.close();
    	response.getWriter().print(buffer.toString());
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		
		try { 
			
			String query = " INSERT INTO test_table(name, age, email) VALUES (?, ?, ?) ";
			PreparedStatement ptmt = conn.prepareStatement(query);
			conn.setAutoCommit(false);
			
			ptmt.setString(1, name);
			ptmt.setInt(2, age);
			ptmt.setString(3, email);
			
			int rowCount = ptmt.executeUpdate();
			if(rowCount > 0) {
				conn.commit();
				System.out.println("commit complete");
			} else {
				conn.rollback();
				System.out.println("commit fail");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // 콘솔창에서 오류를 찾기 위함
			response.getWriter().print("오류 발생"); // Client 측에게 보내는 오류 메시지
		}
		response.getWriter().print("complete");
		
	}

}

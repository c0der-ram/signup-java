package com.ducat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection con = null;
        HttpSession session = request.getSession();
       RequestDispatcher dispatcher = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice?useSSL=false", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from signup where email = ? and password = ?");  //table col name must be same in this query
			ps.setString(1, email);
			ps.setString(2, password);
			
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			session.setAttribute("email", rs.getString("email"));  //get data from database use col name
			dispatcher = request.getRequestDispatcher("welcome.jsp");
		}else {
			request.setAttribute("status", "failed");
			dispatcher = request.getRequestDispatcher("login.jsp");
		}
		 dispatcher.forward(request, response);
		 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

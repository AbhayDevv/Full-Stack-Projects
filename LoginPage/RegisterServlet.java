package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String  query="INSERT INTO BookData(ID,BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		//GET BOOK INFO
		String Bookid=req.getParameter("BookId");
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		String bookPrice=req.getParameter("bookPrice");
		
		
		
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","djalok123");
				PreparedStatement ps=con.prepareStatement(query);)      {
			ps.setString(1, Bookid);
			ps.setString(2, bookName);
			ps.setString(3, bookEdition);
			ps.setString(4, bookPrice);
			
		
			int count =ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is Registered Succesfully");
			}
			else {
				pw.println("<h2>Record is not Registered Succesfully");
			}
		
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>" +se.getMessage()+"</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>" +e.getMessage()+"</h2>");
	}
		pw.println("<a href='Home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='booklList'>Book List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}

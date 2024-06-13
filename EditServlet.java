package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	private static final String  query="UPDATE BookData set BOOKNAME=?,BOOKEDITION=?,BOOKPRICE=? where id=? ";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		//GET BOOK INFO
		int id=Integer.parseInt(req.getParameter("id"));
		//GET EDIT DATA
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
		        ps.setString(1, bookName);
		        ps.setString(2, bookEdition);
		        ps.setString(3, bookPrice);
		        ps.setInt(4, id);
		        int count=ps.executeUpdate();
		        if(count==1) {
		        	pw.println("<h1>RECORD IS EDITED SUCCESFULLY</h1>");
		        }
		        else {
		        	pw.println("<h1>RECORD IS NOT EDITED SUCCESFULLY</h1>");
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

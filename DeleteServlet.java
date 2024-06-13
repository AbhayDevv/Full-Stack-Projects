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

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String  query="delete from BookData  where id=? ";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		//GET BOOK INFO
		int id=Integer.parseInt(req.getParameter("id"));
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","djalok123");
				PreparedStatement ps=con.prepareStatement(query);)      {
		       ps.setInt(1, id);
		       
		        int count=ps.executeUpdate();
		        if(count==1) {
		        	pw.println("<h2>RECORD IS DELETED SUCCESFULLY</h2>");
		        }
		        else {
		        	pw.println("<h2>RECORD IS NOT DELETED SUCCESFULLY</h2>");
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

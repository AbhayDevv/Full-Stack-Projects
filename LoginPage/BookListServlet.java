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

@WebServlet("/booklList")
public class BookListServlet extends HttpServlet {
	private static final String  query="SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BookData";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		//GET BOOK INFO
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","djalok123");
				PreparedStatement ps=con.prepareStatement(query);)      {
			ResultSet rs=ps.executeQuery();
			pw.println("<table border='1' align='center'>");
			pw.println("<tr>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>EDIT</th>");
			pw.println("<th>DELETE</th>");
			pw.println("</tr>");
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+ rs.getString(1) +"</td>");
				pw.println("<td>"+ rs.getString(2) +"</td>");
				pw.println("<td>"+ rs.getString(3) +"</td>");
				pw.println("<td>"+ rs.getString(4) +"</td>");
				pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>EDIT</a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>DELETE</a></td>");
				
                 pw.println("</tr>");
			}
			pw.println("</table>");
			
		
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>" +se.getMessage()+"</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>" +e.getMessage()+"</h2>");
	}
		pw.println("<a href='Home.html'>Home</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}

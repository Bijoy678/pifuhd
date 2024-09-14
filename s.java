package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
/*
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/conn"})
public class login extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("tname");
        String password = request.getParameter("pwd");
        
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lodin", "root", "");

            // Prepare SQL query with placeholders
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM login WHERE user = ? AND password = ?");
            ps.setString(1, id);
            ps.setString(2, password);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // If user exists, perform actions (you can add further logic here)
                System.out.println("Login successful!");
                // For example: forward to a welcome page
                 RequestDispatcher rd = request.getRequestDispatcher("/welcome.jsp");
                rd.forward(request, response);
                
            } else {
                // If user does not exist, print a message
                System.out.println("Login failed!");
            }

            // Close resources
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            // Handle exceptions and print the error message
            System.out.println("Error: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/fill.jsp");
            rd.forward(request, response);
            
        }
    }
}

   
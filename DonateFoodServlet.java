import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/donate")
public class DonateFoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userId");

        String foodType = req.getParameter("foodType");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String location = req.getParameter("location");
        String expiry = req.getParameter("expiry");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO donations(user_id,food_type,quantity,location,expiry_time,status) VALUES(?,?,?,?,?,?)"
            );
            ps.setInt(1, userId);
            ps.setString(2, foodType);
            ps.setInt(3, quantity);
            ps.setString(4, location);
            ps.setString(5, expiry);
            ps.setString(6, "Available");
            ps.executeUpdate();

            res.sendRedirect("donor.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


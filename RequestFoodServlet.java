import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/requestFood")
public class RequestFoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        int receiverId = (int) session.getAttribute("userId");
        int donationId = Integer.parseInt(req.getParameter("donationId"));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO requests(donation_id,receiver_id,request_status) VALUES(?,?,?)"
            );
            ps.setInt(1, donationId);
            ps.setInt(2, receiverId);
            ps.setString(3, "Pending");
            ps.executeUpdate();

            res.sendRedirect("receiver.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example;

/**
 * Created by omatikaya on 22/12/2016.
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class SrvltCalculator
 */
@WebServlet("/AllData")
public class AllData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String offset = request.getParameter("offset") != null ? request.getParameter("offset") : "10";

        double value_a = 0;
        double value_b = 0;

        boolean noError = true;

        try {
            int offset1 = Integer.parseInt(offset);
        }
        catch ( Exception ex ) {
            noError = false;
        }


        if ( noError ) {
            doSetResult( response, offset );
            return;
        }


        doSetError( response);
    }

    protected void doSetResult( HttpServletResponse response, String offset ) throws UnsupportedEncodingException, IOException {
        String header  = "<tr><th>id</th><th>customer_id</th><th>order_date</th>" +
                "<th>item_id</th><th>order_status</th><th>size</th><th>price</th><th>quantity</th>" +
                "<th>tracking_number</th><th>departure_date</th><th>comment</th>" +
                "</tr>";

        String sql = "Select * from `order` where order_status != 'Deleted' limit 0, " + offset;
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        String data = "";
        try {
            ResultSet rs = jdbcTemplate.query(sql);
            while (rs.next()) {
                data += "<tr>";
                for (int i = 1; i <= 11; i++) {
                    data += "<td>" + rs.getString(i) + "</td>";
                }
                data += "</tr>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            data = "<tr></tr>";
        }
        String reply = "<html>\n" +
                "<body>\n" +
                "    <h1 align=center>Все данные</h1>\n" +
                "    <table border=2  align=center>\n"  +
                header + data +
                "    </table>" +
                "</body>\n" +
                "</html>";
        response.getOutputStream().write( reply.getBytes("UTF-8") );
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus( HttpServletResponse.SC_OK );
    }

    protected void doSetError( HttpServletResponse response) throws IOException {
        String reply = "{\"error\":1}";
        response.getOutputStream().write( reply.getBytes("UTF-8") );
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus( HttpServletResponse.SC_OK );
    }

    protected double functionSum( double a, double b ) {
        return a + b;
    }

    protected double functionDif( double a, double b ) {
        return a - b;
    }

    protected double functionMul( double a, double b ) {
        return a * b;
    }

    protected double functionDiv( double a, double b ) {
        return a / b;
    }

}
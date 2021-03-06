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
        String limit = request.getParameter("limit") != null ? request.getParameter("limit") : "100";

        boolean noError = true;

        try {
            int offset1 = Integer.parseInt(limit);
        }
        catch ( Exception ex ) {
            noError = false;
        }


        if ( noError ) {
            doSetResult( response, limit );
            return;
        }


        doSetError( response);
    }

    protected void doSetResult( HttpServletResponse response, String limit ) throws UnsupportedEncodingException, IOException {
        String header  = "<tr><th>id</th><th>customer_id</th><th>order_date</th>" +
                "<th>item_id</th><th>order_status</th><th>size</th><th>price</th><th>quantity</th>" +
                "<th>tracking_number</th><th>departure_date</th><th>comment</th>" +
                "<th></th><th></th>" +
                "</tr>";

        String sql = "Select * from `order` where order_status != 'Deleted' limit  " + limit;
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        String data = "";
        try {
            ResultSet rs = jdbcTemplate.query(sql);
            while (rs.next()) {
                data += "<tr>";
                for (int i = 1; i <= 11; i++) {
                    data += "<td>" + rs.getString(i) + "</td>";
                }
                int id = rs.getInt("order_id");
                data += "<td><a href=./delete?id=" + id + ">Удалить</a></td>";
                data += "<td><a href=./update?id=" + id + ">Редактировать</a></td>";
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
                "  <p align=center > <a href=./add>Добавить заказ</a>" +
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
}
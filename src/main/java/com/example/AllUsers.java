package com.example;

/**
 * Created by omatikaya on 22/12/2016.
 */

import javax.servlet.ServletException;
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
public class AllUsers extends HttpServlet {
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
        String header  = "<tr><th>customer_id</th><th>fio</th>" +
                "<th>email</th><th>phone</th><th>address</th>" +
                "<th></th><th></th><th></th>" +
                "</tr>";

        String sql = "Select * from `customers` limit  " + limit;
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        String data = "";
        try {
            ResultSet rs = jdbcTemplate.query(sql);
            while (rs.next()) {
                data += "<tr>";
                for (int i = 1; i <= 5; i++) {
                    data += "<td>" + rs.getString(i) + "</td>";
                }
                int id = rs.getInt("customer_id");
                data += "<td><a href=./delete_customer?id=" + id + ">Удалить</a></td>";
                data += "<td><a href=./update_customer?id=" + id + ">Редактировать</a></td>";
                data += "<td><a href=./all_customer_orders?id=" + id + ">Заказы покупателя</a></td>";
                data += "</tr>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            data = "<tr></tr>";
        }
        String reply = "<html>\n" +
                "<body>\n" +
                "    <h1 align=center>Все покупатели</h1>\n" +
                "    <table border=2  align=center>\n"  +
                header + data +
                "    </table>" +
                "  <p align=center > <a href=./add_customer>Добавить нового покупателя</a>" +
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
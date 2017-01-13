package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by omatikaya on 23/12/2016.
 */
public class UpdateCustomer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customer_id = Integer.parseInt(req.getParameter("id"));
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        ResultSet rs = null;
        try {
            rs = jdbcTemplate.query("Select * from `customers` where customer_id = " + customer_id);
        } catch (SQLException e) {
            doSetError(resp);
        }

        String form = null;
        try {
            rs.next();
            form = "  <form align=center>" +
                    "   <p>customer_id</p><p><input name=\"customer_id\" value=\"" + rs.getString("customer_id") + "\" readonly></p>" +
                    "   <p>fio</p><p><input name=\"fio\" value=\"" + rs.getString("fio") + "\"></p>" +
                    "   <p>email</p><p><input name=\"email\" value=\"" + rs.getString("email") + "\"></p>" +
                    "   <p>phone</p><p><input name=\"phone\" value=\"" + rs.getString("phone") + "\"></p>" +
                    "   <p>address</p><p><input name=\"address\" value=\"" + rs.getString("address") + "\"></p>" +
                    "   <p><input type=\"submit\" formmethod=\"post\"></p>\n" +
                    "  </form>";
        } catch (SQLException e) {
            doSetError(resp);
            return;
        }
        String reply = "<html><body><h1 align=center>Редактировать покупателя № " + customer_id + "</h1>" +
                form +
                "</body></html>";
        resp.getOutputStream().write(reply.getBytes("UTF-8"));
        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean hasErrors = false;
        int customer_id = 0;
        String fio = "";
        String email = "";
        String phone = "";
        String address = "";
        try {
            customer_id = Integer.parseInt(req.getParameter("customer_id"));

            fio = req.getParameter("fio");
            if (fio.length() < 1) {
                hasErrors = true;
            }

            email = req.getParameter("email");
            if (email.length() < 1) {
                hasErrors = true;
            }

            phone = req.getParameter("phone");
            if (phone.length() < 1) {
                hasErrors = true;
            }

            address = req.getParameter("address");
            if (address.length() < 1) {
                hasErrors = true;
            }
        } catch (Exception e) {
            hasErrors = true;
        }

        if (hasErrors) {
            doSetError(resp);
            return;
        }

        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        String sql = "Update `customers` set fio =  \"" + fio + "\", email = \"" + email +
                "\", phone = \"" + phone + "\" , address = \"" + address +  "\" where customer_id = " + customer_id;
        try {
            jdbcTemplate.insert(sql);
        } catch (SQLException e) {
            hasErrors = true;
        }


        if (hasErrors) {
            doSetError(resp);
            return;
        }


        String reply = "<html><head><meta http-equiv=refresh content=\"1;url=http://localhost:8080/customers\"></head></html>";
        resp.getOutputStream().write(reply.getBytes("UTF-8"));
        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doSetError(HttpServletResponse response) throws IOException {
        String reply = "{\"error\":1}";
        response.getOutputStream().write(reply.getBytes("UTF-8"));
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

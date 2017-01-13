package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by omatikaya on 23/12/2016.
 */
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String form = "  <form align=center>" +
                "   <p><input name=\"fio\" placeholder=\"fio\"></p>" +
                "   <p><input name=\"email\" placeholder=\"email\"></p>" +
                "   <p><input name=\"phone\" placeholder=\"phone\"></p>" +
                "   <p><input name=\"address\" placeholder=\"address\"></p>" +
                "   <p><input type=\"submit\" formmethod=\"post\"></p>\n" +
                "  </form>";
        String reply = "<html><body><h1 align=center>Добавить нового покупателя</h1>" +
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
        String fio = "";
        String email = "";
        String phone = "";
        String address = "";
        try {
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

        String sql = "Insert into `customers` (fio, email, phone, address)" +
                " values (\"" + fio + "\", \"" + email + "\", " + phone + ", \"" + address + "\")";
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


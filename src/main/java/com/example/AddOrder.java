package com.example;

import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by omatikaya on 23/12/2016.
 */
public class AddOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String form = "  <form align=center>" +
                "   <p><input name=\"customer\" placeholder=\"customer\"></p>" +
                "   <p><input name=\"item_id\" placeholder=\"item_id\" min=0></p>" +
                "   <p><input name=\"size\" placeholder=\"size\" min=0></p>" +
                "   <p><input name=\"price\" placeholder=\"price\" min=0></p>" +
                "   <p><input name=\"quantity\" placeholder=\"quantity\" min=0></p>" +
                "   <p><input name=\"comment\" placeholder=\"comment\"></p>" +
                "   <p><input type=\"submit\" formmethod=\"post\"></p>\n" +
                "  </form>";
        String reply = "<html><body><h1 align=center>Добавить новый заказ</h1>" +
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
        String customer = "";
        int item_id = 0;
        int size = 0;
        double price = 0;
        int quantity = 0;
        String comment = "";
        try {
            customer = req.getParameter("customer");
            if (customer.length() < 1) {
                hasErrors = true;
            }

            item_id = Integer.parseInt(req.getParameter("item_id"));
            if (item_id < 1) {
                hasErrors = true;
            }

            size = Integer.parseInt(req.getParameter("size"));
            if (size < 1) {
                hasErrors = true;
            }

            price = Double.parseDouble(req.getParameter("price"));
            if (price < 0) {
                hasErrors = true;
            }

            quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity < 1) {
                hasErrors = true;
            }

            comment = req.getParameter("comment");
        } catch (Exception e) {
            hasErrors = true;
        }

        if (hasErrors) {
            doSetError(resp);
            return;
        }

        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        DateTime dateTime = DateTime.now();
        String order_date = "" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() +
                " " + dateTime.getHourOfDay() + ":" + dateTime.getMinuteOfHour() + ":" + dateTime.getSecondOfMinute();
        String sql = "Insert into `order` (customer, order_date, item_id, order_status, size, price, quantity, comment)" +
                " values (\"" + customer + "\", \"" + order_date + "\", " + item_id + ", \"" + OrderStatus.ACCEPTED + "\", " +
                size + ", " + price + ", " + quantity + ", \"" + comment + "\")";
        try {
            jdbcTemplate.insert(sql);
        } catch (SQLException e) {
            hasErrors = true;
        }


        if (hasErrors) {
            doSetError(resp);
            return;
        }


        String reply = "<html><head><meta http-equiv=refresh content=\"1;url=http://localhost:8080/\"></head></html>";
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


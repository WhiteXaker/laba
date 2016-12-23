package com.example;

import org.joda.time.DateTime;

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
public class UpdateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int order_id = Integer.parseInt(req.getParameter("id"));
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
        ResultSet rs = null;
        try {
            rs = jdbcTemplate.query("Select * from `order` where order_id = " + order_id);
        } catch (SQLException e) {
            doSetError(resp);
        }

        String form = null;
        try {
            rs.next();
            form = "  <form align=center>" +
                    "   <p>order_id</p><p><input name=\"order_id\" value=\"" + rs.getString("order_id") + "\" readonly></p>" +
                    "   <p>customer</p><p><input name=\"customer\" value=\"" + rs.getString("customer") + "\"></p>" +
                    "   <p>order_date</p><p><input name=\"order_date\" value=\"" + rs.getString("order_date") + "\" readonly></p>" +
                    "   <p>item_id</p><p><input name=\"item_id\" value=\"" + rs.getString("item_id") + "\"></p>" +
                    "   <p>order_status</p><p><input name=\"order_status\" value=\"" + rs.getString("order_status") + "\"></p>" +
                    "   <p>size</p><p><input name=\"size\" value=\"" + rs.getString("size") + "\"></p>" +
                    "   <p>price</p><p><input name=\"price\" value=\"" + rs.getString("price") + "\"></p>" +
                    "   <p>quantity</p><p><input name=\"quantity\" value=\"" + rs.getString("quantity") + "\"></p>" +
                    "   <p>tracking_number</p><p><input name=\"tracking_number\" value=\"" + rs.getString("tracking_number") + "\"></p>" +
                    "   <p>departure_date</p><p><input name=\"departure_date\" value=\"" + rs.getString("departure_date") + "\" readonly></p>" +
                    "   <p>comment</p><p><input name=\"comment\" value=\"" + rs.getString("comment") + "\"></p>" +
                    "   <p><input type=\"submit\" formmethod=\"post\"></p>\n" +
                    "  </form>";
        } catch (SQLException e) {
            doSetError(resp);
            return;
        }
        String reply = "<html><body><h1 align=center>Редактировать заказ № " + order_id + "</h1>" +
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
        int order_id = 0;
        String customer = "";
        String order_date = "";
        String order_status = "";
        int item_id = 0;
        int size = 0;
        double price = 0;
        int quantity = 0;
        String tracking_number = "";
        String departure_date = "";
        String comment = "";
        try {
            order_id = Integer.parseInt(req.getParameter("order_id"));

            customer = req.getParameter("customer");
            if (customer.length() < 1) {
                hasErrors = true;
            }

            order_date = req.getParameter("order_date");

            item_id = Integer.parseInt(req.getParameter("item_id"));
            if (item_id < 1) {
                hasErrors = true;
            }

            order_status = OrderStatus.valueOf(req.getParameter("order_status").toUpperCase()).toString().toLowerCase();

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

            tracking_number = req.getParameter("tracking_number");

            departure_date = req.getParameter("departure_date");

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
        if (order_status.equals(OrderStatus.SENT.toString().toLowerCase()) && departure_date.equals("null")) {
            departure_date = "\"" + dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getDayOfMonth() +
                    " " + dateTime.getHourOfDay() + ":" + dateTime.getMinuteOfHour() + ":" + dateTime.getSecondOfMinute() + "\"";
        }
        String sql = "Update `order` set customer =  \"" + customer + "\", item_id = " + item_id +
                ", order_status = \"" + order_status + "\" , size = " + size + ", price = " + price + " , " +
                "quantity = " + quantity +  ", " + "tracking_number = " + tracking_number + ", departure_date = " +
                departure_date + ",  comment = \"" + comment + "\" where order_id = " + order_id;
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

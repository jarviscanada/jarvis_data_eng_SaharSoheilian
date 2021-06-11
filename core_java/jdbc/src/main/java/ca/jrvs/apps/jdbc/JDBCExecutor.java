package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;

public class JDBCExecutor {

  public static void main(String[] args) {
    BasicConfigurator.configure();
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");

    try {
      Connection connection = dcm.getConnection();
      OrderDAO orderDAO = new OrderDAO(connection);

      Order order = orderDAO.findById(1199);
      System.out.println(order);

    } catch (SQLException | RuntimeException ex) {
      dcm.logger.error("Database connection error", ex);
    }
  }
}

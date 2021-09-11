package com.movella;

import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {
    try {

      final int port = getHerokuAssignedPort();
      System.out.println(String.format("listening on port %d", port));

      port(port);

      get("/", (req, res) -> {
        return "Hello World";
      });

      get("/test", (req, res) -> {
        return "yeah";
      });
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  static int getHerokuAssignedPort() {
    String port = new ProcessBuilder().environment().get("PORT");
    return port == null ? 80 : Integer.parseInt(port);
  }

  // @RequestMapping("/db")
  // String db(Map<String, Object> model) {
  // try (Connection connection = dataSource.getConnection()) {
  // Statement stmt = connection.createStatement();
  // stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
  // stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
  // ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

  // ArrayList<String> output = new ArrayList<String>();
  // while (rs.next()) {
  // output.add("Read from DB: " + rs.getTimestamp("tick"));
  // }

  // model.put("records", output);
  // return "db";
  // } catch (Exception e) {
  // model.put("message", e.getMessage());
  // return "error";
  // }
  // }

  // @Bean
  // public DataSource dataSource() throws SQLException {
  // if (dbUrl == null || dbUrl.isEmpty()) {
  // return new HikariDataSource();
  // } else {
  // HikariConfig config = new HikariConfig();
  // config.setJdbcUrl(dbUrl);
  // return new HikariDataSource(config);
  // }
  // }

}

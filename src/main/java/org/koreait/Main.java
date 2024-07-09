package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int lastarticleId = 0;
        System.out.println("==article==");
        while (true) {
            System.out.print("명령어> ");
            String cmd = sc.nextLine().trim();
            if (cmd.length() == 0) {
                System.out.println("입력해주세요!");
            }
            if (cmd.equals("exit")) {
                break;
            }
            if (cmd.equals("article write")) {
                int id = lastarticleId + 1;
                System.out.println("==write==");

                System.out.print("제목: ");
                String title = sc.nextLine();

                System.out.print("내용: ");
                String body = sc.nextLine();

                System.out.println(id + "번째 글이 생성!");
                lastarticleId++;

                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");
                    String sql = "INSERT INTO article";
                    sql += " SET regDate = NOW(),";
                    sql += "updateDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "`body`= '" + body + "';";

                    pstmt = conn.prepareStatement(sql);

                    int affectedRow = pstmt.executeUpdate();

                    System.out.println("affectedRow : " + affectedRow);

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (cmd.startsWith("article list")) {
                System.out.println("==list==");
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                List<Article> articles = new ArrayList<>();

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    String sql = "SELECT * ";
                    sql += "FROM article ";
                    sql += "ORDER BY id DESC";

                    System.out.println(sql);
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery(sql);

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String regDate = rs.getString("regDate");
                        String updateDate = rs.getString("updateDate");
                        String title = rs.getString("title");
                        String body = rs.getString("body");
                        Article article = new Article(id, regDate, updateDate, title, body);
                        articles.add(article);
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (articles.size() == 0) {
                    System.out.println("게시글이 없습니다");
                    continue;
                }
                System.out.println("   번호    /    제목     ");
                    for (Article article : articles) {
                        System.out.printf("     %d      /    %s    \n", article.getId(), article.getTitle());
                    }
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==modify==");
                int id = 0;
                try {
                    id = Integer.parseInt(cmd.split(" ")[2]);
                }catch (Exception e) {
                    System.out.println("정수 입력");
                    continue;
                }
                System.out.println("==글 수정==");
                System.out.print("새 제목 : ");
                String title = sc.nextLine();
                System.out.print("새 내용 : ");
                String body = sc.nextLine();

                Connection conn = null;
                PreparedStatement pstmt = null;
                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공!");

                    String sql = "UPDATE article";
                    sql += " SET updateDate = NOW()";
                    if (title.length() > 0) {
                        sql += " , title = '" + title + "'";
                    }
                    if (body.length() > 0) {
                        sql += " , `body` = '" + body + "'";
                    }
                    sql += " WHERE id = " + id + ";";

                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    pstmt.executeUpdate();
                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(id + "번 게시글이 수정되었습니다");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 종료==");
        sc.close();
    }
}



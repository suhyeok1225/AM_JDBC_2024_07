package org.koreait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Article> articles = new ArrayList<>();
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
                String regDate = org.koreait.Util.getNow();
                String updateDate = regDate;
                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);
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
                if (articles.size() == 0) {
                    System.out.println("아무것도 없음");
                } else {
                    System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                    for (int i = articles.size() - 1; i >= 0; i--) {
                        Article article = articles.get(i);
                        if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                        } else {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                        }
                    }
                }
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==modify==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = articles.get(id);
                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 제목 : " + foundArticle.getTitle());
                System.out.println("기존 내용 : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);
                foundArticle.setUpdateDate(Util.getNow());
                System.out.println(id + "번 게시글이 수정되었습니다");
            } else if (cmd.startsWith("article delete")) {
                System.out.println("==delete==");
                int id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = articles.get(id);
                if (foundArticle == null) {
                    System.out.printf("해당 %d 번 게시글은 없습니다\n", id);
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 종료==");
        sc.close();
    }
}



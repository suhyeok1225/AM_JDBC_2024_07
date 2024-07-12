package org.koreait.controller;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
    Connection conn;
    Scanner sc;

    public MemberController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void doJoin() {
            String name = null;
            String loginId = null;
            String loginPw = null;
            String loginPwConfirm = null;
            System.out.println("==회원가입==");
            while (true) {
                System.out.print("로그인 아이디 : ");
                loginId = sc.nextLine().trim();
                if (loginId.length() == 0 || loginId.contains(" ")) {
                    System.out.println("다시입력");
                    continue;
                }
                SecSql sql = new SecSql();
                sql.append("SELECT COUNT(*) > 0");
                sql.append("FROM `member`");
                sql.append("WHERE loginId = ?;", loginId);

                boolean isLoginIdDup =
                        DBUtil.selectRowBooleanValue(conn, sql);
                if (isLoginIdDup) {
                    System.out.println("이미 사용중이다.");
                    continue;
                }
                break;
            }
            while (true) {
                System.out.print("비밀번호  : ");
                loginPw = sc.nextLine().trim();
                if (loginPw.length() == 0 || loginPw.contains(" ")) {
                    System.out.println("다시입력 : ");
                    continue;
                }
                boolean loginPwCheck = true;
                while (true) {
                    System.out.print("비밀번호 확인 :");
                    loginPwConfirm = sc.nextLine().trim();
                    if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                        System.out.println("다시입력 : ");
                        continue;
                    }
                    if (loginPw.equals(loginPwConfirm) == false) {
                        System.out.println("일치하지 않습니다.");
                        loginPwCheck = false;
                    }
                    break;
                }
                if (loginPwCheck) {
                    break;
                }
            }
            while (true) {
                System.out.print("이름 입력 : ");
                name = sc.nextLine().trim();
                if (name.length() == 0 || name.contains(" ")) {
                    System.out.println("다시입력 ");
                    continue;
                }
                break;
            }
            SecSql sql = new SecSql();
            sql.append("INSERT INTO `member`");
            sql.append("SET regDate = NOW(),");
            sql.append("updateDate = NOW(),");
            sql.append("loginId = ?,", loginId);
            sql.append("loginPw = ?,", loginPw);
            sql.append("`name` = ?;", name);

            int id = DBUtil.insert(conn, sql);

            System.out.println(id + "번 회원가입 되었습니다.");

        }
    }

package org.koreait;

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

                Article article = new Article(id, title, body);
                articles.add(article);
                System.out.printf("%d번째 글이 생성!\n", id);
                lastarticleId++;

            } else if (cmd.equals("article list")) {
                System.out.println("==list==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없음");
                    continue;
                }
                System.out.println("  번호  /   제목   /   내용  ");
                for (Article article : articles) {
                    System.out.printf("   %d   /    %s    /    %s    \n", article.getId(), article.getTitle(),article.getBody());

                }

            }
        }
    }
}

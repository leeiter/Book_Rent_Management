package com.biz.rent.service.book;

public class BookServiceV3 extends BookServiceV2 {

	public void bookMenu() {
		while(true) {
			System.out.println("===============================================================");
			System.out.println("빛고을 도서 정보");
			System.out.println("===============================================================");
			System.out.println("1.목록 2.검색 3.등록 4.수정 0.종료");
			System.out.println("---------------------------------------------------------------");
			System.out.print("작업할 번호를 적어주세요. >> ");
			String strMenu = scanner.nextLine();
			
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("숫자로 입력 부탁드립니다.");
				continue;
			}
			
			if(intMenu == 0) {
				return;
			} else if(intMenu == 1) {
				this.viewBookList();
			} else if(intMenu == 2) {
				this.searchBook();
			} else if(intMenu == 3) {
				this.bookInsert();
			} else if(intMenu == 4) {
				this.bookUpdate();
			} else {
				System.out.println("0 또는 1 ~ 4까지 입력해주세요.");
				continue;
			}
		}
	}
	
}
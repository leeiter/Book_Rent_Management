package com.biz.rent.service.rentbook;

public class RentBookServiceV3 extends RentBookServiceV2 {

	public void rentMenu() {
		while(true) {
			System.out.println("===============================================================");
			System.out.println("빛고을 대여 정보");
			System.out.println("===============================================================");
			System.out.println("1.목록 2.검색 3.등록 4.반납 5.삭제 0.종료");
			System.out.println("---------------------------------------------------------------");
			System.out.print("작업할 번호를 적어주세요. >> ");
			String strMenu = scanner.nextLine();
			System.out.println(strMenu);
			
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

			} else if(intMenu == 2) {

			} else if(intMenu == 3) {
				this.rentBookInsert();
			} else if(intMenu == 4) {

			} else if(intMenu == 5) {

			} else {
				System.out.println("0 또는 1 ~ 5까지 입력해주세요.");
				continue;
			}
		}
	}
	
}
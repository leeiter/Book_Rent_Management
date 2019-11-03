package com.biz.rent.service.user;

import java.util.List;

import com.biz.rent.persistence.UserDTO;

public class UserServiceV2 extends UserServiceV1 {

	public void userMenu() {
		while(true) {
			System.out.println("===============================================================");
			System.out.println("빛고을 회원 정보");
			System.out.println("===============================================================");
			System.out.println("1.목록 2.검색 3.등록 4.수정 0.종료");
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
				this.viewUserList();
			} else if(intMenu == 2) {
				this.searchUser();
			} else if(intMenu == 3) {
				this.userInsert();
			} else if(intMenu == 4) {
				this.userUpdate();
			} else {
				System.out.println("0 또는 1 ~ 4까지 입력해주세요.");
				continue;
			}
		}
	}
	
	public void searchUser() {
		System.out.println("===============================================================");
		System.out.println("빛고을 회원 검색");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("회원 검색을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		System.out.print("회원명으로 검색 하시겠습니까?(Enter:Yes)\n전화번호로 검색하시겠습니까?(아무키나 누르세요:Yes) >> ");
		String strUName = scanner.nextLine();
		
		List<UserDTO> userList = null;
		if(strUName.trim().isEmpty()) {
			System.out.print("회원명 입력 >> ");
			strUName = scanner.nextLine();
			userList = userDao.findByName(strUName);
		} else {
			System.out.print("전화번호 입력 >> ");
			strUName = scanner.nextLine();
			userList = userDao.findByTel(strUName);
		}

		System.out.println("빛고을 검색한 회원 목록");
		System.out.println("===============================================================");
		System.out.println("회원코드\t회원명\t전화번호\t주소");
		System.out.println("---------------------------------------------------------------");
		for(UserDTO dto : userList) {
			System.out.print(dto.getU_code() + "\t");
			System.out.print(dto.getU_name() + "\t");
			System.out.print(dto.getU_tel() + "\t");
			System.out.print(dto.getU_addr() + "\n");
		}
	}
	
}
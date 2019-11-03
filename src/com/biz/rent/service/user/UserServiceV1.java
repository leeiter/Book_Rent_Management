package com.biz.rent.service.user;

import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.UserDao;
import com.biz.rent.persistence.UserDTO;

public class UserServiceV1 {
	
	protected UserDao userDao;
	protected Scanner scanner;

	public UserServiceV1() {
		this.userDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(UserDao.class);
		scanner = new Scanner(System.in);
	}
	
	protected void viewUDetail(UserDTO userDTO) {
		System.out.println("회원코드 : " + userDTO.getU_code());
		System.out.println("회원명 : " + userDTO.getU_name());
		System.out.println("전화번호 : " + userDTO.getU_tel());
		System.out.println("주소 : " + userDTO.getU_addr());
	}
	
	protected void viewUDetails(UserDTO userDTO) {
		System.out.println("=====================================");
		System.out.println("이미 같은 회원이 있습니다.");
		System.out.println("-------------------------------------");
		this.viewUDetail(userDTO);
		System.out.println("=====================================");
	}
	
	public void viewUserList() {
		List<UserDTO> userList = userDao.selectAll();
		System.out.println("===============================================================");
		System.out.println("빛고을 회원 목록");
		System.out.println("===============================================================");
		System.out.println("회원코드\t회원명\t전화번호\t주소");
		System.out.println("---------------------------------------------------------------");
		for(UserDTO user : userList) {
			System.out.print(user.getU_code() + "\t");
			System.out.print(user.getU_name() + "\t");
			System.out.print(user.getU_tel() + "\t");
			System.out.print(user.getU_addr() + "\n");

		}
		System.out.println("===============================================================");
	}

	public void userInsert() {
		System.out.println("===============================================================");
		System.out.println("빛고을 회원 등록");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("회원 등록을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		String strUCode;
		while(true) {
			System.out.print("회원코드(Enter:자동생성, Make:직접입력, -Q:quit) >> ");
			strUCode = scanner.nextLine();
			if(strUCode.trim().isEmpty()) {
				String strTMPCode = userDao.getMaxUCode();
				
				int intUCode = Integer.valueOf(strTMPCode.substring(1));
				intUCode++;
				strUCode = strTMPCode.substring(0, 1);
				strUCode += String.format("%05d", intUCode);
				
				System.out.println("생성된 코드 : " + strUCode);
				System.out.print("사용하시겠습니까?(Enter:Yes, 돌아가기:아무키나 누르세요.) >> ");
				String strYesNo = scanner.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			} else if(strUCode.equals("Make")) {
				System.out.print("생성할 코드 입력 >> ");
				strUCode = scanner.nextLine();
				
				if(strUCode.length() != 6) {
					System.out.println("회원코드 길이가 맞지 않습니다.");
					continue;
				}
				
				strUCode = strUCode.toUpperCase();
				if (!strUCode.substring(0, 1).equals("S")) {
					System.out.println("회원코드는 첫글자는 S로 시작되어야 합니다.");
					continue;
				}
				
				try {
					Integer.valueOf(strUCode.substring(1));
				} catch (Exception e) {
					System.out.println("도서코드 2번째 이후는 숫자만 올 수 있습니다.");
					continue;
				}
				
				if (userDao.findById(strUCode) != null) {
					System.out.println("이미 등록된(사용중인) 코드입니다.");
					continue;
				}
				break;
			} else if (strUCode.equals("-Q")) {
				return;
			} else {
				System.out.println("Enter, Make, -Q 중에서 입력해주세요.");
				continue;
			}
		}
		
		String strUName;
		while(true) {
			System.out.print("회원명(-Q:quit) >> ");
			strUName = scanner.nextLine();
			if(strUName.equals("-Q")) break;
			
			if(strUName.trim().isEmpty()) {
				System.out.println("회원명은 반드시 입력해야 합니다.");
				continue;
			}
			
			UserDTO userDTO = userDao.findBySName(strUName);
			if(userDao.findBySName(strUName) != null) {
				this.viewUDetails(userDTO);
				continue;
			}
			break;
		}
			
		String strUTel;
		while(true) {
			System.out.print("전화번호(-Q:quit) >> ");
			strUTel = scanner.nextLine();
			if(strUTel.equals("-Q")) break;
			
			if(strUTel.trim().isEmpty()) {
				System.out.println("전화번호는 반드시 입력해야 합니다.");
				continue;
			}
			
			UserDTO userDTO = userDao.findBySName(strUTel);
			if(userDao.findBySTel(strUTel) != null) {
				this.viewUDetails(userDTO);
			}
			break;
		}

		String strUAddr;
		while(true) {
			System.out.print("주소(-Q:quit) >> ");
			strUAddr = scanner.nextLine();
			if(strUAddr.equals("-Q")) break;
			
			if(strUAddr.trim().isEmpty()) {
				System.out.println("주소는 반드시 입력해야 합니다.");
				continue;
			}
			break;
		}

		UserDTO userDTO = UserDTO.builder()
				.u_code(strUCode)
				.u_name(strUName)
				.u_tel(strUTel)
				.u_addr(strUAddr)
				.build();

		int ret = userDao.insert(userDTO);
		System.out.println("-------------------------------------------------------");
		System.out.print("이대로 등록하시겠습니까?(Enter:Yes, -Q:quit) >> ");
		String strYes = scanner.nextLine();
		if(strYes.equals("-Q")) return;
		
		if(strYes.trim().isEmpty()) {
			if(ret > 0) System.out.println("회원 등록 성공");
			else System.out.println("회원 등록 실패");
		}
	}
	
	protected UserDTO viewUDetailList(String strUCode) {
		UserDTO userDTO = userDao.findById(strUCode);
		
		if(userDTO == null) return null;
		System.out.println("===============================================================");
		System.out.printf("회원코드 : %s\n", userDTO.getU_code());
		System.out.printf("회원명 : %s\n", userDTO.getU_name());
		System.out.printf("전화번호 : %s\n", userDTO.getU_tel());
		System.out.printf("주소 : %s\n", userDTO.getU_addr());
		
		return userDTO;
	}
	
	public void userUpdate() {
		System.out.println("===============================================================");
		System.out.println("빛고을 회원 정보 수정");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("회원 정보 수정을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		while(true) {
			System.out.print("회원코드 입력(-Q:quit) >> ");
			String strUCode = scanner.nextLine();
			if(strUCode.equals("-Q")) break;
			
			if(strUCode.trim().isEmpty()) {
				System.out.println("다시 입력해주세요.");
				continue;
			} else if(strUCode.length() != 6) {
				System.out.println("회원코드 길이가 맞지 않습니다.");
				continue;
			} else if (!strUCode.substring(0, 1).equals("S")) {
				System.out.println("회원코드는 처음은 S로 시작되어야 합니다.");
				continue;
			}
			
			try {
				Integer.valueOf(strUCode.substring(1));
			} catch (Exception e) {
				System.out.println("회원코드 2번째 이후는 숫자만 올 수 있습니다.");
				continue;
			}
			
			strUCode = strUCode.toUpperCase();
			UserDTO userDTO = this.viewUDetailList(strUCode);
			
			while(true) {
				System.out.printf("변경할 회원명(%s) >> ", userDTO.getU_name());
				String strUName = scanner.nextLine();
				if(userDao.findBySName(strUName) != null) {
					System.out.println("이미 같은 회원이 있습니다.");
					continue;
				} else if(!strUName.trim().isEmpty()) {
					userDTO.setU_name(strUName);
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 전화번호(%s) >> ", userDTO.getU_tel());
				String strUTel = scanner.nextLine();
				if(userDao.findBySTel(strUTel) != null) {
					System.out.println("이미 같은 전화번호가 있습니다.");
					continue;
				} else if(!strUTel.trim().isEmpty()) {
					userDTO.setU_tel(strUTel);
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 주소(%s) >> ", userDTO.getU_addr());
				String strUAddr = scanner.nextLine();
				if(!strUAddr.trim().isEmpty()) {
					userDTO.setU_addr(strUAddr);
				}
				break;
			}
			
			System.out.println("-------------------------------------------------------");
			System.out.print("이대로 수정하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYes = scanner.nextLine();
			if(strYes.equals("-Q")) return;
			
			
			int ret = userDao.update(userDTO);
			if(strYes.trim().isEmpty()) {
				if(ret > 0) System.out.println("회원 정보 수정 성공");
				else System.out.println("회원 정보 수정 실패");
			}
			break;
		}
	} // update end
	
}
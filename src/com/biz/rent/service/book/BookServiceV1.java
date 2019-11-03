package com.biz.rent.service.book;

import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.persistence.BookDTO;

public class BookServiceV1 {
	
	protected BookDao bookDao;
	protected Scanner scanner;

	public BookServiceV1() {
		this.bookDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(BookDao.class);
		scanner = new Scanner(System.in);
	}
	
	protected void viewBDetail(BookDTO bookDTO) {
		System.out.println("도서코드 : " + bookDTO.getB_code());
		System.out.println("도서명 : " + bookDTO.getB_name());
		System.out.println("저자 : " + bookDTO.getB_auther());
		System.out.println("출판사 : " + bookDTO.getB_comp());
		System.out.println("구입연도 : " + bookDTO.getB_year());
		System.out.println("구입가격 : " + bookDTO.getB_iprice());
		System.out.println("대여가격 : " + bookDTO.getB_rprice());
	}

	protected void viewBDetails(BookDTO bookDTO) {
		System.out.println("=====================================");
		System.out.println("이미 같은 도서명이 있습니다.");
		System.out.println("-------------------------------------");
		this.viewBDetail(bookDTO);
		System.out.println("=====================================");
	}
	
	public void viewBookList() {
		List<BookDTO> bookList = bookDao.selectAll();
		System.out.println("===============================================================");
		System.out.println("빛고을 도서 목록");
		System.out.println("===============================================================");
		System.out.println("도서코드\t도서명\t저자\t출판사\t구입연도\t구입가격\t대여가격");
		System.out.println("---------------------------------------------------------------");
		for(BookDTO book : bookList) {
			System.out.print(book.getB_code() + "\t");
			System.out.print(book.getB_name() + "\t");
			System.out.print(book.getB_auther() + "\t");
			System.out.print(book.getB_comp() + "\t");
			System.out.print(book.getB_year() + "\t");
			System.out.print(book.getB_iprice() + "\t");
			System.out.print(book.getB_rprice() + "\n");
		}
		System.out.println("===============================================================");
	}
	
	public void bookInsert() {
		System.out.println("===============================================================");
		System.out.println("빛고을 도서 등록");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("도서 등록을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		String strBCode;
		while(true) {
			System.out.print("도서코드(Enter:자동생성, Make:직접입력, -Q:quit) >> ");
			strBCode = scanner.nextLine();
			if(strBCode.trim().isEmpty()) {
				String strTMPCode = bookDao.getMaxBCode();
				
				int intBCode = Integer.valueOf(strTMPCode.substring(2));
				intBCode++;
				strBCode = strTMPCode.substring(0, 2);
				strBCode += String.format("%04d", intBCode);
				
				System.out.println("생성된 코드 : " + strBCode);
				System.out.print("사용하시겠습니까?(Enter:Yes, 돌아가기:아무키나 누르세요.) >> ");
				String strYesNo = scanner.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			} else if(strBCode.equals("Make")) {
				System.out.print("생성할 코드 입력 >> ");
				strBCode = scanner.nextLine();
				
				if(strBCode.length() != 6) {
					System.out.println("도서코드 길이가 맞지 않습니다.");
					continue;
				}
				
				strBCode = strBCode.toUpperCase();
				if (!strBCode.substring(0, 2).equals("BK")) {
					System.out.println("도서코드는 처음은 BK로 시작되어야 합니다.");
					continue;
				}
				
				try {
					Integer.valueOf(strBCode.substring(2));
				} catch (Exception e) {
					System.out.println("도서코드 3번째 이후는 숫자만 올 수 있습니다.");
					continue;
				}
				
				if (bookDao.findById(strBCode) != null) {
					System.out.println("이미 등록된(사용중인) 코드입니다.");
					continue;
				}
				break;
			} else if (strBCode.equals("-Q")) {
				return;
			} else {
				System.out.println("Enter, Make, -Q 중에서 입력해주세요.");
				continue;
			}
		}
		
		String strBName;
		while(true) {
			System.out.print("도서명(-Q:quit) >> ");
			strBName = scanner.nextLine();
			if(strBName.equals("-Q")) break;
			
			if(strBName.trim().isEmpty()) {
				System.out.println("도서명은 반드시 입력해야 합니다.");
				continue;
			}
			
			BookDTO bookDTO = bookDao.findBySName(strBName);
			if(bookDao.findBySName(strBName) != null) {
				this.viewBDetails(bookDTO);
				continue;
			}
			break;
		}
		
		String strBAuther;
		while(true) {
			System.out.print("저자명(-Q:quit) >> ");
			strBAuther = scanner.nextLine();
			if(strBAuther.equals("-Q")) break;
			
			if(strBAuther.trim().isEmpty()) {
				System.out.println("저자명은 반드시 입력해야 합니다.");
				continue;
			}
			break;
		}
		
		String strBComp;
		while(true) {
			System.out.print("출판사(-Q:quit) >> ");
			strBComp = scanner.nextLine();
			if(strBComp.equals("-Q")) break;
			
			if(strBComp.trim().isEmpty()) {
				System.out.println("출판사는 반드시 입력해야 합니다.");
				continue;
			}
			break;
		}
		
		int intYear = 0;
		while(true) {
			System.out.print("구입연도(-Q:quit) >> ");
			String strBYear = scanner.nextLine();
			if(strBYear.equals("-Q")) break;
			
			try {
				intYear = Integer.valueOf(strBYear);
			} catch (Exception e) {
				System.out.println("구입연도는 반드시 숫자로 입력해야 합니다.");
				continue;
			}
			break;
		}
		
		int intIPrice = 0;
		while(true) {
			System.out.print("구입가격(-Q:quit) >> ");
			String strBIPrice = scanner.nextLine();
			if(strBIPrice.equals("-Q")) break;
			
			try {
				intIPrice = Integer.valueOf(strBIPrice);
			} catch (Exception e) {
				System.out.println("구입가격는 반드시 숫자로 입력해야 합니다.");
				continue;
			}
			break;
		}
		
		int intRPrice = 0;
		while(true) {
			System.out.print("대여가격(-Q:quit) >> ");
			String strBRPrice = scanner.nextLine();
			if(strBRPrice.equals("-Q")) break;
			
			try {
				intRPrice = Integer.valueOf(strBRPrice);
			} catch (Exception e) {
				System.out.println("대여가격는 반드시 숫자로 입력해야 합니다.");
				continue;
			}
			break;
		}
		
		BookDTO bookDTO = BookDTO.builder()
			 	.b_code(strBCode)
				.b_name(strBName)
				.b_auther(strBAuther)
				.b_comp(strBComp)
				.b_year(intYear)
				.b_iprice(intIPrice)
				.b_rprice(intRPrice)
				.build();
		
		int ret = bookDao.insert(bookDTO);
		System.out.println("-------------------------------------------------------");
		System.out.print("이대로 등록하시겠습니까?(Enter:Yes, -Q:quit) >> ");
		String strYes = scanner.nextLine();
		if(strYes.equals("-Q")) return;
		
		if(strYes.trim().isEmpty()) {
			if(ret > 0) System.out.println("도서 등록 성공");
			else System.out.println("도서 등록 실패");
		}
	}

}
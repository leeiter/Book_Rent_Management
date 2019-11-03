package com.biz.rent.service.book;

import java.util.List;

import com.biz.rent.persistence.BookDTO;

public class BookServiceV2 extends BookServiceV1 {

	protected BookDTO viewBDetailList(String strBCode) {
		BookDTO bookDTO = bookDao.findById(strBCode);
		
		if(bookDTO == null) return null;
		System.out.println("===============================================================");
		System.out.printf("도서코드 : %s\n", bookDTO.getB_code());
		System.out.printf("도서명 : %s\n", bookDTO.getB_name());
		System.out.printf("저자 : %s\n", bookDTO.getB_auther());
		System.out.printf("출판사 : %s\n", bookDTO.getB_comp());
		System.out.printf("구입연도 : %d\n", bookDTO.getB_year());
		System.out.printf("구입가격 : %d\n", bookDTO.getB_iprice());
		System.out.printf("대여가격 : %d\n", bookDTO.getB_rprice());

		return bookDTO;
	}
	
	public void bookUpdate() {
		System.out.println("===============================================================");
		System.out.println("빛고을 도서 수정");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("도서 수정을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
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
			System.out.print("도서 코드 입력(-Q:quit) >> ");
			String strBCode = scanner.nextLine();
			if(strBCode.equals("-Q")) break;
			
			if(strBCode.trim().isEmpty()) {
				System.out.println("다시 입력해주세요.");
				continue;
			} else if(strBCode.length() != 6) {
				System.out.println("도서코드 길이가 맞지 않습니다.");
				continue;
			} else if (!strBCode.substring(0, 2).equals("BK")) {
				System.out.println("도서코드는 처음은 BK로 시작되어야 합니다.");
				continue;
			}
			
			try {
				Integer.valueOf(strBCode.substring(2));
			} catch (Exception e) {
				System.out.println("도서코드 3번째 이후는 숫자만 올 수 있습니다.");
				continue;
			}

			strBCode = strBCode.toUpperCase();
			BookDTO bookDTO = this.viewBDetailList(strBCode);
			
			while(true) {
				System.out.printf("변경할 도서명(%s) >> ", bookDTO.getB_name());
				String strBName = scanner.nextLine();
				if(bookDao.findBySName(strBName) != null) {
					System.out.println("이미 등록된(사용중인) 도서명입니다.");
					continue;
				} else if(!strBName.trim().isEmpty()) {
					bookDTO.setB_name(strBName);
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 저자(%s) >> ", bookDTO.getB_auther());
				String strBAuther = scanner.nextLine();
				if(!strBAuther.trim().isEmpty()) {
					bookDTO.setB_auther(strBAuther);
				}
				break;			
			}
			
			while(true) {
				System.out.printf("변경할 출판사(%s) >> ", bookDTO.getB_comp());
				String strBComp = scanner.nextLine();
				if(!strBComp.trim().isEmpty()) {
					bookDTO.setB_comp(strBComp);
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 구입연도(%d) >> ", bookDTO.getB_year());
				String strBYear = scanner.nextLine();

				try {
					bookDTO.setB_year(Integer.valueOf(strBYear));
				} catch (Exception e) {
					if(strBYear.trim().isEmpty()) {
						break;
					} else {
						System.out.println("구입연도는 반드시 숫자로 입력해야 합니다.");
						continue;
					}
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 구입가격(%d) >> ", bookDTO.getB_iprice());
				String strBIPrice = scanner.nextLine();

				try {
					bookDTO.setB_iprice(Integer.valueOf(strBIPrice));
				} catch (Exception e) {
					if(strBIPrice.trim().isEmpty()) {
						break;
					} else {
						System.out.println("구입가격은 반드시 숫자로 입력해야 합니다.");
						continue;
					}
				}
				break;
			}
			
			while(true) {
				System.out.printf("변경할 대여가격(%d) >> ", bookDTO.getB_rprice());
				String strRPrice = scanner.nextLine();

				try {
					bookDTO.setB_rprice(Integer.valueOf(strRPrice));
				} catch (Exception e) {
					if(strRPrice.trim().isEmpty()) {
						break;
					} else {
						System.out.println("대여가격은 반드시 숫자로 입력해야 합니다.");
						continue;
					}
				}
				break;
			}
			
			System.out.println("-------------------------------------------------------");
			System.out.print("이대로 수정하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYes = scanner.nextLine();
			if(strYes.equals("-Q")) return;
			
			int ret = bookDao.update(bookDTO);
			if(strYes.trim().isEmpty()) {
				if(ret > 0) System.out.println("도서 정보 수정 성공");
				else System.out.println("도서 정보 수정 실패");
			}
			break;
		}
	}
	
	public void searchBook() {
		System.out.println("===============================================================");
		System.out.println("빛고을 도서 검색");
		System.out.println("---------------------------------------------------------------");
		
		while(true) {
			System.out.print("도서 검색을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		System.out.print("도서명으로 검색 하시겠습니까?(Enter:Yes)\n저자로 검색하시겠습니까?(아무키나 누르세요:Yes) >> ");
		String strBName = scanner.nextLine();
		
		List<BookDTO> bookList = null;
		if(strBName.trim().isEmpty()) {
			System.out.print("도서명 입력 >> ");
			strBName = scanner.nextLine();
			bookList = bookDao.findByName(strBName);
		} else {
			System.out.print("저자 입력 >> ");
			strBName = scanner.nextLine();
			bookList = bookDao.findByAuther(strBName);
		}

		System.out.println("빛고을 검색한 도서 목록");
		System.out.println("===============================================================");
		System.out.println("도서코드\t도서명\t저자\t출판사\t구입연도\t구입가격\t대여가격");
		System.out.println("---------------------------------------------------------------");
		for(BookDTO dto : bookList) {
			System.out.print(dto.getB_code() + "\t");
			System.out.print(dto.getB_name() + "\t");
			System.out.print(dto.getB_auther() + "\t");
			System.out.print(dto.getB_comp() + "\t");
			System.out.print(dto.getB_year() + "\t");
			System.out.print(dto.getB_iprice() + "\t");
			System.out.print(dto.getB_rprice() + "\n");
		}
	}
	
}
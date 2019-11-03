package com.biz.rent.service.rentbook;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.dao.RentBookDao;
import com.biz.rent.dao.UserDao;
import com.biz.rent.persistence.BookDTO;
import com.biz.rent.persistence.RentBookDTO;
import com.biz.rent.persistence.UserDTO;


public class RentBookServiceV1 {

	protected BookDao bookDao;
	protected UserDao userDao;
	protected RentBookDao rentBookDao;
	protected Scanner scanner;
	
	public RentBookServiceV1() {
		this.bookDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(BookDao.class);
		this.userDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(UserDao.class);
		this.rentBookDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(RentBookDao.class);
		scanner = new Scanner(System.in);
	}
	
	public void rentBookInsert() {
		System.out.println("===============================================================");
		System.out.println("빛고을 도서 대여 등록");
		System.out.println("---------------------------------------------------------------");
		
		RentBookDTO rentBookDTO = new RentBookDTO();
		while(true) {
			System.out.print("도서 대여 등록을 하시겠습니까?(Enter:Yes, -Q:quit) >> ");
			String strYesNO = scanner.nextLine();
			if(strYesNO.equals("-Q")) break;
			
			if(strYesNO.trim().isEmpty()) {
				break;
			} else {
				System.out.println("Enter 또는 -Q를 눌러주세요.");
				continue;
			}
		}
		
		String strBName;
		while(true) {
			System.out.print("도서명 검색(Q:quit) >> ");
			strBName = scanner.nextLine();
			if(strBName.equals("-Q")) break;
			
			if(strBName.trim().isEmpty()) {
				System.out.println("도서명을 입력해주세요.");
				continue;
			}
						
			List<BookDTO> bookList = bookDao.findByName(strBName);
			if(bookList.size() < 1) {
				System.out.println("검색한 도서 결과가 없습니다. 다시 입력해주세요.");
				continue;
			} else {
				this.viewBookList(bookList);
				break;
			}
		}
		
		String strBCode;
		while(true) {
			System.out.print("도서 코드 입력(Q:quit) >> ");
			strBCode = scanner.nextLine();
			if(strBCode.equals("-Q")) break;
			
			
			if(strBCode.trim().isEmpty()) {
				System.out.println("도서 코드를 입력해주세요.");
				continue;
			}
			
			BookDTO bookDTO = bookDao.findById(strBCode);
			if(bookDTO == null) {
				System.out.println("등록되지 않은 코드입니다.");
				continue;
			}
			
			rentBookDTO = rentBookDao.returnRent(strBCode);
			if(rentBookDTO != null) {
				System.out.println("이미 대출된 도서입니다.");
				this.viewRDetail(rentBookDTO);
				continue;
			}
			
			rentBookDTO.setRent_bcode(strBCode);
			break;
		}
		
		while(true) {
			System.out.print("회원명 검색(Q:quit) >> ");
			String strUName = scanner.nextLine();
			if(strUName.equals("-Q")) break;
			
			List<UserDTO> userList = userDao.findByName(strUName);
			if(userList.size() < 1) {
				System.out.println("검색 결과가 없습니다. 다시 입력해주세요.");
				continue;
			} else {
				this.viewUserList(userList);
				break;
			}
		}
		
		String strUCode;
		while(true) {
			System.out.print("회원코드 검색(Q:quit) >> ");
			strUCode = scanner.nextLine();
			if(strUCode.equals("-Q")) break;
			
			UserDTO userDTO = userDao.findById(strUCode);
			if(userDTO == null) {
				System.out.print("검색 결과가 없습니다. 다시 입력해주세요.");
				// 검색 결과가 없으면 입력한 코드로 입력할 것인지 물어보고
				// 한다고 하면 회원 등록을 바로 할 수 있게끔 연결해서
				// 회원 등록이 가능하게 실행하기
				// 업데이트 예정
				continue;
			}
			
			System.out.println("-----------------------------------------------------");
			this.viewUDetail(userDTO);
			System.out.println("-----------------------------------------------------");
			
			rentBookDTO.setRent_ucode(strUCode);
			break;
		}
		
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 14);
		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String rentDate = sd.format(date);
		String returnDate = sd.format(calendar.getTime());
		
		rentBookDTO.setRent_date(rentDate);
		rentBookDTO.setRent_return_date(returnDate);
		
		int ret = rentBookDao.insert(rentBookDTO);
		System.out.println("-------------------------------------------------------");
		System.out.print("이대로 등록하시겠습니까?(Enter:Yes, -Q:quit) >> ");
		String strYes = scanner.nextLine();
		if(strYes.equals("-Q")) return;
		
		if(strYes.trim().isEmpty()) {
			if(ret>0) System.out.println("도서 대여 등록 성공");
			else System.out.println("도서 대여 등록 실패");
		}
	}
		
	public void viewBookList(List<BookDTO> bookList) {
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
	
	protected void viewRentList(List<RentBookDTO> rentBookList) {
		System.out.println("==================================================================");
		System.out.println("SEQ\t대출일\t반납예정일\t도서코드\t회원코드\t도서반납여부\t포인트");
		System.out.println("------------------------------------------------------------------");
		for (RentBookDTO rentBookDTO : rentBookList) {
			System.out.print(rentBookDTO.getRent_seq() + "\t");
			System.out.print(rentBookDTO.getRent_date() + "\t");
			System.out.print(rentBookDTO.getRent_return_date() + "\t");
			System.out.print(rentBookDTO.getRent_bcode() + "\t");
			System.out.print(rentBookDTO.getRent_ucode() + "\t");
			System.out.print(rentBookDTO.getRent_retur_yn() + "\t");
			System.out.print(rentBookDTO.getRent_point() + "\n");
		}
		System.out.println("==================================================================");
	}

	protected void viewRDetail(RentBookDTO rentBookDTO) {
		System.out.println("SEQ : " + rentBookDTO.getRent_seq());
		System.out.println("대출일 : " + rentBookDTO.getRent_date());
		System.out.println("반납예정일 : " + rentBookDTO.getRent_return_date());
		System.out.println("도서코드 : " + rentBookDTO.getRent_bcode());
		System.out.println("회원코드 : " + rentBookDTO.getRent_ucode());
		System.out.println("도서반납여부 : " + rentBookDTO.getRent_retur_yn());
		System.out.println("포인트 : " + rentBookDTO.getRent_point());
	}
	
	protected void viewUDetail(UserDTO userDTO) {
		System.out.println("회원코드 : " + userDTO.getU_code());
		System.out.println("회원명 : " + userDTO.getU_name());
		System.out.println("전화번호 : " + userDTO.getU_tel());
		System.out.println("주소 : " + userDTO.getU_addr());
	}
	
	public void viewUserList(List<UserDTO> userList) {
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
	
	
}
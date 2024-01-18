package com.kh.jdbc.day04.pstmt.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day04.pstmt.member.common.JDBCTemplate;
import com.kh.jdbc.day04.pstmt.member.model.vo.Member;

public class MemberDAO {
	private JDBCTemplate jdbcTemplate;

	public MemberDAO() {
//		jdbcTemplate = new JDBCTemplate;
		jdbcTemplate = JDBCTemplate.getInstance();
	}

	// JDBC를 이용하여
	// Oracle DB에 접속하는 클래스
	// JDBC 코딩 절차

	public void insertMember(Member member) {
//		String query = "INSERT INTO MEMBER_TBL VALUES('" + member.getMemberId() + "', '" + member.getMemberPw() + "', '"
//				+ member.getMemberName() + "', '" + member.getGender() + "', " + member.getAge() + ", '"
//				+ member.getEmail() + "', '" + member.getPhone() + "', '" + member.getAddress() + "', '"
//				+ member.getHobby() + "', sysdate)";
		String query = "INSERT INTO MEMBER_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, String.valueOf(member.getGender()));
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby()); // 실행하기전 위치홀더(?) 값 세팅
			// ResultSet rset = stmt.executeQuery(query);
//			int result = stmt.executeUpdate(query); // DML의 경우 호출하는 메소드
			int result = pstmt.executeUpdate(); // pstmt는 실행할 때 전달값이 필요 없음!!
			if (result > 0) { // autocommit이 되기 때문에 안해도 됨
				// insert 성공
			} else {
				// insert 실패
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 1. 드라이버 등록
	// 2. DB 연결 생성
	// 3. 쿼리문 실행 준비[
	// 4. 쿼리문 실행 및 5. 결과 받기
	// 6. 자원해제(close())
	public void updateMember(Member member) {
//		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = '" + member.getMemberPw() + "', EMAIL = '"
//				+ member.getEmail() + "', PHONE = '" + member.getPhone() + "', ADDRESS = '" + member.getAddress()
//				+ "', HOBBY = '" + member.getHobby() + "' WHERE MEMBER_ID = '" + member.getMemberId() + "'";
		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE MEMBER_ID = ?";
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
//			int result = stmt.executeUpdate(query);
			pstmt.setString(1, member.getMemberPw());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId()); // 실행하기 전에 위치홀더에 값 세팅!
			int result = pstmt.executeUpdate(); // 실행할땐 전달값 필요없음
			if (result > 0) {
				// success
			} else {
				// fail
			}
//			stmt.close();
			pstmt.close();
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMember(String memberId) {
//		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
//			int result = stmt.executeUpdate(query); // DML의 경우 int로 받음
			pstmt.setString(1, memberId); // 실행하기 전 위치홀더 값 세팅해줘야함.
			int result = pstmt.executeUpdate(); // DML의 경우 int로 받음
			if (result > 0) { // 지금은 autocommit
				// 성공 후 커밋
			} else {
				// 실패면 롤백
			}
//			stmt.close();
			pstmt.close();
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member selectOneById(String memberId) {
//		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		Member member = null;
		try {
			Connection conn = jdbcTemplate.getConnection();
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
//			ResultSet rset = stmt.executeQuery(query);
			pstmt.setString(1, memberId); // 쿼리문 실행하기전 위치홀더 값 세팅.
			ResultSet rset = pstmt.executeQuery(); // 쿼리문 실행시 전달값 필요없음.
			if (rset.next()) {
				member = this.rsetToMember(rset);
			}
			rset.close();
//			stmt.close();
			pstmt.close();
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	// 클래스 바로 밑에 코드 못씀
	// 메소드로 감싸줘야 함
	// 메소드 안에 코드를 씀
	// 내가 필요할 때 호출해서 씀
	public List<Member> selectAll() {
		String query = "SELECT * FROM MEMBER_TBL";
		List<Member> mList = null;
		try {
			Connection conn = jdbcTemplate.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query); // 초록색 재생버튼 누름
			// rset 전부다 담겨있기 때문에 한 행씩 꺼내서 출력해줘야 함
			mList = new ArrayList<Member>(); // 누락 주의 꼭 쓰세요
			while (rset.next()) {
				Member member = this.rsetToMember(rset);
				mList.add(member);
			}
			rset.close();
			stmt.close();
//			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;
	}

	public Member selectLoginInfo(Member mOne) {
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PWD = ?"; ///// 바뀌는 부분 1
//		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + mOne.getMemberId() + "' AND MEMBER_PWD = '"
//				+ mOne.getMemberPw() + "'";
		Member member = null;
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement pstmt = null; ///// 바뀌는 부분 2
		ResultSet rset = null;
		// 싱글톤 미적용 - 쓸때마다 객체생성 필요해짐
//		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			conn = jdbcTemplate.getConnection();
			///// 바뀌는 부분 3
			pstmt = conn.prepareStatement(query); // 쿼리문을 미리 컴파일 함.
			///// 바뀌는 부분 4
			pstmt.setString(1, mOne.getMemberId()); // ?(위치홀더) 에 값을 넣는 코드
			pstmt.setString(2, mOne.getMemberPw()); // 시작은 1로 하고 마지막 수는 물음표의 개수와 같다.(물음표 = 위치홀더)
			///// 바뀌는 부분 5
			rset = pstmt.executeQuery(); // 쿼리문을 미리 컴파일하고 위치홀더 값을 세팅하고 쿼리문 실행 및 결과 받기

//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(query);
			if (rset.next()) {
				member = this.rsetToMember(rset);
			}
			rset.close();
//			stmt.close();
			pstmt.close();
//			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member;
	}

	private Member rsetToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setMemberId(rset.getString("MEMBER_ID"));
		member.setMemberPw(rset.getString("MEMBER_PWD"));
		member.setMemberName(rset.getString("MEMBER_NAME"));
		member.setGender(rset.getString("GENDER").charAt(0));
		member.setAge(rset.getInt("AGE"));
		member.setEmail(rset.getString("EMAIL"));
		member.setPhone(rset.getString("PHONE"));
		member.setAddress(rset.getString("ADDRESS"));
		member.setHobby(rset.getString("HOBBY"));
		member.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return member;
	}
}

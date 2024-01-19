package com.kh.jdbc.day05.member.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.jdbc.day05.member.model.vo.Member;

public class MemberDAO {

	private Properties prop;

	public MemberDAO() {
		prop = new Properties();
		Reader reader = null;
		try {
			reader = new FileReader("resources/query.properties");
			prop.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insertMember(Connection conn, Member member) {
		String query = prop.getProperty("insertMember");
//		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;

		try {
//			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, String.valueOf(member.getGender()));
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {
		String query = prop.getProperty("updateMember");
//		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;
		try {
//			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberPw());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		String query = prop.getProperty("deleteMember");
//		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;
		try {
//			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Member> selectAllMembers(Connection conn) {
		String query = prop.getProperty("selectAllMembers");
//		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		List<Member> mList = null;
		try {
//			conn = jdbcTemplate.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			mList = new ArrayList<Member>();
			while (rset.next()) {
				Member member = this.rsetToMember(rset);
				mList.add(member);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mList;
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

	public int selectLoginInfo(Connection conn, Member member) {
		String query = prop.getProperty("selectLoginInfo");
//		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
//			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			rset = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}

package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;


public class MemberDao {	
	//연결객체
	private Connection conn;
	//구문객체
	private PreparedStatement pstmt;
	
	public MemberDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	public int insertMember(String memberId, String memberPwd,String memberName,String memberEmail,String memberGender,String memberAddr,String memberPhone,String memberJumin,String hobby,String ip){
		int value=0;		
		
		String sql="insert into a_member(MEMBERID,MEMBERPWD,MEMBERNAME,MEMBEREMAIL,MEMBERGENDER,MEMBERADDR,MEMBERPHONE,MEMBERJUMIN,MEMBERHOBBY,MEMBERIP)" 
				+ "values(?,?,?,?,?,?,?,?,?,?)";
			
		//쿼리를 실행하기 위한 쿼리실행 클래스를 생성한다
		try{
		//	Statement stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			pstmt.setString(3, memberName);
			pstmt.setString(4, memberEmail);
			pstmt.setString(5, memberGender);
			pstmt.setString(6, memberAddr);
			pstmt.setString(7, memberPhone);
			pstmt.setString(8, memberJumin);
			pstmt.setString(9, hobby);
			pstmt.setString(10,ip);
			value = pstmt.executeUpdate();		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return value;
	}

	public ArrayList<MemberVo> memberSelectAll(){	
		//객체생성
		ArrayList<MemberVo> alist  = new ArrayList<MemberVo>();
		ResultSet rs = null;
		String sql="select * from a_member where delyn='N' order by midx desc";
		
		try{
			//Connection 객체를 통해서 문자열을 쿼리화 시킨다
			pstmt = conn.prepareStatement(sql);
			//여러 데이터를 그대로 복사해서 담는 클래스 ResultSet 
			rs = pstmt.executeQuery();
			//반복문 실행 . rs.next()는 다음 행에 값이 있는지 true 없으면 false..있으면 그 행으로 이동
			while(rs.next()){
				
				//객체생성		
				MemberVo mv = new MemberVo();
				//옮겨담고
				mv.setMidx(rs.getInt("midx"));
				mv.setMembername(rs.getString("memberName"));
				mv.setMemberphone(rs.getString("memberphone"));
				mv.setWriteday(rs.getString("writeday"));
				// 담은 mv를 alist에 추가한다
				alist.add(mv);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
			rs.close();
			pstmt.close();
			conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		
		return alist;
	}
	
	public MemberVo memberLogin(String memberId, String memberPwd) {
		
		ResultSet rs = null;
		MemberVo mv = null;
		String sql="select * from a_member where delyn='N' and memberid=? and memberpwd=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mv = new MemberVo();
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberId"));	
				mv.setMembername(rs.getString("memberName"));
			}				
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			
		}	
		
		
		return mv;
	}
	
	
	
	
	

}

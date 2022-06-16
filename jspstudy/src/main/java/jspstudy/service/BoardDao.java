package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.SearchCriteria;

public class BoardDao {
	
	Connection conn;
	PreparedStatement pstmt;
	
	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();		
	}
	
	
	public int insertBoard(String subject,String content,String writer,String ip,int midx, String filename) {
		int value=0;
		ResultSet rs = null;
		int maxbidx = 0;
				
		String sql="INSERT INTO a_board(originbidx, depth,level_,subject,content,writer,ip,midx,filename) "
				+ "VALUES(0,0,0,?,?,?,?,?,?)";		
		
		String sql2 ="select max(bidx) as maxbidx from a_board";
		
		String sql3 = "update a_board set originbidx = ? where bidx= ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, ip);
			pstmt.setInt(5, midx);
			pstmt.setString(6, filename);
			value= pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			rs  = pstmt.executeQuery();
			
			if (rs.next()) {
			 maxbidx = 	rs.getInt("maxbidx");
			}
			
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, maxbidx);
			pstmt.setInt(2, maxbidx);
			value= pstmt.executeUpdate();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		
		return value;
	}
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
		ArrayList<BoardVo> alist =new ArrayList<BoardVo>();
		ResultSet rs = null;
		String str ="";
		if (scri.getSearchType().equals("subject")) {
			str = "and subject like ? "; 
		}else {
			str = "and writer like ?";
		}
		
		String sql = "SELECT * FROM a_board WHERE delyn='N' "+str+" ORDER BY originbidx desc, depth ASC limit ?,?";		
		
		try {						
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			pstmt.setInt(2, (scri.getPage()-1)*15);
			pstmt.setInt(3, 15);
			rs= pstmt.executeQuery();
			
			//다음값이 존재하면 true이고 그 행으로 커서가 이동한다
			while(rs.next()) {
				BoardVo bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx"));		//rs에 복사된 bidx를 bv에 옮겨담는다		
				bv.setSubject(rs.getString("subject"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				bv.setLevel_(rs.getInt("level_"));
				alist.add(bv);   //각각의 bv객체를 alist에 추가한다
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
				
		return alist;
	}
	
	
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = null;
		ResultSet rs = null;
		
		String sql="select * from a_board where bidx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			rs  = pstmt.executeQuery();
			
			if (rs.next()) {				
				bv = new BoardVo();
				bv.setBidx(rs.getInt("bidx"));
				bv.setOriginbidx(rs.getInt("originbidx"));
				bv.setDepth(rs.getInt("depth"));
				bv.setLevel_(rs.getInt("level_"));
				
				bv.setSubject(rs.getString("subject"));
				bv.setContent(rs.getString("content"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getNString("writeday"));
				bv.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return bv;
	}
	
	public int updateBoard(String subject,String content,String writer,String ip,int midx,int bidx) {
		int value=0;
		
		String sql="update a_board set subject=?, content=?, writer=?, ip=?, midx=?, writeday= now() where bidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, ip);
			pstmt.setInt(5, midx);
			pstmt.setInt(6, bidx);
			value= pstmt.executeUpdate();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		
		return value;
	}
	
	public int deleteBoard(int bidx) {
		int value=0;
		
		String sql="update a_board set delyn='Y', writeday=now() where bidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value= pstmt.executeUpdate();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		
		return value;
	}
	
	public int replyBoard(BoardVo bv) {
		int value=0;
		
		String sql1="update a_board set depth=depth+1 where originbidx=? and depth > ?";
		String sql2="insert into a_board(originbidx,depth,level_,subject,content,writer,ip,midx) "
				+ "VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			//트랜잭션
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth());			
			value= pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth()+1);	
			pstmt.setInt(3, bv.getLevel_()+1);
			pstmt.setString(4, bv.getSubject());
			pstmt.setString(5, bv.getContent());
			pstmt.setString(6, bv.getWriter());
			pstmt.setString(7, bv.getIp());
			pstmt.setInt(8, bv.getMidx());
			value= pstmt.executeUpdate();	
			
			conn.commit();
			
		} catch (SQLException e) {	
			try {
				conn.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
		}		
		
		return value;
	}
	
	public int boardTotal(SearchCriteria scri) {
		int cnt = 0;
		ResultSet rs = null;
		String str ="";
		if (scri.getSearchType().equals("subject")) {
			str = "and subject like ? "; 
		}else {
			str = "and writer like ?";
		}
		
		String sql = "select count(*) as cnt from a_board where delyn='N' "+str+"";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
			 cnt = 	rs.getInt("cnt");
			}		
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			
			try {
				rs.close();
				pstmt.close();
			//	conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
				
		}
		
		
		return cnt;
	}
	
	
	
	
	
	
}

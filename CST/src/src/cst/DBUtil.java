package src.cst;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Timer;

//import prog.BookingFrame;



import javax.swing.JLabel;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
public class DBUtil{
	
	public String getMemberPresent(){
		return "present";
	}
	private static Connection conn = null;

	public DBUtil() {
		conn = getConnection();
	}

	public static Connection getConnection(){
		String dbDriver = "sun.jdbc.odbc.Jdbc OdbcDriver";
		String dbString = "jdbc:odbc:proj";// db driver 
		System.out.println(dbDriver + " and " + dbString);
		if (conn == null) {
			try {
				Class.forName(dbDriver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				conn = DriverManager.getConnection(dbString,"","");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static boolean checkLogin(Login login) {
		boolean status = false;
		try {
			String sql = "select * from Staff where Staffname = '" + Login.getUser() + "' and password = '" + Login.getPassword() + "'";
			Statement stmt = getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				status = true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public static boolean saveSale(SaleMember member,Sale sale,String[] seat,ShowSeat showseat){
		int Movieid;
		int seatId;
		int total;
		String timestamp;
		String sql1 = "select MovieId from Movie where Moviename = ?";
		try {
			PreparedStatement p = getConnection().prepareStatement(sql1);
			p.setString(1, sale.getMovie());
			ResultSet set = p.executeQuery();

			if(set.next()){
				Movieid = set.getInt(1);
				
				for (int i=0;i<seat.length;i++){
					String sql5 = "select Seat_Id from Seat where Seat_Number= ? and Show_Time=#"+showseat.getShowtime()+"#";
					PreparedStatement p5 = getConnection().prepareStatement(sql5);
					p5.setString(1, seat[i]);
					ResultSet set3 = p5.executeQuery();
					
					
					if(set3.next()){
						seatId= set3.getInt(1);
						//String movie = set3.getString(1);
						set3.close();
						p5.close();
						String sql4 = "insert into Ticket2(MovieId,Seat_Id,Price,Quantity,TicketDate,Member_Id) values("+Movieid+","+seatId+","+sale.getPrice()+","+sale.getQuantity()+","+"#"+sale.getTicketdate()+"#,"+member.getMemberId()+")";
						PreparedStatement p4 = getConnection().prepareStatement(sql4);
					
						System.out.println(sql4);
						p4.executeUpdate();
						p4.close();
						
						String sql6 = "update Seat set Occupied=yes where Show_Time=? and Seat_Id=?";
						PreparedStatement p6 = conn.prepareStatement(sql6);
						
						p6.setString(1, showseat.getShowtime());
						p6.setInt(2, seatId);
						p6.executeUpdate();
						p6.close();

					}
					
					
				}
						String sql2 = "select Total from Member where MemberId = ?";
						System.out.println(sql2);
						PreparedStatement p2 = getConnection().prepareStatement(sql2);
						p2.setInt(1, member.getMemberId());
						ResultSet set2 = p2.executeQuery();
		
						if(set2.next()){
							System.out.println("successful deposit");
							int totalamt =set2.getInt(1);
							//System.out.println(totalamt+"That is total");
							total = totalamt + member.getTotal();
							//System.out.println(total);
				
							String sql3 = "update Member set Total="+total+" where MemberId="+member.getMemberId();
							//System.out.println(sql3);
							PreparedStatement p3 = conn.prepareStatement(sql3);
							p3.executeUpdate();
							System.out.println("successful update");
							p3.close();
							//int s = Movieid - sale.getQuantity();
							/*timestamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
							String sql4 = "select Promotion_Gift from Promotion where Valid_Date>=#"+timestamp+"# and Total_Amount<="+total;
							System.out.println(sql4);
							PreparedStatement p4 = getConnection().prepareStatement(sql4);
							ResultSet set4 = p4.executeQuery();
			
							if(set4.next()){
								
							}*/
							//conn.close();
							return true;
							}
			}

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean saveSale(Sale sale,String[] seat,ShowSeat showseat){
		/*int NoOfSeat;
		int seatId;
		//System.out.println(showseat.getSeat_Number()+"Seat Number");
		//System.exit(0);
		String sql1 = "select MovieId from Movie where Moviename = ?";
		try {
			PreparedStatement p = getConnection().prepareStatement(sql1);
			p.setString(1, sale.getMovie());
			ResultSet set = p.executeQuery();

			if(set.next()){
				NoOfSeat = set.getInt(1);
				
					
							String sql5 = "select Seat_Id from Seat where Seat_Number= ?";
							PreparedStatement p5 = getConnection().prepareStatement(sql5);
							p5.setString(1, showseat.getSeat_Number());
							ResultSet set3 = p5.executeQuery();
							
							
							if(set3.next()){
								seatId= set3.getInt(1);
								//String movie = set3.getString(1);
								set3.close();
								p5.close();
								String sql4 = "insert into Ticket1(MovieId,Seat_Id,Price,Quantity,TicketDate) values("+NoOfSeat+","+seatId+","+sale.getPrice()+","+sale.getQuantity()+","+"#"+sale.getTicketdate()+"#)";
								PreparedStatement p4 = getConnection().prepareStatement(sql4);
								p4.setInt(1, 1);
								p4.setInt(2, NoOfSeat);
								p4.setInt(3, 3);
								p4.setInt(4, sale.getPrice());
								p4.setInt(5, sale.getQuantity());
								p4.setString(6,ticketdate);
								System.out.println(sql4);
								p4.executeUpdate();
								p4.close();

							}

							String sql3 = "update Member set Deposit=? where MemberId=?";
							PreparedStatement p3 = conn.prepareStatement(sql3);
							p3.setInt(1, (depo-total));
							p3.setString(2, sale.getMemberId());
							p3.executeUpdate();
							System.out.println("successful update");
							p3.close();
							int s = NoOfSeat - sale.getQuantity();

							String sql6 = "update Movie set NoOfSeat=? where Moviename=?";
							PreparedStatement p6 = conn.prepareStatement(sql6);
							p6.setInt(1, s);
							p6.setString(2, sale.getMovie());
							p6.executeUpdate();
							p6.close();
							conn.close();
							return true;
							}
					

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;*/
		int NoOfSeat;
		int seatId;
		//System.out.println(showseat.getSeat_Number()+"Seat Number");
		//System.exit(0);
		String sql1 = "select MovieId from Movie where Moviename = ?";
		try {
			PreparedStatement p = getConnection().prepareStatement(sql1);
			p.setString(1, sale.getMovie());
			ResultSet set = p.executeQuery();

			if(set.next()){
				NoOfSeat = set.getInt(1);
				
							for (int i=0;i<seat.length;i++){
								String sql5 = "select Seat_Id from Seat where Seat_Number= ? and Show_Time=#"+showseat.getShowtime()+"#";
								PreparedStatement p5 = getConnection().prepareStatement(sql5);
								p5.setString(1, seat[i]);
								ResultSet set3 = p5.executeQuery();
								
								
								if(set3.next()){
									seatId= set3.getInt(1);
									//String movie = set3.getString(1);
									set3.close();
									p5.close();
									String sql4 = "insert into Ticket2(MovieId,Seat_Id,Price,Quantity,TicketDate) values("+NoOfSeat+","+seatId+","+sale.getPrice()+","+sale.getQuantity()+","+"#"+sale.getTicketdate()+"#)";
									PreparedStatement p4 = getConnection().prepareStatement(sql4);
								
									System.out.println(sql4);
									p4.executeUpdate();
									p4.close();
									
									String sql6 = "update Seat set Occupied=yes where Show_Time=? and Seat_Id=?";
									PreparedStatement p6 = conn.prepareStatement(sql6);
									
									p6.setString(1, showseat.getShowtime());
									p6.setInt(2, seatId);
									p6.executeUpdate();
									p6.close();

								}
								
								
							}
							

						
							conn.close();
							return true;
							}
					

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	

	}

	public static ArrayList<ShowMovie> showMovie(){
		ArrayList<ShowMovie> MovieList = new ArrayList<ShowMovie>();
		try {
			String sql ="SELECT Moviename,Director,Actor,Actress,Type,Description FROM Movie";
			Statement stmt = getConnection().createStatement();
			ResultSet set = stmt.executeQuery(sql);
			while(set.next()){
				ShowMovie movie = new ShowMovie();
				movie.setName(set.getString(1));
				movie.setDirector(set.getString(2));
				movie.setActor(set.getString(3));
				movie.setActress(set.getString(4));
				movie.setType(set.getString(5));
				//movie.setPrice(set.getString(6));
				MovieList.add(movie);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MovieList;
	}
	/*public static ArrayList<ShowSeat> showseat(String showtime){
	
		ArrayList<ShowSeat> SeatList = new ArrayList<ShowSeat>();
		
		
		try {
			//String sql1 ="SELECT Seat_Id,Seat_Number FROM Seat where occupied=No and Show_Time=#"+showtime+"#";
			String sql1 ="SELECT * FROM Seat where Show_Time=#"+showtime+"#";
			System.out.println(sql1);
			Statement stmt1 = getConnection().createStatement();
			ResultSet set = stmt1.executeQuery(sql1);
			while(set.next()){
				ShowSeat seat = new ShowSeat();
				seat.setId(set.getInt(1));
				seat.setSeat_Number(set.getString(2));
				seat.setOccupied(set.getBoolean(3));
				//System.out.println(set.getInt(0));
				//System.out.println(seat.getId());
				//System.out.println(seat.getSeat_Number());
				SeatList.add(seat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return SeatList;
	}*/
	
	public static ArrayList<ShowSeat> showseat(String showtime, String lblSysDate,String moviename){
		
		ArrayList<ShowSeat> SeatList = new ArrayList<ShowSeat>();
		
		
		try {
			//String sql1 ="SELECT Seat_Id,Seat_Number FROM Seat where occupied=No and Show_Time=#"+showtime+"#";
			//String sql1 ="SELECT Seat.Seat_Id,Seat.Seat_Number FROM Ticket2 LEFT JOIN Seat ON Seat.Seat_Id=Ticket2.Seat_Id WHERE Seat.Show_Time=#"+showtime+"# and Ticket2.TicketDate=#"+lblSysDate+"# order by Seat.Seat_Id";
			String sql1="SELECT  Seat.Seat_Id,Seat.Seat_Number "+
							"FROM   (Seat INNER JOIN Ticket2 ON  Seat.Seat_Id = Ticket2.Seat_Id) "+ 
							"INNER JOIN Movie ON  Ticket2.MovieId = Movie.MovieId "+
							"WHERE Moviename='"+moviename+"' and Seat.Show_Time=#"+showtime+"# and Ticket2.TicketDate=#"+lblSysDate+"# "+
							"ORDER BY Seat.Seat_Id";
			System.out.println(sql1+"Showing Seat");
			Statement stmt1 = getConnection().createStatement();
			ResultSet set = stmt1.executeQuery(sql1);
			while(set.next()){
				ShowSeat seat = new ShowSeat();
				seat.setId(set.getInt(1));
				seat.setSeat_Number(set.getString(2));
				//seat.setOccupied(set.getBoolean(3));
				//System.out.println(set.getInt(0));
				//System.out.println(seat.getId());
				//System.out.println(seat.getSeat_Number());
				SeatList.add(seat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return SeatList;
	}
	
	public static ArrayList<ShowSeat> GetSeatALL(String showtime) throws SQLException{
		ArrayList<ShowSeat> seatAll = new ArrayList<ShowSeat>();
		
		try {
			String sql1 ="SELECT * FROM Seat where Show_Time=#"+showtime+"#";
			System.out.println(sql1);
			Statement stmt1 = getConnection().createStatement();
			ResultSet set = stmt1.executeQuery(sql1);
			
			String tmp;
			while(set.next()){
				//tmp=Integer.toString();
				//seatAll.add(set.getString(2));
				ShowSeat seat = new ShowSeat();
				seat.setId(set.getInt(1));
				seat.setSeat_Number(set.getString(2));
				seatAll.add(seat);
				/*java.sql.Array z = set.getArray("Seat_Number");
			    String[] zips = (String[])z.getArray();
			    System.out.println("Hello");
			    for (int i = 0; i < zips.length; i++) {
			        
			    	
					System.out.println(i);
					//tmp=set.getString(2);
					seatAll[i]="hello"+i;
					System.out.println(zips[i]);
					System.out.println(set.getString(2));
			        }*/
			
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return seatAll;
	}
	
	public static int GetTotalAmount(SaleMember member) throws SQLException {
		int totalamt=0;
		String sql2 = "select Total from Member where MemberId="+member.getMemberId();
		System.out.println(sql2);
		PreparedStatement p2;
		try {
			
			p2 = getConnection().prepareStatement(sql2);
			ResultSet set2 = p2.executeQuery();

			if(set2.next()){
				System.out.println("successful deposit");
				totalamt =set2.getInt(1);
				//System.out.println(totalamt+"That is total");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return totalamt;
		
	}


	public static ArrayList<Promotion> GetPromtionToalAmt(int total) {
		// TODO Auto-generated method stub
		ArrayList<Promotion> pro_amt= new ArrayList<Promotion>();
		String timestamp;
		timestamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		String sql4 = "select Promotion_Gift from Promotion where Valid_Date>=#"+timestamp+"# and Total_Amount<="+total;
		System.out.println(sql4);
		PreparedStatement p4;
		try {
			p4 = getConnection().prepareStatement(sql4);
			ResultSet set4 = p4.executeQuery();

			while(set4.next()){
				Promotion pm= new Promotion();
				pm.setPromotion_gift(set4.getString(1));
				pro_amt.add(pm);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pro_amt;
	}

	public static boolean saveMovie(ShowMovie movie) {
	
		try {
			
				String sql4 = "insert into Movie(Moviename,Director,Actor,Actress,Type,Description,Show) values("+movie.getName()+","+movie.getDirector()+","+movie.getActor()+","+movie.getActress()+","+movie.getType()+","+movie.getDescription()+",yes"+")";
				PreparedStatement p4 = getConnection().prepareStatement(sql4);
			
				System.out.println(sql4);
				p4.executeUpdate();
				p4.close();
						
				conn.close();
				return true;
						

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
		
	}
	public static boolean saveMember(SaleMember member) {
		
		try {
			
				String sql4 = "insert into Member(Membername,Address,Phone,Total) values('"+member.getMemberName()+"','"+member.getAddress()+"',"+member.getPhone()+","+member.getTotal()+")";
				PreparedStatement p4 = getConnection().prepareStatement(sql4);
			
				System.out.println(sql4);
				p4.executeUpdate();
				p4.close();
						
				conn.close();
				return true;
						

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
		
	}
public static boolean UpdateMemberBalance(String pm,SaleMember s,int totalamt) {
		int balance = 0;
		try {
				String sql3= "Select Total_Amount from Promotion where Promotion_Gift='"+pm+"'";
				System.out.println(sql3);
				PreparedStatement p3 = getConnection().prepareStatement(sql3);
				ResultSet set3 = p3.executeQuery();

				if(set3.next()){
					
					balance = totalamt - set3.getInt(1);
				}
			
				System.out.println(balance+"This is balance");
				String sql4 = "update Member set Total="+balance+" where MemberId="+s.getMemberId();
				System.out.println(sql4);
				PreparedStatement p4 = getConnection().prepareStatement(sql4);
			
				
				p4.executeUpdate();
				p4.close();
						
				conn.close();
				return true;
						

				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
		
	}
public static boolean savePromotion(Promotion pm) {
	
	try {
		
			String sql4 = "insert into Promotion(Promotion_Gift,Valid_Date,Total_Amount,Description) values('"+pm.getPromotion_gift()+"',#"+pm.getPromotion_valid_date()+"#,'"+pm.getPromotion_total_amount()+"','"+pm.getPromotion_description()+"')";
			System.out.println(sql4);
			PreparedStatement p4 = getConnection().prepareStatement(sql4);
		
			
			p4.executeUpdate();
			p4.close();
					
			conn.close();
			return true;
					

			
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;

	
}
}

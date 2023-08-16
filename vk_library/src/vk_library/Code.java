package vk_library;


import java.sql.*;
import java.util.Scanner;

public class Code {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
				
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root","pass@word1");
			
			System.out.println("Admin Login Details: ");
            boolean flag=true;
            while(flag) {
            	
	            System.out.println("Enter username: ");
	            String nm=sc.next();
	            System.out.println("Enter password: ");
	            String pwd=sc.next();
	            
	            if(nm.equals("libdtu") && pwd.equals("lib123")) {
	            	System.out.println("Logged In\n");
				
				
					while(true) {
						
						System.out.println("Welcome to Library Management System !!");
						System.out.println("1. Add Book\n2. Delete Book\n3. Update Book\n4. Search Book\n5. Issue Book\n6. Exit");
			            System.out.println("Enter choice: ");
			            int choice=sc.nextInt();
			            
			            if(choice==1) { // ADD BOOK
			            	
			            	System.out.println("Enter book id: ");
			                int n=sc.nextInt();
			                sc.nextLine();
			                System.out.println("Enter book name: ");
			                String bn=sc.nextLine();
			                System.out.println("Enter author name: ");
			                String an=sc.nextLine();
			                System.out.println("Enter quantity: ");
			                int qn=sc.nextInt();
			                sc.nextLine();
			                
			                String q1="Insert into book values(?,?,?,?);";
			                PreparedStatement p1=con.prepareStatement(q1);
			                p1.setInt(1, n);
			                p1.setString(2, bn);
			                p1.setString(3, an);
			                p1.setInt(4, qn);
			                p1.executeUpdate();
			                System.out.println("Book Added Succesfully !!\n");
			            }
			            else if(choice==2) {    //DELETE BOOK
			            	
			            	System.out.println("Enter book id to be deleted: ");
			                int n=sc.nextInt();
			                
			                String q1="Delete from book where bid=?;";
			                PreparedStatement p1=con.prepareStatement(q1);
			                p1.setInt(1, n);
			                int rows = p1.executeUpdate();
			                
			                if(rows>0) {
			                	System.out.println("Book Deleted Succesfully !!\n");
			                }
			                else {
			                	System.out.println("Book not found\n");
			                }
			                
			            }
			            else if(choice==3) {    // UPDATE BOOK
			            	
			            	System.out.println("Enter book id to be updated: ");
			                int bid=sc.nextInt();
			                System.out.println("Update quantity: ");
			                int qn=sc.nextInt();
			                sc.nextLine();
			                
			                String q1="Update book set quantity=? where bid=?;";
			                PreparedStatement p1=con.prepareStatement(q1);
			                p1.setInt(1, qn);
			                p1.setInt(2, bid);
			                int rows = p1.executeUpdate();
			                
			                if(rows>0) {
			                	System.out.println("Book Updated Succesfully !!\n");
			                }
			                else {
			                	System.out.println("Book not found\n");
			                }

			            }
			            else if(choice==4) {   // SEARCH BOOK
			            	
			            	System.out.println("Enter book id to be searched: ");
			            	int bid=sc.nextInt();
			            	
			            	String q1="Select * from book where bid=?;";
			                PreparedStatement p1=con.prepareStatement(q1);
			                p1.setInt(1, bid);
			                ResultSet rs= p1.executeQuery();
			                
			                // -----------------------------------------------------------------------------------
			                String query = "SELECT bid FROM book WHERE bid = " + bid; 
			                try{
			                        Statement statement;
			                        ResultSet resultSet;
			                        statement = con.createStatement();
			                        resultSet = statement.executeQuery(query);
			                        if(resultSet.next()){
			                        	// Display the results (for simplicity, just display the product name)
						                while (rs.next()) {
						                	int bookId= rs.getInt("bid");
						                	String bookName=rs.getString("bname");
						                	String authorName=rs.getString("author");
						                	int bookQuantity=rs.getInt("quantity");
						                                   
						                    System.out.println("BookID: " + bookId + ", BookName: " + bookName +", AuthorName: "+authorName+
						                    		", Quantity: "+bookQuantity);
						                }
						                
						                System.out.println("Book searched successfully !!!\n");
			                        }else{
			                        	System.out.println("Book Not Found !!!\n");
			                        } //Missing in your code
			                }catch(Exception e) {
			                	System.out.println(e);
			                }
			                // -----------------------------------------------------------------------------------
			                        
			                        
//			             // Display the results (for simplicity, just display the product name)
//			                while (rs.next()) {
//			                	int bookId= rs.getInt("bid");
//			                	String bookName=rs.getString("bname");
//			                	String authorName=rs.getString("author");
//			                	int bookQuantity=rs.getInt("quantity");
//			                                   
//			                    System.out.println("BookID: " + bookId + ", BookName: " + bookName +", AuthorName: "+authorName+
//			                    		", Quantity: "+bookQuantity);
//			                }
//			                
//			                System.out.println("Book searched successfully !!!\n");
			            	
			            }
			            else if(choice==5) {    // ISSUE BOOK
			            	
			            	System.out.println("Enter user id: ");
			            	int ud=sc.nextInt();
			            	
			            	// ------------------------------------------------------------------------------------------
			            	String query = "SELECT uid FROM user WHERE uid = " + ud;
			            	try{
			            	        Statement statement;
			            	        ResultSet resultSet;
			            	        statement = con.createStatement();
			            	        resultSet = statement.executeQuery(query);
			            	        if(resultSet.next()){
			            	        	System.out.println("Enter book id: ");
						            	int bd=sc.nextInt();
						            	System.out.println("Enter issue date: (YYYY-MM-DD) ");
						            	String idate=sc.next();
						            	          	
						            	// ISSUE BOOK QUERY
						            	String q1="Insert into issue(uid,bid,issuedate) values(?,?,?);";
						            	// UPDATE BOOK QUERY
						            	String q2="Update book set quantity=quantity-1 where bid=?;";
						            	
						            	PreparedStatement p1=con.prepareStatement(q1);
						            	p1.setInt(1, ud);
						            	p1.setInt(2, bd);
						            	p1.setString(3, idate);
						            	
						            	PreparedStatement p2=con.prepareStatement(q2);
						            	p2.setInt(1, bd);
						            	
						            	p1.executeUpdate();
						            	p2.executeUpdate();
						            	
						            	System.out.println("Issued book successfully!!\n");
			            	        }else{
			            	        	System.out.println("User not found\n");
			            	        } 
			            	}catch(Exception e) {
			            		System.out.println(e);
			            	}
			            	//-------------------------------------------------------------------------------------------
			            	
//			            	System.out.println("Enter book id: ");
//			            	int bd=sc.nextInt();
//			            	System.out.println("Enter issue date: (YYYY-MM-DD) ");
//			            	String idate=sc.next();
//			            	          	
//			            	// ISSUE BOOK QUERY
//			            	String q1="Insert into issue(uid,bid,issuedate) values(?,?,?);";
//			            	// UPDATE BOOK QUERY
//			            	String q2="Update book set quantity=quantity-1 where bid=?;";
//			            	
//			            	PreparedStatement p1=con.prepareStatement(q1);
//			            	p1.setInt(1, ud);
//			            	p1.setInt(2, bd);
//			            	p1.setString(3, idate);
//			            	
//			            	PreparedStatement p2=con.prepareStatement(q2);
//			            	p2.setInt(1, bd);
//			            	
//			            	p1.executeUpdate();
//			            	p2.executeUpdate();
//			            	
//			            	System.out.println("Issued book successfully!!\n");
			            	
			            }
			            else {
			            	System.out.println("Exit successfully !!!");
			            	flag=false;
			            	break;
			            }
					}
	            }
	            else {
	            	System.out.println("Invalid Credentials :( --- Try Again\n");
	            }
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			sc.close();
		}
		
	}

}

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_reservation";
    private static final String username = "root";
    private static final String password = "admin@123";



    private static void newReservation(Statement stmt, Scanner sc) {

        System.out.println("Enter Guest Name: ");
        String name = sc.next();
        sc.nextLine();

        System.out.println("Enter Room Number: ");
        int room_no = sc.nextInt();

        System.out.println("Enter Contact Number: ");
        int contact = sc.nextInt();



        String query = "insert into reservation(guest_name,room_number,contact) values ('"+name+"',"+room_no+","+contact+");";

        try{


            int affectedRows = stmt.executeUpdate(query);
            if(affectedRows>0){
                System.out.println("Successfully Reservation Done !!!");
            }else{
                System.out.println("Reservation Not Done !!!");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }


    private static void checkReservation(Statement stmt) throws SQLException{

        String query = "select * from reservation;";

        try{

            ResultSet rs = stmt.executeQuery(query);
            System.out.println("|********************|**************************|*************|*****************|**********************|");
            System.out.println("| Reservation_ID     |      GUEST_NAME          |   ROOM_NO   |      CONTACT    |       DATETIME       | ");
            System.out.println("|********************|**************************|*************|*****************|**********************|");
            while(rs.next()){
                int reservation_id = rs.getInt("reservation_id");
                String guest_name = rs.getString("guest_name");
                int room_no = rs.getInt("room_number");
                int contact = rs.getInt("contact");
                String datetime = rs.getString("reservation_date");
                System.out.println("|           "+reservation_id+"        |"+guest_name+"                    |"+room_no+"           |     "+contact+"    |  "+datetime+"    |");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }



    }



    private static void GetRoomNo(Statement stmt,Scanner sc){

        System.out.println("Enter Reservation ID : ");
        int reservation_id = sc.nextInt();
        String query = "select guest_name,room_number from reservation where reservation_id = "+reservation_id+";";


        try{

            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){
                 String name = rs.getString("guest_name");
                 int room_no = rs.getInt("room_number");
                 System.out.println("Guest name is : "+name);
                 System.out.println("Room number : "+room_no);
            }else{
                System.out.println("Reservation not found !!!");
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
        }



    }




    private static void updateReservation(Statement stmt, Scanner sc) {
        System.out.println("Enter Reservation ID : ");
        int reservation_id = sc.nextInt();

        if(!reservationExist(stmt,reservation_id)){
            System.out.println("Reservation not found to update ");
            return;
        }


        System.out.println("Enter Guest Name : ");
        String name = sc.next();
        sc.nextLine();

        System.out.println("Enter Room Number : ");
        int room_no = sc.nextInt();

        System.out.println("Enter Contact Number : ");
        int contact= sc.nextInt();

        String query = "update reservation set guest_name='"+name+"',room_number="+room_no+",contact="+contact+" where reservation_id="+reservation_id+";";

        try{

            int rowsaffected = stmt.executeUpdate(query);

            if(rowsaffected>0){
                System.out.println("Reservation update successfully !!!");
            }else{
                System.out.println("Reservation update failed !!!");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }


    }


    private static boolean reservationExist(Statement stmt,int reservation_id){
        String query = "select * from reservation where reservation_id="+reservation_id+";";
        try{
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    private static void deleteReservation(Statement stmt, Scanner sc) {
        System.out.println("Enter Reservation ID : ");
        int reservation_id = sc.nextInt();
        String query = "delete from reservation where reservation_id="+reservation_id+";";
        try{

            int rowsaffetced = stmt.executeUpdate(query);

            if(rowsaffetced>0){
                System.out.println("Delete successfully !!!");
            }else{
                System.out.println("Deletion failed !!!");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void exit() {
        int i=5;
        System.out.println("Thanks to use this ");
        while(i>0){
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            i--;
        }

        System.exit(0);
    }



    public static void main(String[] args) throws SQLException,ClassNotFoundException {


       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
       }catch (ClassNotFoundException e){
           System.out.println(e.getMessage());
       }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();
            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("**********************WELCOME****************************");
                System.out.println("1. New Reservation");
                System.out.println("2. Check Reservations");
                System.out.println("3. Get Room No");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("6. Exit");
                System.out.println("Choose an option : ");
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        newReservation(stmt,sc);
                        break;
                    case 2:
                        checkReservation(stmt);
                        break;
                    case 3:
                        GetRoomNo(stmt,sc);
                        break;
                    case 4:
                        updateReservation(stmt,sc);
                        break;
                    case 5:
                        deleteReservation(stmt,sc);
                        break;
                    case 6:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid option");
                }


            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



}
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;
public class Client implements Serializable{
    public String id ;
    public String pass;
    public Client(String id,String pass){

        this.id=id;
        this.pass=pass;
    }
    public static int[][] EnterMatrice(String A,int a,int b){
        int[][] reponse=new int[a][b];
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<a;i++){
            for(int j=0;j<b;j++){
                System.out.print(A+"["+i+"]["+j+"]"+"=");
                reponse[i][j]=sc.nextInt();
            }
        }
        return reponse;
    }
    public static void Affichage(int[][] a){
        for(int[] x:a){
            System.out.print("[");
            for(int y:x){
                System.out.print(y+",");
            }
            System.out.print("]");
            System.out.print("\n");
        }
    }
    public static void main(String[] arg)throws Exception{
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter id: ");
        String id=sc.nextLine();
        System.out.print("Enter Pass: ");
        String pass=sc.nextLine();
        Client client=new Client(id, pass);
        Socket socket=new Socket("127.0.0.1",8888);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(client);
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        boolean rps=(boolean)is.readObject();
        if(rps==false){
            System.out.println("Failed Authentification !!!");
            String reponse="n";
            System.out.print("Create Profile [y/n]: ");
            reponse=sc.nextLine();
            os.writeObject(reponse.equalsIgnoreCase("y"));
            if(reponse.equalsIgnoreCase("y")){
                System.out.print("Enter id: ");
                String datac=sc.nextLine();
                os.writeObject(datac);
                System.out.print("Enter Password: ");
                datac=sc.nextLine();
                os.writeObject(datac);
                rps=true;
            }
            else{
                System.out.println("EXIT !!");
            }
        }
        if(rps==true){
            String entrymatrice=(String)is.readObject();
            System.out.println(entrymatrice);
            System.out.println("Entrer The size of Matrice: ");
            System.out.print("Enter Row matrix: ");
            int row=sc.nextInt();
            System.out.print("Enter column matrix: ");
            int column=sc.nextInt();
            int[][] a=EnterMatrice("A", row, column);
            os.writeObject(a);
            entrymatrice=(String)is.readObject();
            System.out.println(entrymatrice);
            System.out.println("Entrer The size of Matrice: ");
            System.out.print("Enter Row matrix: ");
            row=sc.nextInt();
            System.out.print("Enter column matrix: ");
            column=sc.nextInt();
            int[][] b=EnterMatrice("B", row, column);
            os.writeObject(b);
            String datamatrice=(String)is.readObject();
            System.out.println(datamatrice);
            System.out.print("Enter The Choice: ");
            int choice=sc.nextInt();
            os.writeObject(choice);
            int[][] resultmatrice=(int[][])is.readObject();
            System.out.println("Matrice Result: ");
            Affichage(resultmatrice);
        }
    }
}
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.System;
class Client implements Serializable{
    public String id ;
    public String pass;
    public Client(String id,String pass){
        this.id=id;
        this.pass=pass;
    }
}
public class Server extends Thread{
    Socket socket;
    boolean rpns=false;
    public ArrayList<Client> client=new ArrayList<>();
    Server(Socket socket){
        this.socket=socket;
    }
 
    public void run(){
        try{
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        Client user=(Client)is.readObject();
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        rpns=verifer(user.id,user.pass);

        os.writeObject(rpns);
        if(rpns==false){
            boolean rs=(boolean)is.readObject();
            if(rs==true){
                String dataserverid=(String)is.readObject();
                String dataserverpass=(String)is.readObject();
                client.add(new Client(dataserverid, dataserverpass));
            }
            rpns=true;
        }
        if(rpns==true){
            os.writeObject("Enter Matrice N1: ");
            int[][] a=(int[][])is.readObject();
            os.writeObject("Enter Matrice N2: ");
            int[][] b=(int[][])is.readObject();
            os.writeObject("1)->Addition between matrice: \n2)->Substitution between matrix: \n3)->Multiplication between matrix:");
            int cleintchoice=(int)is.readObject();
            int[][] c;
            switch(cleintchoice){
                case 1:
                System.out.println("Start Addition");
                c=add(a,b);
                os.writeObject(c);
                break;
                case 2:
                System.out.println("Start Substitution");
                c=sub(a,b);
                os.writeObject(c);
                break;
                case 3:
                System.out.println("Start Multiplication");
                c=mult(a,b);
                os.writeObject(c);
                break;
            }
        }
        }catch(Exception io){
            System.out.println(io.getMessage());
        }
    }
    int[][] add(int[][] a,int[][] b){
        int[][] c=new int[a.length][a.length];
        for(int i=0;i<a.length;i++){    
            for(int j=0;j<a[i].length;j++){    
            c[i][j]=a[i][j]+b[i][j];    
            }}
            return c;
    }
    int[][] sub(int[][] a,int[][] b){
        int[][] c=new int[a.length][a.length];
        for(int i=0;i<3;i++){    
            for(int j=0;j<3;j++){    
            c[i][j]=a[i][j]-b[i][j];    
            }}
            return c;
    }
    int[][] mult(int[][] a,int[][] b){
    int c[][]=new int[a.length][b[0].length];       
for(int i=0;i<3;i++){    
for(int j=0;j<3;j++){    
c[i][j]=0;      
for(int k=0;k<3;k++)      
{      
c[i][j]+=a[i][k]*b[k][j];}}}   
return c; 
}
    
    boolean verifer(String id,String pass){
        
        for(Client x:client){
            if(x.id.equals(id)&&x.pass.equals(pass)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] arf)throws Exception {
        ServerSocket ss=new ServerSocket(8888);
        Socket skt;
        Server srv;
        while(true){
                     skt=ss.accept();
                     System.out.println("Start....");
                     srv=new Server(skt);
                     srv.start();
        }
    }
}
import java.net.*;
import java.io.*;

class Receiver {

    static char[][] mat(String key){
        key = key.toUpperCase().replace('J','I');
        String used="";

        for(char c:key.toCharArray())
            if(used.indexOf(c)==-1 && c>='A'&&c<='Z') used+=c;

        for(char c='A';c<='Z';c++)
            if(c!='J' && used.indexOf(c)==-1) used+=c;

        char m[][]=new char[5][5];
        int k=0;
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                m[i][j]=used.charAt(k++);
        return m;
    }

    static int[] pos(char m[][],char c){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                if(m[i][j]==c) return new int[]{i,j};
        return null;
    }

    static String dec(String msg,String key){
        char m[][]=mat(key);
        String out="";

        for(int i=0;i<msg.length();i+=2){
            char a=msg.charAt(i), b=msg.charAt(i+1);
            int p1[]=pos(m,a), p2[]=pos(m,b);

            if(p1[0]==p2[0]){
                out+=m[p1[0]][(p1[1]+4)%5];
                out+=m[p2[0]][(p2[1]+4)%5];
            }
            else if(p1[1]==p2[1]){
                out+=m[(p1[0]+4)%5][p1[1]];
                out+=m[(p2[0]+4)%5][p2[1]];
            }
            else{
                out+=m[p1[0]][p2[1]];
                out+=m[p2[0]][p1[1]];
            }
        }
        return out;
    }

    public static void main(String[] args)throws Exception{

        ServerSocket ss=new ServerSocket(5000);
        Socket s=ss.accept();

        DataInputStream in =
                new DataInputStream(s.getInputStream());

        String key=in.readUTF();
        String enc=in.readUTF();

        System.out.println("Encrypted Received: "+enc);

        String dec=dec(enc,key);

        System.out.println("Decrypted Message: "+dec);

        ss.close();
        s.close();
    }
}
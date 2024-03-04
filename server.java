import java.net.*;
import java.io.*;

class server
{
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    public server()
    {        
       try{
         server=new ServerSocket(7777);
         System.out.println("server is ready to accept connection");
         System.out.println("waiting..");
         socket=server.accept();

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out=new PrintWriter(socket.getOutputStream());

        startreading();
        startwriting();


       } catch (Exception e) {
        e.printStackTrace();
       }

    }


        public void startreading(){

            Runnable r1=()->{
                System.out.println("reader stared");
                try{
                while(true)
                {
                    
                    String msg=br.readLine();

                    if(msg.equals("exit"))
                    {
                        System.out.println("client terminated the chat");
                        socket.close();
                        break;

                    }
                    System.out.println("client :" +msg);
                }
                }catch(Exception e){
                   // e.printStackTrace();
                   System.out.println("connection is closed");
                }
                

            };

            new Thread(r1).start();
        }

        public void startwriting(){

             Runnable r2=()->{
                System.out.println("writer started");
                try{
                while(!socket.isClosed()){

                    

                        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));

                        String content=br1.readLine();

                        out.println(content);
                        out.flush();

                        if(content.equals("exit")){
                            socket.close();
                            break;
                        }
                    }  

                    System.out.println("connection is closed");

                    } catch (Exception e) {
                        e.printStackTrace();
                        
                    }
                

            };

             new Thread(r2).start();
        }

    public static void main(String[] args) {
        System.out.println("this is sever..going to start server");
        new server();
    }
}
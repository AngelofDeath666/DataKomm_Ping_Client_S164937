import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PingClient {

    private Socket socket;
    private BufferedReader fromServer;
    private DataOutputStream toServer;
    private long start;
    public String hostName;
    public int port;
    public int timeOut;






    public static void main(String[] args) throws IOException {
        PingClient client = new PingClient();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the domain you want to connect to: ");
        client.hostName = scanner.nextLine();
        System.out.println("Please input your chosen port: ");
        client.port = scanner.nextInt();
        System.out.println("Please input your chosen timeout: ");
        client.timeOut = scanner.nextInt();
        System.out.println(client.timeOut + "banana");
        System.out.println("Please input how many times you want to ping: ");
        int amount = scanner.nextInt();


        client.amountOfPings(amount);




    }
    //method to get the start time
    public void setStartTime(){
        start = System.currentTimeMillis();

    }


    //method to get the elapsed time.
    public void setElapsedTime(){
        System.out.println("Time elapsed = "+(System.currentTimeMillis()-start));

    }




    //method to connect socket to the given hostname and port
    public void connectSocket (String hostName, int port, int timeOut) throws IOException{

        socket = new Socket();
        //Here we connect to the socket
        socket.connect(new InetSocketAddress(hostName, port), timeOut);
        //We use BufferedReader so we are able to wait for answer from the server
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new DataOutputStream(socket.getOutputStream());



    }

    public boolean sendPing() throws IOException{
        connectSocket(hostName,port,timeOut);
        setStartTime();
        toServer.writeByte(32);
        fromServer.readLine();
        setElapsedTime();
        socket.close();
        fromServer.close();
        toServer.close();
        return true;
    }

    public void amountOfPings(int amount) throws IOException {
        for (int i = 0; i < amount; i++){
            System.out.println("pinged");

            if (!sendPing()){
                System.out.println("Failed: " + i+1);
            }

        }
    }

}

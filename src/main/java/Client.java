import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{

	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private Consumer<Integer> clientNumber;


	private Consumer<ArrayList<String>> clientListCallback; // new callback for client list added


	Client(Consumer<Serializable> call, Consumer<ArrayList<String>> clientListCall){
		callback = call;
		clientListCallback = clientListCall; // added
	}
	
	public void run() {


		
		try {
			socketClient= new Socket("127.0.0.1",5555);
	    	out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
	    	socketClient.setTcpNoDelay(true);


		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {

				Object receivedObj = in.readObject();

				if(receivedObj instanceof String) {
					// if the received object is a message
					String message = (String) receivedObj;
					callback.accept(message);
				} if(receivedObj instanceof ArrayList) {
					// if the received object is a client list
					ArrayList<String> clientNames = (ArrayList<String>) receivedObj;
					clientListCallback.accept(clientNames);
				}

			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToIndividual(String data, int clientNum){
		try {
			out.writeObject("Individual" + clientNum + ": " + data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;
	Integer counter = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;



	Server(Consumer<Serializable> call){
		callback = call;
		server = new TheServer();
		server.start();

	}

	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);


				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();


				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}

			public ArrayList<String> getClientNames() {
				ArrayList<String> clientNames = new ArrayList<>();
				for (ClientThread client : clients) {
					clientNames.add("Client #" + client.count);
				}
				return clientNames;
			}
			
//			public void updateClients(String message) {
//				for(int i = 0; i < clients.size(); i++) {
//					ClientThread t = clients.get(i);
//					try {
//					 t.out.writeObject(message);
//					 //t.out.writeObject(clientNames);
//					}
//					catch(Exception e) {}
//				}
//			}
			public void updateIndividualClient(int senderId, int recieverId, String message) {
				for (ClientThread client : clients) {
					if (client.count == senderId) {
						try {
							client.out.writeObject(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}

				for (ClientThread client : clients) {
					if (client.count == recieverId) {
						try {
							client.out.writeObject(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}

			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {

						t.out.writeObject(message);

					}
					catch(Exception e) {}
				}
			}

			private void updateClientsList(){
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {

						t.out.writeObject(getClientNames());

					}
					catch(Exception e) {}
				}
			}


			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
				counter = count;


					
				 while(true) {
					 updateClientsList();
					    try {
					    	String data = in.readObject().toString();//Orig
							callback.accept("client: " + count + " sent: " + data);//Orid
							if (data.startsWith("Individual")){
								String str = data;
								String[] parts = str.split(":");
								String numberStr = parts[0].substring("Individual".length());
								int number = Integer.parseInt(numberStr);

								updateIndividualClient(count, number, data);
							} else {
								updateClients("client #" + count + " said: " + data);//Orig
							}
					    	
					    	}
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
					    	clients.remove(this);
							updateClientsList();
					    	break;
					    }
					}
				}//end of run
		}//end of client thread
}


	
	

	

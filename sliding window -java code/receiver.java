

		import java.net.*;
		import java.io.*;
		class receiver
		{
		public static void main(String a[])throws Exception
		{
		Socket s=new Socket(InetAddress.getLocalHost(),6666); 
		 DataInputStream in=new DataInputStream(s.getInputStream());// read data from the socket
		PrintStream p=new PrintStream(s.getOutputStream()); // write data to the socket
		
		String frameBuffer[]=new String[8];
		String ch; System.out.println();
		do
		{
		int i=0,index=0,noOfFrame,rws=8;
		noOfFrame=Integer.parseInt(in.readLine());
		if(noOfFrame<=rws-1)
		{
		for(i=1;i<=noOfFrame-1;i++) // Frame -1 -> frames not received by the receiver
		{
		index=++index%8;
		frameBuffer[index]=in.readLine();// used to store the received frames in an array
		System.out.println("The received Frame " +index+" is : "+frameBuffer[index]);
		}
		rws-=noOfFrame;
		System.out.println("\nAcknowledgment For Frame "+ index+ "sent\n");
		p.println(index); // sends  acknowledgement for the last frame received. 
		rws+=noOfFrame; 
		while(index<noOfFrame)
		{
		for(i=index+1; i<=noOfFrame; i++){ 
		index++;
		frameBuffer[i]=in.readLine();//stores missing frame on an arrray
		System.out.println("Received the missing Frame " +index+ " : "+frameBuffer[index]);		
		}
		System.out.println("\nAcknowledgement for frame " +index+" has been sent ");
		p.println(index); // sends acknowledgement of all frames being received
		}
		while(!in.readLine().equals(" "))
		{
		in.readLine();
		}
		
		}
		else
		break;
		ch=in.readLine();
		}
		while(ch.equals("yes"));
		}
		}

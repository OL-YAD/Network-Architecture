
import java.net.*;
import java.io.*;
import java.rmi.*;
public class sender
{
public static void main(String a[])throws Exception
{
		ServerSocket ser=new ServerSocket(6666); 
		Socket s=ser.accept();
		DataInputStream in=new DataInputStream(System.in); accept input from user
		DataInputStream ackReader=new DataInputStream(s.getInputStream()); //object used to read data from the socket
		String framebuffer[]=new String[8];
		PrintStream p;
		int NoOfFrames,latestAckSeqNo,i;
		String ch;
		do
		{
		int index=1,sws=8;
		p=new PrintStream(s.getOutputStream());	
		System.out.print("Enter the no. of frames : ");
		NoOfFrames=Integer.parseInt(in.readLine()); //store the number of frames the user will transmit
		p.println(NoOfFrames);
		if(NoOfFrames<=sws-1)
		{

		System.out.println("Enter "+NoOfFrames+" Messages to be send\n");
		for(i=1;i<=NoOfFrames;i++)
		{
		framebuffer[index]=in.readLine(); // read frames from the user and store 
		p.println(framebuffer[index]);    // sends  received frame to the receiver 
		index=++index%8;
		}
		sws-=NoOfFrames;

		latestAckSeqNo=Integer.parseInt(ackReader.readLine());//the sequence number of frames whose ack is received.
		System.out.print("Acknowledgment for frame "+latestAckSeqNo+" has been received");
		
		sws+=NoOfFrames;
		while(latestAckSeqNo<NoOfFrames)//waits for a time out and sends all frames after latestAckSeqNo			
		{
		for(i=latestAckSeqNo+1; i<=NoOfFrames; i++){	// sending frames that are sequenced after latestAckSeqNo
		System.out.print("\nResending frames "+ i);
		p.println(framebuffer[i]);
		}
		Thread.sleep(1000);	// waits for 1sec timeout	
		latestAckSeqNo=Integer.parseInt(ackReader.readLine());//sequence number of the latest frame acknowledged
		if(latestAckSeqNo==NoOfFrames){//if true all frames are acknowledged ,else resend frames after latestAckSeqNo. 
		System.out.print("\nAcknowledgement for frame "+ latestAckSeqNo + "received");	
		}
		}
		
		}
		else
		{
		System.out.println("no. of frames exceeds window size");
		break;
		}
		System.out.print("\nDo you wants to send some more frames : ");
		p.println(" ");
		ch=in.readLine(); p.println(ch);
		}
		while(ch.equals("yes"));
		s.close();
		}
		}

package team_viewer;

import java.io.*;
import java.net.*;
public class TeamViewer 
{
	private static URL url;
	private static URLConnection urlConnection;
	private static InputStream inputStream;
	private static Socket hostComputer;
	private static BufferedInputStream fromHostComputer;
	private static FileOutputStream writeFileToDisk;
	private static DataInputStream readFromBuffer;
	public static void main(String[] args) throws Exception 
	{
		String centralServer = "http://logitekprojects.com/TeamViewer/getIpPort.php";
		String hostAddress[] = getHostAddress(centralServer).trim().split(",");
		String ipAddress = hostAddress[0];
		int portNumber = Integer.parseInt(hostAddress[1]);
		hostComputer = new Socket(ipAddress, portNumber);
		System.out.println("Connected to remote computer at: " + ipAddress);
		fromHostComputer = new BufferedInputStream(hostComputer.getInputStream());
		readFromBuffer = new DataInputStream(fromHostComputer);
		writeFileToDisk = new FileOutputStream("D:\\one.mp3");
		Thread rxThread = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						writeFileToDisk.write(readFromBuffer.read());
						writeFileToDisk.flush();
					}catch (Exception e) {}
				}
			}
		});rxThread.start();
	}
	
	public static String getHostAddress(String ipAddress) throws Exception
	{
		String rxdAddress = "";
		initConnection(ipAddress);
		inputStream = urlConnection.getInputStream();
		int inetReply;
		while ((inetReply = inputStream.read()) != -1)
			rxdAddress = rxdAddress + (char) inetReply;
		return rxdAddress;
	}
	
	public static void initConnection(String ipAddress) throws Exception
	{
		url = new URL(ipAddress);
		urlConnection = url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.connect();
	}
}
package teamviewer;
import java.io.*;
import java.net.*;
public class TeamViewer 
{
	private static URL url;
	private static URLConnection urlConnection;
	private static InputStream inputStream;
	private static ServerSocket hostComputer;
	private static Socket remoteComputer;
	private static BufferedOutputStream toRemoteComputer;
	private static File pathToFiles;
	private static FileInputStream readFileFromDisk;
	private static DataOutputStream writeToBuffer;
	public static void main(String[] args) throws Exception 
	{
		String getIpAddress = "http://bot.whatismyipaddress.com/";
		String centralServer = "http://logitekprojects.com/TeamViewer/getIpPort.php";
		int portNum = 1883;
		informRemoteMachine(getPublicIpAddress(getIpAddress), centralServer, portNum);
		
		hostComputer = new ServerSocket(portNum);
		remoteComputer = hostComputer.accept();
		System.out.println("Remote computer is connected now.");
		
		toRemoteComputer = new BufferedOutputStream(remoteComputer.getOutputStream());
		writeToBuffer = new DataOutputStream(toRemoteComputer);
		
		pathToFiles = new File("/Users/savreen/one.mp3");
		readFileFromDisk = new FileInputStream(pathToFiles);
		
		int byteCounter = 0;
		int inputByte;
		while ((inputByte = readFileFromDisk.read()) != -1)
		{
			byteCounter++;
			writeToBuffer.write(inputByte);
			writeToBuffer.flush();
		}
		System.out.println(byteCounter / 1024 + " byte(s) are transferred!");
	}
	
	public static String getPublicIpAddress(String ipAddress) throws Exception
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
	
	public static void informRemoteMachine(String ipAddress, String centralServer, int portNumber) throws Exception
	{
		initConnection(centralServer);
		PrintStream postMan = new PrintStream(urlConnection.getOutputStream());
        postMan.print("&ipAddress=" + ipAddress);
        postMan.print("&portNumber=" + portNumber);
        urlConnection.getInputStream();  
	}
}


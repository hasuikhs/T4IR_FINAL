package cantest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ServerLattePanda extends Application{
	TextArea textarea;		// 메시지 창(받은 메시지를 보여주는 역할)
	Button connBtn;			// 연결버튼(COM포트 연결, TCP 서버 연결 버튼)
	Button serverDisconnBtn; // 서버 접속 종료 버튼
	String portName;
	String carId = "10미2345";
	Socket socket;
	BufferedReader socketBr;
	PrintWriter socketOut;
	
    class ReceiveRunnable implements Runnable {
    	private BufferedReader br;
    	
		public ReceiveRunnable(BufferedReader br) {
			super();
			this.br = br;
		}

		@Override
		public void run() {
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					if(line.contains("Cooler#")) {
						String[] cooler= line.split("#");
//						System.out.println(Double.parseDouble(cooler[1]));
						String waterMsg = ":W2800000002"+carData(Double.parseDouble(cooler[1])).toUpperCase()+stringTOHex("W2800000002",carData(Double.parseDouble(cooler[1])))+"\r";
						byte[] inputData = waterMsg.getBytes();
						out.write(inputData);
						printMsg("냉각수OK");
					}
					else if(line.contains("EngineOil#")) {
						String[] engineOil= line.split("#");
//						System.out.println(Double.parseDouble(engineOil[1]));
						String oilMsg = ":W2800000003"+carData(Double.parseDouble(engineOil[1])).toUpperCase()+stringTOHex("W2800000003",carData(Double.parseDouble(engineOil[1])))+"\r";
						byte[] inputData = oilMsg.getBytes();
						out.write(inputData);
						printMsg("엔진오일OK");
					}
					else if(line.equals("CarOpen#")) {
						String openMsg = ":W28000000050000000000000005"+stringTOHex("W2800000005","0000000000000005")+"\r";
						byte[] inputData = openMsg.getBytes();
						out.write(inputData);
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
	
    
	public String carData(double u) {
		int it;
		int ri;
		int itIndex = 12;
		int riIndex = 4;
		int zeroFill;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		String[] str = Double.toString(u).split("\\.");
		if(str[1].length()>1) {
			str[1] = str[1].substring(0,2);
		}
		
		it = Integer.parseInt(str[0]);
		ri = Integer.parseInt(str[1]);
		String rt = Integer.toHexString(it);
		String rt2 = Integer.toHexString(ri);
		sb.append(rt);
		zeroFill = itIndex - rt.length();
		for(int i = 0; i < zeroFill; i++) {
			sb.insert(0,"0");
		}
		sb.append(rt2);
		zeroFill = riIndex - rt2.length();
		for(int i = 0; i < zeroFill; i++) {
			sb.insert(12,"0");
		}
		return sb.toString();
	}
	
	private CommPortIdentifier portIdentifier;
	
	private CommPort commPort;
	private SerialPort serialPort;
	private BufferedInputStream bis;
	private OutputStream out;
	
	public String stringTOHex(String str, String str2) {
		int checkSum = 0;
		String msg = str+str2;
		for(int i = 0; i < msg.length(); i++) {
			checkSum += msg.toUpperCase().charAt(i);
		}
		checkSum = checkSum & 0xff;
		String myReturn = Integer.toHexString(checkSum);
		if(myReturn.length()==1) {
			myReturn = "0"+myReturn;
		}
		return myReturn.toUpperCase();
	}	
	
	class MyPortListenter implements SerialPortEventListener{
		DatagramSocket dsoc;
    	DatagramPacket dp;
    	InetAddress ia;
    	public MyPortListenter() {
    		try {
				this.dsoc = new DatagramSocket();
				ia = InetAddress.getByName("70.12.225.139");
			} catch (IOException e) {
				e.printStackTrace();
				printMsg("UDP통신 소켓 에러!!! 다시 실행하세요!");
			}
			
    	}
		@Override
		public void serialEvent(SerialPortEvent event) {
			if(event.getEventType() == 
					SerialPortEvent.DATA_AVAILABLE) {
				byte[] readBuffer = new byte[128];
				try {
					while(bis.available()>0) {
						bis.read(readBuffer);
					}
					String result = new String(readBuffer);
					printMsg("받은 메시지는 : "+result);
					String[] code = result.split(":");
					if(code[1].contains("U280")) {
						for(int i = 1; i <= code.length-1; i++) {
							if(code[i].contains("U2800000001")){
								printMsg("주행거리 : " + hexstringtoInt(code[i]));
								socketOut.println("Distance#" + hexstringtoInt(code[i]));
								socketOut.flush();
								dp = new DatagramPacket(("Distance#" + hexstringtoInt(code[i])).getBytes(),
										("Distance#" + hexstringtoInt(code[i])).getBytes().length, ia, 7779);
								dsoc.send(dp);
							} else if(code[i].contains("U2800000002")) {
								printMsg("냉각수 양 : " + hexstringtoInt(code[i]));
								socketOut.println("Cooler#" + hexstringtoInt(code[i]));
								socketOut.flush();
								dp = new DatagramPacket(("Cooler#" + hexstringtoInt(code[i])).getBytes(),
										("Cooler#" + hexstringtoInt(code[i])).getBytes().length, ia, 7779);
								dsoc.send(dp);
							} else if(code[i].contains("U2800000003")) {
								printMsg("앤진오일 양 : " + hexstringtoInt(code[i]));
								socketOut.println("EngineOil#" + hexstringtoInt(code[i]));
								socketOut.flush();
								dp = new DatagramPacket(("EngineOil#" + hexstringtoInt(code[i])).getBytes(),
										("EngineOil#" + hexstringtoInt(code[i])).getBytes().length, ia, 7779);
								dsoc.send(dp);
							} else if(code[i].contains("U280AAAAAAA")) {
								System.out.println("STOPDRIVE");
								socketOut.println("STOPDRIVE");
								socketOut.flush();
							} 
							else {
								printMsg("알수없는 protocol입니다.");
							}
						}
					}
					else if(code[1].contains("U281FFFFFFF")) {
						socketOut.println("ready#");
						socketOut.flush();
						
						printMsg("주행 준비 완료");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String hexstringtoInt(String str) {
		String number = null;
		if(str.length()>27) {
			String natureHexString = str.substring(11, 23);
			String primeHexString = str.substring(23,27);
			int n = Integer.parseUnsignedInt(natureHexString, 16);
			int p = Integer.parseUnsignedInt(primeHexString, 16);
			number = n + "." + p;
		}
		return number;
		
	}
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	private void connectPort(String portName) {
		try {
			portIdentifier = 
					CommPortIdentifier.getPortIdentifier(portName);
			printMsg(portName + "에 연결을 시도합니다.");
			
			if(portIdentifier.isCurrentlyOwned()) {
				printMsg(portName+"가 다른 프로그램에 의해서 사용되고 있어요!");
			}else {
				commPort = portIdentifier.open("MyApp",5000);
				if(commPort instanceof SerialPort) {
					serialPort = (SerialPort)commPort;
					serialPort.setSerialPortParams(
							38400,						// Serial Port 통신 속도 
							SerialPort.DATABITS_8,		// 데이터 비트  
							SerialPort.STOPBITS_1,		// stop bit 설정 
							SerialPort.PARITY_NONE);	// Parity bit는 안써요
					
					serialPort.addEventListener(new MyPortListenter());
					serialPort.notifyOnDataAvailable(true);
					printMsg(portName+"에 리스터가 등록되었어요!");
					bis = new BufferedInputStream(
							serialPort.getInputStream());
					out = serialPort.getOutputStream();
					String msg = ":G11A9\r";
					try {
						byte[] inputData = msg.getBytes();
						out.write(inputData);
						printMsg(portName+"가 수신을 시작합니다.");
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPrefSize(750, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		connBtn = new Button("COM포트, TCP 서버 연결");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t->{
			portName = "COM12";
			connectPort(portName);
			
			try {
				socket = new Socket("localhost",6767);
				InputStreamReader isr = 
						new InputStreamReader(socket.getInputStream());
				socketBr = new BufferedReader(isr);
				socketOut = new PrintWriter(socket.getOutputStream());				
				printMsg("TCP 서버 접속 성공!!");
				
				ReceiveRunnable runnable = new ReceiveRunnable(socketBr);
				Thread thread = new Thread(runnable);
				thread.start();
				socketOut.println("CarID#"+carId);
				socketOut.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	
		serverDisconnBtn = new Button("접속 종료");
		serverDisconnBtn.setPrefSize(250, 50);
		serverDisconnBtn.setOnAction(t->{
			try {
				socketOut.println("/EXIT/");   
				socketOut.flush();
				printMsg("서버 접속 종료!!");
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		

		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(750, 50);
		flowPane.getChildren().add(connBtn);
		flowPane.getChildren().add(serverDisconnBtn);
		root.setBottom(flowPane);
		
		// Scene객체가 필요함.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ServerLattePanda");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}

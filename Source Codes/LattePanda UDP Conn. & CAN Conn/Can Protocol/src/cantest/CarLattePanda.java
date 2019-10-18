package cantest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class CarLattePanda extends Application{
	TextArea textarea;		// 메시지 창(받은 메시지를 보여주는 역할)
	Button connBtn, startBtn, stopBtn;
	TextField tf;
	String portName;		// port번호
	double driveDistance;
	double coolingWater;
	double engineOil;
	Thread driveThread;
	String[] carsData = new String[3];
	
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
//		System.out.println(Arrays.toString(str));
		
		it = Integer.parseInt(str[0]);
		ri = Integer.parseInt(str[1]);
		String rt = Integer.toHexString(it);
		String rt2 = Integer.toHexString(ri);
//		System.out.println(rt);
//		System.out.println(rt2);
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
							} else if(code[i].contains("U2800000002")) {
								coolingWater = Double.parseDouble(hexstringtoInt(code[i]));
							} else if(code[i].contains("U2800000003")) {
								engineOil = Double.parseDouble(hexstringtoInt(code[i]));
							} else if(code[i].contains("U28000000050000000000000005")){
								printMsg("차문 열림");
							}
//							else {
//								printMsg("알수없는 protocol입니다.");
//							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String hexstringtoInt(String str) {
		String natureHexString = str.substring(11, 23);
		String primeHexString = str.substring(23,27);
		
		int n = Integer.parseUnsignedInt(natureHexString, 16);
		int p = Integer.parseUnsignedInt(primeHexString, 16);
		
		String number = n + "." + p;
		
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
					String connMsg = ":W28FFFFFFFFFFFFFFFFFFFFFFFF"+stringTOHex("W28FFFFFFFF","FFFFFFFFFFFFFFFF")+"\r";
					try {
						byte[] inputData = msg.getBytes();
						out.write(inputData);
						inputData = connMsg.getBytes();
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
		driveDistance = 0;
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		tf = new TextField();
		
		connBtn = new Button("COM포트 연결");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t->{
			portName = "COM11";
			connectPort(portName);
		});
		
		startBtn = new Button("주행 시작");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			driveThread = new Thread() {
				public void run() {
					printMsg("주행 시작");
					printMsg("주행거리 : " + driveDistance);
					printMsg("냉각수 : " + coolingWater);
					printMsg("엔진오일 : " + engineOil);
					
					while(!isInterrupted()) {
						try {
							out.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						driveDistance += 0.25;
						coolingWater -= 0.004;
						engineOil -= 0.004;
						if(coolingWater<0) {
							coolingWater = 0;
						}else if(engineOil < 0) {
							engineOil = 0;
						}
						String distanceMsg = ":W2800000001"+carData(driveDistance).toUpperCase()+stringTOHex("W2800000001",carData(driveDistance))+"\r";
						carsData[0] = distanceMsg;
						String waterMsg = ":W2800000002"+carData(coolingWater).toUpperCase()+stringTOHex("W2800000002",carData(coolingWater))+"\r";
						carsData[1] = waterMsg;
						String oilMsg = ":W2800000003"+carData(engineOil).toUpperCase()+stringTOHex("W2800000003",carData(engineOil))+"\r";
						carsData[2] = oilMsg;
						
						for(int i = 0; i < carsData.length; i++) {
							try {
								byte[] inputData = carsData[i].getBytes();
								out.write(inputData);
								
								inputData = null;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							out.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							break;
						}
					}
					printMsg("Thread중지");
				}
			};
			driveThread.start();
		});
		
		stopBtn = new Button("주행 중지");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t->{
			String stopDrive =  ":W28AAAAAAAACCCCCCCCCCCCCCCC"+stringTOHex("W28AAAAAAAA","CCCCCCCCCCCCCCCC")+"\r";
			byte[] inputData = stopDrive.getBytes();
			try {
				out.write(inputData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			driveThread.interrupt();
			printMsg("주행거리 : " + driveDistance);
			printMsg("냉각수 : " + coolingWater);
			printMsg("엔진오일 : " + engineOil);
			driveDistance = 0;
		});
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(750, 50);
		flowPane.getChildren().add(connBtn);
		flowPane.getChildren().add(startBtn);
		flowPane.getChildren().add(stopBtn);
		root.setBottom(flowPane);
		
		// Scene객체가 필요함.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CarLattePanda");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}

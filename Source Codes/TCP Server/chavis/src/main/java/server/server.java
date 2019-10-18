package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chavis.car.dao.CarDAO;
import com.chavis.car.dao.CarDAO_mybatis;
import com.chavis.car.service.CarService;
import com.chavis.car.service.CarServiceImpl;
import com.chavis.car.vo.CarVO;
import com.chavis.notification.dao.NotificationDAO;
import com.chavis.notification.dao.NotificationDAO_mybatis;
import com.chavis.notification.service.NotificationService;
import com.chavis.notification.service.NotificationServiceImpl;
import com.chavis.notification.vo.NotificationVO;
import com.chavis.repairlist.dao.RepairListDAO;
import com.chavis.repairlist.dao.RepairListDAO_mybatis;
import com.chavis.repairlist.service.RepairListService;
import com.chavis.repairlist.service.RepairListServiceImpl;
import com.chavis.repairlist.vo.RepairListVO;
import com.chavis.reservation.dao.ReservationDAO;
import com.chavis.reservation.dao.ReservationDAO_mybatis;
import com.chavis.reservation.service.ReservationService;
import com.chavis.reservation.service.ReservationServiceImpl;
import com.chavis.reservation.vo.ReservationVO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class server extends Application{
	
	TextArea textarea;
	Button startBtn;		// 서버에 접속하는 버튼
	Button exitBtn;
	ExecutorService executorService;
	CarDAO carDAO = new CarDAO_mybatis();
	CarService carService = new CarServiceImpl(carDAO);
	ReservationDAO reservationDAO = new ReservationDAO_mybatis();
	ReservationService reservationService = new ReservationServiceImpl(reservationDAO);
	RepairListDAO repairListDAO = new RepairListDAO_mybatis();
	RepairListService repairListService = new RepairListServiceImpl(repairListDAO);
	NotificationDAO notificationDAO = new NotificationDAO_mybatis();
	NotificationService notificationService = new NotificationServiceImpl(notificationDAO);
	List<CarRunnable> carList = new ArrayList<CarRunnable>();
	List<AndroidRunnable> appList = new ArrayList<AndroidRunnable>();
	List<BodyShopAppRunnable> bodyShopList = new ArrayList<BodyShopAppRunnable>();
	
//	String testKey = "Key#123456789";
	
	public String distanceAccumulate(String dis, String dis2) {
		double data = Double.parseDouble(dis) + Double.parseDouble(dis2);
		String[] result = Double.toString(data).split("\\.");
		if(result[1].length() > 1) {
			return result[0] + "." + result[1].substring(0,2);
		}
		return result[0] + "." + result[1];
	}
	
	// 클라이언트의 접속을 받아들이는 서버소켓.
	ServerSocket server;
	
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	class CarRunnable implements Runnable{
		// 가지고 있어야 하는 field
		private Socket socket;		// 클라이언트와 연결된 소켓
		private BufferedReader br;	// 입력을 위한 스트림
		private PrintWriter out;	// 출력을 위한 스트림
		private String carId;		// 차량 번호
		private int carNo;
		private String tireDistance;
		private String wiperDistance;
		private String distance;
		private String cooler;
		private String engineOil;
		private int memberNo;
		private NotificationVO vo;
		
		public CarRunnable(String carId, Socket socket, BufferedReader br) {
			super();
			this.carId = carId;
			this.socket = socket;
			try {
				this.br = br;
				this.out = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			vo = new NotificationVO();
		}

		@Override
		public void run() {
			// 클라이언트와 echo처리 구현
			// 클라이언트가 문자열을 보내면 해당 문자열을 받아서
			// 다시 클라이언트에게 전달.
			// 한번하고 종료하는게 아니라 클라이언트가 "/EXIT/"라는 
			// 문자열을 보낼 때까지 지속.
			String line = "";
			
			try {
				while((line = br.readLine()) != null) {
					if(Thread.currentThread().isInterrupted()) {
						break;
					}
					
					if(line.equals("ready#")) {
						CarVO car = carService.getCar(carNo);
						tireDistance = car.getTire_change_distance();
						wiperDistance = car.getWiper_change_distance();
						distance = car.getDistance();
						cooler = car.getCooler_left();
						engineOil = car.getEngine_oil_viscosity();
						memberNo = car.getMember_no();
						printMsg(carId + " 차랑정보");
						printMsg("타이어 주행거리 : " + tireDistance);
						printMsg("와이퍼 주행거리 : " + wiperDistance);
						printMsg("차량누적 주행거리 : " + distance);
						printMsg("차량 엔진오일 점도 " + engineOil);
						printMsg("차량 냉각수 잔량 : " + cooler);
						out.println("Cooler#" + cooler);
						out.println("EngineOil#" + engineOil);
						out.flush();
						printMsg(carId+"주행준비 완료");
					}
					else if(line.contains("Distance#")) {
						String[] distance = line.split("#");
						if(!(distance[1].contains("null")) & !(distance[1].equals(""))) {
							carService.updateTireDistance(distanceAccumulate(this.tireDistance, distance[1]), carNo);
							carService.updateWiperDistance(distanceAccumulate(this.wiperDistance, distance[1]), carNo);
							carService.updateDistance(distanceAccumulate(this.distance, distance[1]), carNo);
						}
					}
					else if(line.contains("Cooler#")) {
						String[] cooler = line.split("#");
						if(!(cooler[1].contains("null")) & !(cooler[1].equals(""))) {
							carService.updateCooler(cooler[1], carNo);
							this.cooler = cooler[1];
						}
					}
					else if(line.contains("EngineOil#")) {
						String[] engineOil = line.split("#");
						if(!(engineOil[1].contains("null")) & !(engineOil[1].equals(""))) {
							carService.updateEngineOil(engineOil[1], carNo);
							this.engineOil = engineOil[1];
						}
					}
					else if(line.equals("STOPDRIVE")) {
						CarVO car = carService.getCar(carNo);
						this.tireDistance = car.getTire_change_distance();
						this.wiperDistance = car.getWiper_change_distance();
						this.distance = car.getDistance();
						String alarmMsg = "";
						System.out.println("주행중지");
						if(Double.parseDouble(this.tireDistance)>48000.00) {
							if(!(appList.isEmpty())) {
								appList.forEach(a->{
									if(a.memberNo == memberNo) {
										a.out.println("RequestChangeTire");
										a.out.flush();
									}
								});
							}
							alarmMsg += "타이어, ";
						}
						if(Double.parseDouble(this.wiperDistance)>4800.00) {
							if(!(appList.isEmpty())) {
								appList.forEach(a->{
									if(a.memberNo == memberNo) {
										a.out.println("RequestChangeWiper");
										a.out.flush();
									}
								});
							}
							alarmMsg += "와이퍼, ";
						}
						if(Double.parseDouble(this.cooler)<=20.00) {
							if(!(appList.isEmpty())) {
								appList.forEach(a->{
									if(a.memberNo == memberNo) {
										a.out.println("RequestChangeCooler");
										a.out.flush();
									}
								});
							}
							alarmMsg += "냉각수, ";
						}
						if(Double.parseDouble(this.engineOil)<=20.00) {
							if(!(appList.isEmpty())) {
								System.out.println(1);
								appList.forEach(a->{
									if(a.memberNo == memberNo) {
										a.out.println("RequestChangeEngineOil");
										a.out.flush();
									}
								});
							}
							alarmMsg += "엔진오일, ";
						}
						if(!(alarmMsg.equals(""))) {
							System.out.println(alarmMsg);
							alarmMsg = alarmMsg.substring(0, alarmMsg.length()-2);
							alarmMsg += " 교체시기가 경과했습니다. 점검 받아주세요!!";
							vo.setContents(alarmMsg);
							vo.setMember_no(memberNo);
							vo.setTitle("[ 점검 알림 ]");
							notificationService.addNotification(vo);
						}
					}
					
					
					else if(line.equals("/EXIT/")) {
						break;	// 가장 근접한 loop를 탈출!
					}
				}
				// 자원 반날
				out.close();
				br.close();
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	class AndroidRunnable implements Runnable{
		// 가지고 있어야 하는 field
		private Socket socket;		// 클라이언트와 연결된 소켓
		private BufferedReader br;	// 입력을 위한 스트림
		private PrintWriter out;	// 출력을 위한 스트림
		private int memberNo;
		private String key = null;
		
		public AndroidRunnable(int memberNo, Socket socket, BufferedReader br) {
			super();
			this.memberNo = memberNo;
			this.socket = socket;
			try {
				this.br = br;
				this.out = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					if(Thread.currentThread().isInterrupted()) {
						break;
					}
					else if(line.contains("Key")) {
						String[] tmp = line.split("#");
						key = tmp[1];
						printMsg("Android KEY : "+key);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class BodyShopAppRunnable implements Runnable{
		private int bodyShopNo;
		private Socket socket;
		private int memberNo;
		private String carId;
		private int carNo;
		private ReservationVO reservationVO;
		private RepairListVO repairListVO;
		private PrintWriter out;
		private BufferedReader br;
		private int reservationNo;
		private String repairedTime;
		private String repairedPerson;
		private String tireRepairCheck;
		private String wiperRepairCheck;
		private String coolerRepairCheck;
		private String engineOilRepairCheck;
		private int result = 0;
		
		public BodyShopAppRunnable(int bodyShopNo, Socket socket, BufferedReader br) {
			this.bodyShopNo = bodyShopNo;
			this.socket = socket;
			try {
				this.br = br;
				this.out = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		@Override
		public void run() {
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					if(Thread.currentThread().isInterrupted()) {
						break;
					}
					else if(line.contains("CarOpen")) {
						String[] msg = line.split("#");
						memberNo = Integer.parseInt(msg[1]);
						carId = msg[2];
						System.out.println(memberNo+", "+carId);
						carNo = carService.getCarNo(carId);
						
						StringBuffer temp = new StringBuffer();
						Random rnd = new Random();
						for (int i = 0; i < 20; i++) {
						    int rIndex = rnd.nextInt(2);
						    switch (rIndex) {
						    case 0:
						        // a-z
						        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
						        break;
						    case 1:
						        // 0-9
						        temp.append((rnd.nextInt(10)));
						        break;
						    }
						}
						out.println("Key#" + temp.toString());
						out.flush();
						if(!(appList.isEmpty())) {
							appList.forEach(a->{
								if(a.memberNo == memberNo) {
									a.out.println("Key#" + temp.toString());
									a.out.flush();
								}
							});
						}
					}
					else if(line.contains("Key")) {
						System.out.println(line);
						String[] tmp = line.split("#");
						printMsg("BodyShop KEY" + tmp[1]);
						if(!(appList.isEmpty())& !(carList.isEmpty())) {
							appList.forEach(a->{
								if(a.memberNo == memberNo) {
									if(a.key == null) {
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									if(a.key.equals(tmp[1])) {
										System.out.println("KeyValue Ok");
										carList.forEach(c->{
											if(c.memberNo == memberNo) {
												c.out.println("CarOpen#");
												c.out.flush();
												System.out.println(c.carId);
											}
										});
									}
								}
							});
						}
					}
					else if(line.contains("RepairFinish#")) {
						System.out.println(line);
						tireRepairCheck = "X";
						wiperRepairCheck = "X";
						coolerRepairCheck = "X";
						engineOilRepairCheck = "X";
						String[] reservationMsg = line.split("/");
						reservationNo = Integer.parseInt(reservationMsg[1]);
						repairedTime = reservationMsg[2];
						repairedPerson = reservationMsg[3];
						String[] repairMsg = reservationMsg[0].split("#");
						for(int i = 1; i < repairMsg.length; i++) {
							if(repairMsg[i].equals("Tire")) {
								carService.resetTireDistance(carNo);
								tireRepairCheck = "O";
							}
							else if(repairMsg[i].equals("Wiper")) {
								carService.resetWiperDistance(carNo);
								wiperRepairCheck = "O";
							}
							else if(repairMsg[i].equals("Cooler")) {
								carService.resetCooler(carNo);
								coolerRepairCheck = "O";
							}
							else if(repairMsg[i].equals("EngineOil")) {
								carService.resetEngineOil(carNo);
								engineOilRepairCheck = "O";
							}
						}
						reservationVO = reservationService.getReservation(reservationNo);
						reservationVO.setRepaired_time(repairedTime);
						reservationVO.setRepaired_person(repairedPerson);
						result = reservationService.updateReservation(reservationVO);
						if(result >= 1) {
							out.println("RepairFinishResult#Success");
							out.flush();
						}else {
							out.println("RepairFinishResult#Fail");
							out.flush();
						}
						repairListVO = new RepairListVO(tireRepairCheck,wiperRepairCheck,coolerRepairCheck,engineOilRepairCheck,reservationNo);
						repairListService.addRepairList(repairListVO);
						notificationService.removeNotification(memberNo);
						if(!(appList.isEmpty())) {
							appList.forEach(a->{
								if(a.memberNo==memberNo) {
									a.out.println("RepairSuccess");
									a.out.flush();
								}
							});
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5 개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startBtn = new Button("TCP 서버 시작");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t->{
			executorService = Executors.newCachedThreadPool();
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 서버프로그램을 시작
			// 클라이언트의 접속을기다린다! -> 접속이 되면Thread를 하나 생성
			// -> Thread를 시작해서 클라이언트와 Thread가 통신하도록 만든다.
			// 서버는 다시 다른 클라이언트의 접속을 기다린다!
			Runnable runnable = () ->{
				try {
					server = new ServerSocket(6767);
					printMsg("TCP 서버 기동!!");
					while(true) {
						printMsg("클라이언트 접속 대기");
						Socket s = server.accept();		// blocking
						printMsg("클라이언트 접속 성공");
						PrintWriter out = new PrintWriter(s.getOutputStream());
						BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
						String[] check = br.readLine().split("#");
						if(check[0].equals("CarID")) {
							// 클라이언트가 접속했으니 Thread를 만들고 시작해야 한다.
							CarRunnable cr = new CarRunnable(check[1],s, br);
							System.out.println(check[1]);
							cr.carNo = carService.getCarNo(cr.carId);
							System.out.println(cr.carNo);
							printMsg(cr.carNo+"접속완료");
							carList.add(cr);
							executorService.execute(cr);		// thread 실행
						}
						else if(check[0].equals("MemberNO")) {
							System.out.println(check[1]);
							AndroidRunnable ar = new AndroidRunnable(Integer.parseInt(check[1]), s, br);
							appList.add(ar);
							System.out.println("MemberNO" + check[1]);
							executorService.execute(ar);		// thread 실행
						}
						else if(check[0].equals("BodyShopNO")) {
							BodyShopAppRunnable bsr = new BodyShopAppRunnable(Integer.parseInt(check[1]), s, br);
							System.out.println("BodyShopNO" + check[1]);
							bodyShopList.add(bsr);
							executorService.execute(bsr);		// thread 실행
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorService.execute(runnable);			
		});
		exitBtn = new Button("TCP 서버 종료");
		exitBtn.setPrefSize(250, 50);
		exitBtn.setOnAction(t->{
			executorService.shutdownNow();
			try {
				server.close();
				printMsg("TCP 서버 중지!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		FlowPane flowPane = new FlowPane();
		flowPane.setPrefSize(700, 50);
		flowPane.getChildren().add(startBtn);
		flowPane.getChildren().add(exitBtn);
		root.setBottom(flowPane);
		
		// Scene객체가 필요함.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Server Window");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}

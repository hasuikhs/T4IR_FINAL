package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ClientTest extends Application {
	
	TextArea textarea;
	Button connBtn,disConnBtn;     // 서버접속, 접속끊기 버튼	
	TextField idTf, msgTf;         // 아이디 입력칸, 메시지 입력칸

	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// 클라이언트쪽 Thread는 1개만 만들어져요! ThreadPool을 사용할 경우
	// overhead발생.
	ExecutorService executorService = 
			Executors.newCachedThreadPool();
	
	
	private void printMsg(String msg) {
		Platform.runLater(()-> {
			textarea.appendText(msg + "\n");
		});
	}
	
	// 서버로부터 들어오는 메시지를 계속 받아서 화면에 출력하기 위한 용도의
	// Thread
    class ReceiveRunnable implements Runnable {
    	// 서버로부터 들어오는 메시지를 받아들이는 역할을 수행
    	// 소켓에 대한 입력스트림만 있으면 되요!!
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
					printMsg(line);
				}				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
    }
    
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		connBtn = new Button("TCP 서버 접속");
		connBtn.setPrefSize(250, 50);
		connBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 접속버튼
			try {
				// 클라이언트는 버튼을 누르면 서버쪽에 Socket접속을 시도.
				// 만약에 접속에 성공하면 socket객체를 하나 획득.
				socket = new Socket("localhost",6767);
				// Stream을 생성
				InputStreamReader isr = 
						new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(isr);
				out = new PrintWriter(socket.getOutputStream());				
				printMsg("TCP 서버 접속 성공!!");
				
				// 접속을 성공했으니 이제 Thread를 만들어서 서버가 보내준
				// 데이터를 받을 준비를 해요!
				ReceiveRunnable runnable = new 
						ReceiveRunnable(br);
				executorService.execute(runnable);
				out.println("CarID#94오0805");
				out.flush();
			} catch (Exception e) {
				System.out.println(e);
			}
		});

		disConnBtn = new Button("접속 종료");
		disConnBtn.setPrefSize(250, 50);
		disConnBtn.setOnAction(t->{
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			try {
				// 우리가 정한(protocol) 서버 접속 종료를 위한 문자열을 보내요				
				out.println("/EXIT/");   
				out.flush();
				printMsg("서버 접속 종료!!");
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		
		idTf = new TextField();
		idTf.setPrefSize(100, 40);
		
		msgTf = new TextField();
		msgTf.setPrefSize(200, 40);
		msgTf.setOnAction(t-> {
			// 입력상자(TextField)에서 enter key가 입력되면 호출
			// 예) 홍길동>안녕하세요!!
			String msg = idTf.getText() + ">" + msgTf.getText();
			out.println(msg);   // 서버로 문자열 전송!!
			out.flush();
			msgTf.setText("");
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(connBtn);
		flowpane.getChildren().add(disConnBtn);
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);
		
		// Scene객체가 필요해요.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("LattePanda입니다!!");
		primaryStage.show();
	}

	public static void main(String[] args) {
		
		launch();
	}

}

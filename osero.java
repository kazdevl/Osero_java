package osero;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class osero extends Application {
	final int me=1;
	final int enemy=-1;

	int compare;
	Gui guiosero;
	Rule rule = new Rule();
			;
	Label lb1;
	Label lb2;
	Label lb3;
	Label lb4;
	Menu mn[];
	Button bt[];
	MenuItem mi[];

	FlowPane fp[];
	BorderPane bp;
	MenuBar mb;


	Canvas cv;
	GraphicsContext gc;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		guiosero = new Gui();
		int[] cvSize = new int[2];
		cvSize = guiosero.calcMapSize();
		System.out.println(cvSize[0] + "___" + cvSize[1]);


		cv = new Canvas(cvSize[0], cvSize[1]);
		gc = cv.getGraphicsContext2D();
		guiosero.paintMap(gc);

		lb1 = new Label("  Me_point="+0+"			");
		lb2 = new Label("Enemy_point="+0);

		mn=new Menu[2];
		mn[0]=new Menu("Option");
		mn[1]=new Menu("GameStart");
		mi=new MenuItem[3];
		mi[0]=new MenuItem("Skip");
		mi[1]=new MenuItem("Reset");
		mi[2]=new MenuItem("GameStart");
		mn[0].getItems().addAll(mi[0],mi[1]);
		mn[1].getItems().addAll(mi[2]);

		mb=new MenuBar();
		mb.getMenus().addAll(mn[0],mn[1]);

		fp=new FlowPane[2];
		fp[0]=new FlowPane();
		fp[1]=new FlowPane();
		fp[1].getChildren().add(mb);
		fp[0].getChildren().add(lb1);
		fp[0].getChildren().add(lb2);

		bp = new BorderPane();
		bp.setCenter(cv);
		bp.setTop(fp[0]);
		bp.setBottom(fp[1]);

		Scene sc = new Scene(bp, cvSize[0], cvSize[1]*1.2);
		stage.setScene(sc);
		stage.setTitle("osero");
		stage.show();

		cv.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
		mi[0].setOnAction(new MyMenuEventHandler());
		mi[1].setOnAction(new MyMenuEventHandler());
		mi[2].setOnAction(new MyMenuEventHandler());

	}

	class MyEventHandler implements EventHandler<MouseEvent> {
		double get_x;
		double get_y;

		public void handle(MouseEvent e) {
			try {
				get_x = (e.getX() - guiosero.leftMargin) / guiosero.SIZE;
				get_y = (e.getY() - guiosero.topMargin) / guiosero.SIZE;
				compare=rule.count(guiosero);

				if (rule.my_turn) rule.judge(guiosero,(int)get_x,(int)get_y, me, enemy);
				else rule.judge(guiosero,(int)get_x,(int)get_y, enemy, me);


				lb1.setText("  Me_point="+rule.count_myPoint(guiosero)+"			");
				lb2.setText("Enemy_point="+(rule.count(guiosero)-rule.count_myPoint(guiosero)));


				if(rule.can_change(guiosero,compare)) rule.change_turn(lb1,lb2);

				if(rule.my_turn) {

				}else {
					lb1.setFont(Font.font("Serif",12));
					lb2.setFont(Font.font("Serif",FontWeight.BLACK,18));
				}

				if(rule.win(guiosero,me,enemy)==1)guiosero.mode=2;
				else if(rule.win(guiosero,me,enemy)==2) guiosero.mode=3;
				else if(rule.win(guiosero,me,enemy)==3) guiosero.mode=4;
				guiosero.paintMap(gc);
			}catch(ArrayIndexOutOfBoundsException C) {
				System.out.println(C);
			}catch (Exception Z) {
				System.out.println("error");
			}
		}
	}

	class MyMenuEventHandler implements EventHandler<ActionEvent>{
	public void handle(ActionEvent e) {
		if(e.getSource()==mi[0])rule.change_turn(lb1,lb2);
		else if(e.getSource()==mi[1]) {
			guiosero.Reset();
			guiosero.mode=0;
			lb1.setText("  Me_point="+0+"			");
			lb2.setText("Enemy_point="+0);
			guiosero.paintMap(gc);
			}
		else if(e.getSource()==mi[2]) {
			guiosero.mode=1;
			guiosero.paintMap(gc);
			lb1.setText("  Me_point="+rule.count_myPoint(guiosero)+"			");
			lb1.setFont(Font.font("Serif",FontWeight.BLACK,18));
			lb2.setText("Enemy_point="+(rule.count(guiosero)-rule.count_myPoint(guiosero)));
			lb2.setTextFill(Color.GRAY);}
			}
		}
}


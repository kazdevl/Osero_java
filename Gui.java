package osero;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Gui extends Map{

	protected int SIZE; // マス一辺あたりの画素数
	int topMargin; // 上の余白サイズ
	int leftMargin; // 左の余白サイズ
	int rightMargin; // 右の余白サイズ
	int bottomMargin; // 下の余白サイズ
	final String youImageName = "shiro.png";
	final String meImageName = "black.png";
	final String startImageName="start1.jpg";
	final String loseImageName="loser.jpg";
	final String winImageName="winner.jpg";

	Image youImage;
	Image meImage;
	Image startImage;
	Image loseImage;
	Image winImage;

	protected int s=40;

	public Gui() {
		super();
		SIZE = s;
		topMargin =2;
		bottomMargin = 2;
		leftMargin = 20;
		rightMargin =20;

		String dirName ="./";
		youImage = new Image(dirName + youImageName);
		meImage = new Image(dirName + meImageName);
		startImage=new Image(dirName+startImageName);
		loseImage = new Image(dirName + loseImageName);
		winImage=new Image(dirName+winImageName);
	}

	// サイズを返す
	public int[] calcMapSize() {
		int[] size = new int[2];
		size[0] = width * SIZE + leftMargin + rightMargin;
		size[1] = height * SIZE + topMargin + bottomMargin;
		return size;
	}

	public void paintMap(GraphicsContext gs){
		try {
			switch(mode) {
		case 0:
				gs.drawImage(startImage,leftMargin,topMargin, width * SIZE ,height * SIZE );
			break;
		case 1:
			for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				int fd = field[y][x];
				if (fd==0) {
					gs.setFill(Color.GREEN);
					gs.fillRect(x*SIZE+leftMargin, y*SIZE+topMargin, SIZE, SIZE);
				}else if(fd==-1) {
					gs.drawImage(youImage, x*SIZE+leftMargin, y*SIZE+topMargin, SIZE, SIZE);
				}else if (fd==1) {
					gs.drawImage(meImage, x*SIZE+leftMargin, y*SIZE+topMargin, SIZE, SIZE);
				}
			}
		}

			break;
		case 2:
			gs.drawImage(winImage,0,0, width * SIZE + leftMargin + rightMargin,height * SIZE + topMargin + bottomMargin );
			break;
		case 3:
			gs.drawImage(loseImage,0,0, width * SIZE + leftMargin + rightMargin,height * SIZE + topMargin + bottomMargin );
			break;
		default:
			throw new Exception();
			}
		}catch(Exception e) {System.out.println(e);}
	}
}



class Map{
	 int width;
	 int height;
	 int field[][];
	 int mode;

	Map() {
		mode=0;
		width=8;
		height=8;
		field=new int [height][width];
		Reset();
   }
	void Reset() {
		int count=0;

		for(int i=0;i<height;i++) {
		for(int j=0;j<width;j++) {
			if((i==height/2-1&&j==width/2-1)||(i==height/2&&j==width/2)){
				count=1;
			}else if((i==height/2-1&&j==width/2)||(i==height/2&&j==width/2-1)) {
				count=-1;
			}else {
				count=0;
			}
			field[i][j]=count;
			count=0;
		}
	  }
	}
	// マップ情報をテキスト表示
	void printMap() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int f = field[y][x];
				System.out.print(f);//ワンちゃんここが変
			}
			System.out.println();
		}
	}
}


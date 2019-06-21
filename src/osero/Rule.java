package osero;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Rule {
	boolean my_turn;
	boolean can_C;
	boolean exit1;

	Rule() {
		my_turn = true;
		can_C = false;
		exit1 = true;
	}

	boolean change_turn(Label lb1,Label lb2){
		if (my_turn) {
			my_turn = false;
			lb1.setFont(Font.font("Serif",12));
			lb2.setFont(Font.font("Serif",FontWeight.BLACK,18));}
		else if (my_turn != true) {
			my_turn = true;
			lb1.setFont(Font.font("Serif",FontWeight.BLACK,18));
			lb2.setFont(Font.font("Serif",12));}


		return my_turn;
	}
	void judge(Gui o,int get_x,int get_y,int me,int enemy) {

		int count[]= {0,0,0,0,0,0,0,0};
		int nowx[]= {get_x,get_x,get_x,get_x,get_x,get_x,get_x};
		int nowy[]= {get_y,get_y,get_y,get_y,get_y,get_y,get_y};

		//左方向のチェック
		if(o.field[get_y][get_x]==0) {
			for(int i=get_x-1;i>=0;i--) {
				if(o.field[get_y][i]==me) {nowx[0]=i;break;}
				else if(o.field[get_y][i]==enemy) {count[0]++;}
			}
			for(int i=get_x-1;i>=0;i--) {
			nowy[1]--;
			if(nowy[1]>=0) {
				if(o.field[nowy[1]][i]==me) {nowx[1]=i;break;}
				else if(o.field[nowy[1]][i]==enemy) {count[1]++;}
				}
			}
			for(int i=get_x-1;i>=0;i--) {
			nowy[2]++;
			if(nowy[2]<8) {
				if(o.field[nowy[2]][i]==me) {nowx[2]=i;break;}
				else if(o.field[nowy[2]][i]==enemy) {count[2]++;}
				}
			}

			//上下のチェック
			for(int i=get_y-1;i>=0;i--) {
				if(o.field[i][get_x]==me) {nowy[3]=i;break;}
				else if(o.field[i][get_x]==enemy) {count[3]++;}
			}
			for(int i=get_y+1;i<8;i++) {
				if(o.field[i][get_x]==me) {nowy[4]=i;break;}
				else if(o.field[i][get_x]==enemy) {count[4]++;}
			}


			//右方向のチェック
			for(int i=get_x+1;i<8;i++) {
				if(o.field[get_y][i]==me) {nowx[3]=i;break;}
				else if(o.field[get_y][i]==enemy) {count[5]++;}
			}
			for(int i=get_x+1;i<8;i++) {
			nowy[5]--;
			if(nowy[5]>=0) {
				if(o.field[nowy[5]][i]==me) {nowx[4]=i;break;}
				else if(o.field[nowy[5]][i]==enemy) {count[6]++;}
				}
			}
			for(int i=get_x+1;i<8;i++) {
			nowy[6]++;
			if(nowy[6]<8) {
				if(o.field[nowy[6]][i]==me) {nowx[5]=i;break;}
				else if(o.field[nowy[6]][i]==enemy) {count[7]++;}
				}
			}


			//yの初期化
			nowy[1]=nowy[2]=nowy[5]=nowy[6]=get_y;

			//左方向の駒反転
			if(count[0]==get_x-nowx[0]-1&&o.field[get_y][get_x-1]==enemy) {
				for(int i=get_x;i>nowx[0];i--) {
					o.field[nowy[0]][i]=me;
				}}
			if(count[1]==get_x-nowx[1]-1&&o.field[get_y-1][get_x-1]==enemy) {
				for(int i=get_x;i>nowx[1];i--) {
					o.field[nowy[1]][i]=me;
					nowy[1]--;
				}}
			if(count[2]==get_x-nowx[2]-1&&o.field[get_y+1][get_x-1]==enemy) {
				for(int i=get_x;i>nowx[2];i--) {
					o.field[nowy[2]][i]=me;
					nowy[2]++;
						}}

			//上下方向の駒反転
			if(count[3]==get_y-nowy[3]-1&&o.field[get_y-1][get_x]==enemy) {
				for(int i=get_y;i>nowy[3];i--) {
					o.field[i][get_x]=me;
						}}
			if(count[4]==nowy[4]-get_y-1&&o.field[get_y+1][get_x]==enemy) {
				for(int i=get_y;i<nowy[4];i++) {
					o.field[i][get_x]=me;
						}}

			//右方向の駒反転
			if(count[5]==nowx[3]-get_x-1&&o.field[get_y][get_x+1]==enemy) {
				for(int i=get_x;i<nowx[3];i++) {
					o.field[get_y][i]=me;//隣が黒なら実行できてしまう。黒黒になってしまう。ここの修正
				}}
			if(count[6]==nowx[4]-get_x-1&&o.field[get_y-1][get_x+1]==enemy) {
				for(int i=get_x;i<nowx[4];i++) {
					o.field[nowy[5]][i]=me;
					nowy[5]--;
				}}
			if(count[7]==nowx[5]-get_x-1&&o.field[get_y+1][get_x+1]==enemy) {
				for(int i=get_x;i<nowx[5];i++) {
					o.field[nowy[6]][i]=me;
					nowy[6]++;
						}}
		}

	}
	//駒が反転できるか
	boolean can_change(Gui o,int compare) {
		can_C=false;
		int count=0;
			for(int i=0;i<o.height;i++) {
				for(int j=0;j<o.width;j++) {
					if(o.field[i][j]!=0)count++;
				}
			}
			if(compare!=count)can_C=true;
			System.out.println("compare"+compare+"count"+count);
			return can_C;
		}
	//駒数の合計
	int count(Gui o) {
		int count=0;
			for(int i=0;i<o.height;i++) {
				for(int j=0;j<o.width;j++) {
					if(o.field[i][j]!=0)count++;
				}
			}
			return count;
		}
	int count_myPoint(Gui o) {
		int count=0;
			for(int i=0;i<o.height;i++) {
				for(int j=0;j<o.width;j++) {
					if(o.field[i][j]==1)count++;
				}
			}
			return count;
		}
	int win(Gui o,int me,int enemy,int kind) {
		int win = 0;
		int count_m=0;
		int count_e=0;
		int count_n=0;
		for(int i=0;i<o.height;i++) {
			for(int j=0;j<o.width;j++) {
				if(o.field[i][j]==me)count_m++;
				else if(o.field[i][j]==enemy)count_e++;
				else if(o.field[i][j]==0)count_n++;
			}
		}
		if(kind == 0) {
			if(((count_n==0)&&count_m>count_e)||count_e==0)win=1;
			else if(((count_n==0)&&count_m<count_e)||count_m==0)win=2;
			else if(((count_n==0)&&count_m==count_e))win=3;
		}else if(kind == 1){
			if(count_m>count_e)			win = 1;
			else if(count_m<count_e)	win = 2;
			else if(count_m == count_e) win = 3;
		}
		return win;
	}
}

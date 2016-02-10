package ahp;

public class AHP_lib {
	
	//フィールド
	//接続行列
	double C[][];
	//カットベクトル
	double P[][];
	
	//コンストラクタ
	//接続行列C カットベクトルPの初期化を行う
	public AHP_lib(double from_ID[], double to_ID[], double value[]){
		//接続行列の作成
		C = makeConectionMatrix(from_ID, to_ID);
		//カットベクトルの変換
		P = makeCatVector(value);
	}
	
	//接続行列Cを作成
	double[][] makeConectionMatrix(double from_ID[], double to_ID[]){
		//接続行列の要素数を求める
		//toの最大値が行列の行の値になる
		int toMAX = 0;
		for(int i = 0; i < to_ID.length; i++){
			if(to_ID[i] > toMAX){
				toMAX = (int) to_ID[i];
			}
		}
		
		//接続行列
		//行はtoMAXの値、列は渡されたDBの行数を使用する
		double C[][] = new double[toMAX][from_ID.length];

		//カウント用変数
		int k = 0;
		//変化のチェック用変数
		int chk_from, chk_to;
		for(int i = 0; i < C[0].length; i++){
			//チェック変数の中身更新
			chk_from = (int) from_ID[k];
			chk_to = (int) to_ID[k];
			for(int j = 0; j < C.length;j++){
				//fromと行が同じ場合１を代入する
				if(j == chk_from - 1){
					C[j][i] = 1;
				}
				//toと行が同じ場合-1を代入する
				else if(j == chk_to - 1){
					C[j][i] = -1;
				}
				//両方に当てはまらない場合0を入れる
				else{
					C[j][i] = 0;
				}
			}
			//カウント用変数の更新
			k++;
		}
		return C;
	}
	
	//カットベクトルを変換
	double[][] makeCatVector(double value[]){
		//カットベクトルの作成
		P = new double[value.length][1];
		for(int i = 0; i < value.length; i++){
			P[i][0] = Math.log(value[i]);
		}
		return P;
	}

	//評価項目の重要度を計算する（正規方程式を解く）
	double[] makeWeightVectorMatrix(){
		//重要度ベクトル
		double w[] = new double[C.length];
		//Cの転置行列
		double CT[][] = new double[C[0].length][C.length];
		//計算用行列(C*CT)
		double wCCT[][] = new double[C.length][CT.length];
		//計算用行列(C*P)
		double wCP[][] = new double[C.length][P.length];
		//全て1の行列
		double All1[][] = new double[C.length][CT.length];
		//全て１の行列の初期化
		for(int i = 0; i < C.length; i++){
			for(int j = 0; j < CT.length; j++){
				All1[i][j] = 1;
			}
		}
		
		//転置行列の作成
		for(int i = 0; i < C.length; i++){
			for(int j = 0; j < C[0].length; j++){
				CT[j][i] = C[i][j];
			}
		}
		
		//接続行列の転置との掛け算
		wCCT = multiplyingMatrix(C, CT);
		
		//接続行列とカットベクトルの掛け算
		wCP = multiplyingMatrix(C, P);
		
		//接続行列の転置との掛け算に全て１の行列を加算する
		for(int i = 0; i < wCCT.length; i++){
			for(int j = 0; j < wCCT[0].length; j++){
				wCCT[i][j] += All1[i][j];
			}
		}
	
		double wCP_2[] = new double[wCP.length];
		//CPを左辺から右辺に移行
		//ガウスの計算を行うために１次元配列に格納しなおす。
		for(int i = 0; i < wCP.length; i++){
			wCP_2[i] = -wCP[i][0];
		}

		//ガウスの掃き出し法計算
		Gauss_lib GL = new Gauss_lib(wCCT, wCP_2, wCCT.length);
		w = GL.calcGauss();
		
		//指数化
		for(int i = 0; i < w.length; i++){
			w[i] = Math.exp(w[i]);
		}
		
		//正規化
		//総和１に正規化
		//正規化用総和変数
		double sum = 0.0;
		//総和を求める
		for(int i = 0; i < w.length; i++){
			sum += w[i];
		}
		
		//各値を総和で除算
		for(int i = 0; i < w.length; i++){
			w[i] /= sum;
		}

		return w;
	}
	

	//行列の掛け算
	double[][] multiplyingMatrix(double a[][], double b[][]){
		double result[][] = new double[a.length][b[0].length];
		for(int i = 0; i < result.length; i++){
			for(int j = 0; j < result[0].length; j++){
				for(int k = 0; k < b.length; k++){
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return result;
	}
	
	//getter setter
	public double[][] getC() {
		return C;
	}
	public void setC(double[][] c) {
		C = c;
	}
	public double[][] getP() {
		return P;
	}
	public void setP(double[][] p) {
		P = p;
	}	

}

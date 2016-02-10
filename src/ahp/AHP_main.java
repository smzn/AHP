package ahp;

public class AHP_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//一対比較行列からコネクション行列とカット行列を作成する
		//user_ID,from_ID,to_ID,valueを各配列に格納する

		//フィールド
		double from_ID[] = {1, 1, 2, 1, 1, 2};
		double to_ID[] = {2, 3, 3, 2, 3, 3};
		double value[] = {9.0, 7.0, 3.0, 7.0, 9.0, 0.333 };
		double w[];//重要度
				
		//from to valueから接続行列、カットベクトルを作成する
		AHP_lib CL = new AHP_lib(from_ID, to_ID, value);

		//正規方程式を解く
		w = CL.makeWeightVectorMatrix();
				
		//重要度の表示
		System.out.println("重要度");
		for(int i = 0; i < w.length; i++){
			System.out.print(w[i] + "\t");
		}
	}

}

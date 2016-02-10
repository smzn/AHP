package ahp;

public class Gauss_lib {
	private double a[][];
	private double b[];
	private int N;
	
	public Gauss_lib(double[][] a, double[] b, int N) {
		this.a = a;
		this.b = b;
		this.N = N;
	}
	
	public double [] calcGauss(){
		int p;
		double pmax, s;
		double w[] = new double[N];
		/* 前進消去（ピボット選択）*/
		for(int k = 0; k < N-1; k++){  /* 第ｋステップ */
		      p = k;
		      pmax = Math.abs( a[k][k] );
		      for(int i = k+1; i < N; i++){  /* ピボット選択 */
		         if(Math.abs( a[i][k] ) > pmax){
		            p = i;
		            pmax = Math.abs( a[i][k] );
		         }
		      }

		      if(p != k){  /* 第ｋ行と第ｐ行の交換　*/
		         for(int i = k; i < N; i++){
		            /* 係数行列　*/
		            s = a[k][i];
		            a[k][i] = a[p][i];
		            a[p][i] = s;
		         }
		         /* 既知ベクトル */
		         s = b[k];
		         b[k] = b[p];
		         b[p] = s;
		      }
		/* 前進消去 */
		      for(int i = k +1; i < N; i++){ /* 第ｉ行 */
		         w[i] = a[i][k] / a[k][k];
		         a[i][k] = 0.0;
		         /* 第ｋ行を-a[i][k]/a[k][k]倍して、第ｉ行に加える */
		         for(int j = k + 1; j < N; j++){
		            a[i][j] = a[i][j] - a[k][j] * w[i];
		         }
		         b[i] = b[i] - b[k] * w[i];
		      }
		   }
		/* 後退代入 */
		      for(int i = N - 1; i >= 0; i--){
		         for(int j = i + 1; j < N; j++){
		            b[i] = b[i] - a[i][j] * b[j];
		            a[i][j] = 0.0;
		         }
		         b[i] = b[i] / a[i][i];
		         a[i][i] = 1.0;
		      }
		
		return b;
	}
	
	

}

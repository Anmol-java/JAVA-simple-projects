import java.util.*;
class MatrixSort {
public static void main(String args[]){
Scanner sc = new Scanner(System.in);
int M = sc.nextInt();
int N = sc.nextInt();
if(M<=2 || M>=20 || N<=2 || N>=20){
System.out.println("SIZE OUT OF RANGE");
return;
}
int a[][] = new int[M][N];
for(int i=0;i<M;i++)
for(int j=0;j<N;j++)
a[i][j] = sc.nextInt();
System.out.println("ORIGINAL MATRIX");
for(int i=0;i<M;i++){
for(int j=0;j<N;j++)
System.out.print(a[i][j]+" ");
System.out.println();
}
int max=a[0][0], min=a[0][0];
int rmax=0,cmax=0, rmin=0,cmin=0;
ArrayList<Integer> list = new ArrayList<>();
for(int i=0;i<M;i++){
for(int j=0;j<N;j++){
list.add(a[i][j]);
if(a[i][j] > max){ max=a[i][j]; rmax=i;cmax=j; }
if(a[i][j] < min){ min=a[i][j]; rmin=i;cmin=j; }
}
}
System.out.println("LARGEST NUMBER: "+max+"\nROW = "+rmax+"\nCOLUMN ="+cmax);
System.out.println("SMALLEST NUMBER: "+min+"\nROW = "+rmin+"\nCOLUMN= "+cmin);
Collections.sort(list);
int idx=0;
// ascending
System.out.println("REARRANGED MATRIX");
for(int i=0;i<M;i++){
for(int j=0;j<N;j++){
System.out.print(list.get(idx++)+" ");
}
System.out.println();
}
}
}




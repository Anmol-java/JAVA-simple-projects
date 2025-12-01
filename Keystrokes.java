import java.util.*;
class Keystrokes {
static int count(char ch){
ch = Character.toUpperCase(ch);
if("ABC".indexOf(ch)>=0) return "ABC".indexOf(ch)+1;
if("DEF".indexOf(ch)>=0) return "DEF".indexOf(ch)+1;
if("GHI".indexOf(ch)>=0) return "GHI".indexOf(ch)+1;
if("JKL".indexOf(ch)>=0) return "JKL".indexOf(ch)+1;
if("MNO".indexOf(ch)>=0) return "MNO".indexOf(ch)+1;
if("PQRS".indexOf(ch)>=0) return "PQRS".indexOf(ch)+1;
if("TUV".indexOf(ch)>=0) return "TUV".indexOf(ch)+1;
if("WXYZ".indexOf(ch)>=0) return "WXYZ".indexOf(ch)+1;
return -1;
}
public static void main(String args[]){
Scanner sc = new Scanner(System.in);
System.err.println("Enter");
String s = sc.nextLine();
for(char ch : s.toCharArray())
if(!Character.isLetter(ch)){
System.out.println("INVALID ENTRY");
return;
}
int total = 0;
for(char ch : s.toCharArray())
total += count(ch);
System.out.println("Number of keystrokes = " + total);
}
}
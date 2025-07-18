import java.util.Scanner;

public class CRC{

    private static String XOR(String a,String b){
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<b.length();i++){
            sb.append(a.charAt(i)==b.charAt(i)?'0':'1');
        }
        return sb.toString();
    }

    private static  String mod2Div(String dividend,String Divisor){
        int pick=Divisor.length();
        String newStr=dividend.substring(0,pick);
        while(pick< dividend.length()){
            if(newStr.charAt(0)=='1'){
                newStr=XOR(Divisor,newStr)+dividend.charAt(pick);
            }
            else{
                newStr=XOR("0".repeat(pick),newStr)+dividend.charAt(pick);
            }
            pick++;
            newStr.substring(1);
        }

        //last bit
        if(newStr.charAt(0)=='1'){
            newStr=XOR(Divisor,newStr);
        }else{
            newStr=XOR("0".repeat(pick),newStr);
        }
         return newStr;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        System.out.println("Enter your bits(Divident): ");
        String data=sc.nextLine();

        System.out.println("Enter your Divisors: ");
        String Divisor=sc.nextLine();
        String appeandData=data+"0".repeat(Divisor.length()-1);

        System.out.println("Data: "+data);
        System.out.println("Divisors: "+Divisor);
        System.out.println("Append data: " +appeandData);

        String remiander=mod2Div(appeandData, Divisor);
        System.out.println("CRC Remainder: "+remiander);

        String transmitted=data+remiander;
        System.out.println("Tranmitted data: "+transmitted);
        sc.close();
    }
}
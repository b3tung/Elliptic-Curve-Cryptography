import java.util.Scanner;
import java.lang.Math;

class ecc {

    public int coeff_a;
    public int coeff_b;
    public int prime_modulo;
    class coords {
        private int x;
        private int y;

        private int gcd(int a, int b) 
        {
            if (a == 0) {
                return b;
            }

            return gcd(b % a, a);
        }

        //private function to solve for modulo inverse using fermat's little theorem
        //a^-1 = a^(n-2) mod n
        private int modInverse(int a, int n){
            if( gcd(Math.abs(a), n) != 1){
                return -1;
            }
            if( a < 0){
                return -(power(Math.abs(a), n-2, n)) ;
            }
            return power(a, n-2, n);
        }

        private int power(int x,int y,int m)
        {
            int result = 1;
            for (int i = 0; i < y; i++) {
                result = ((result * x) % m);
            }
            return result;
        }
        
        public coords(int x, int y){
            super();
            this.x = x;
            this.y = y;
        }   

        public int get_x(){
            return x;
        }

        public int get_y(){
            return y;
        }

        public boolean equals(coords op){
            return (this.x == op.get_x() && this.y == op.get_y());
        }

        public coords add(coords op){
            int slope = 0;
            int new_x, new_y, denom;
            if(!equals(op)){
                denom = this.x - op.get_x();
                slope = (this.y - op.get_y())*modInverse(denom, prime_modulo) % prime_modulo;
            }
            else{
                denom = this.y * 2;
                slope = (3*this.x*this.x + coeff_a)*modInverse(denom, prime_modulo) % prime_modulo;
            }
            slope = (slope + prime_modulo) % prime_modulo;
            new_x = (((slope*slope - this.x - op.x) % prime_modulo) + prime_modulo) % prime_modulo;
            new_y = (((slope*(this.x - new_x) - this.y) % prime_modulo) + prime_modulo) % prime_modulo;
            return new coords(new_x, new_y);
        }

        public coords subtract(coords op){
            op.negation();
            return this.add(op);
        }

        public void negation(){
            y = -y;
        }
    }

    public static void main(String [] args){
       ecc program = new ecc();
       program.run();
    }

    public void run(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Equation of elliptic curve is y^2 mod p = x^3 + ax + b mod p. \nEnter coefficient a: ");
        coeff_a = reader.nextInt();
        System.out.println("Enter coeffecient b: ");
        coeff_b = reader.nextInt();
        System.out.println("Enter prime modulo: ");
        prime_modulo = reader.nextInt();
        reader.close();
        System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + " + coeff_a + "x + " + coeff_b + " mod " + prime_modulo);

        coords P = new coords(3, 10);
        coords Q = new coords(9, 7);
        P.add(Q);
    }





    private String encryption(String data){

        return null;
    }

    private String decryption(String data){
        return null;
    }

    private void keyGenerator(){

    }

    


}
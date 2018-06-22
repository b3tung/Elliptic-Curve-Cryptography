import java.util.Scanner;
import java.lang.Math;

class ecc {

    public int coeff_a = 0;
    public int coeff_b = 0;
    public int prime_modulo = 2;

    class coords {
        private int x;
        private int y;

        //constructor
        //todo: check if points are on curve
        //todo: create default constructor to randomly create points on curve
        public coords(int x, int y){
            super();
            this.x = x;
            this.y = y;
        } 
        public void print(){
            System.out.println("x: " + x + " ,y: " + y);
        }
        //function to find the greatest common divisor (gcd) of two numbers 
        private int gcd(int a, int b) 
        {
            if (a == 0) {
                return b;
            }

            return gcd(b % a, a);
        }

        //"zero" point on an elliptic curve
        private coords zero(){
            return new coords(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        //returns whether or not a point is zero
        private boolean isZero(){
            return this.x==Integer.MAX_VALUE || this.y==Integer.MAX_VALUE;
        }

        //function to solve for modulo inverse using fermat's little theorem
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

        //function to find x^y mod m
        private int power(int x,int y,int m)
        {
            int result = 1;
            for (int i = 0; i < y; i++) {
                result = ((result * x) % m);
            }
            return result;
        }
          
        //getter method for x_coordinate
        public int get_x(){
            return x;
        }

        //getter method for y_coordinate
        public int get_y(){
            return y;
        }

        //returns whether or not two coordinates are equal
        public boolean equals(coords op){
            return (this.x == op.get_x() && this.y == op.get_y());
        }

        //'adds' two points together and returns a third point on the curve
        public coords add(coords op){
            int slope = 0;
            int new_x, new_y, denom;
            if(this.isZero()){
                return op;
            }
            else if(!this.equals(op)){
                denom = this.x - op.get_x();
                slope = (this.y - op.get_y())*modInverse(denom, prime_modulo) % prime_modulo;
            }
            else{
                denom = this.y * 2;
                slope = (3*(int)Math.pow(this.x,2) + coeff_a)*modInverse(denom, prime_modulo) % prime_modulo;
            }
            slope = (slope + prime_modulo) % prime_modulo;
            new_x = (((int)(Math.pow(slope, 2) - this.x - op.x) % prime_modulo) + prime_modulo) % prime_modulo;
            new_y = (((slope*(this.x - new_x) - this.y) % prime_modulo) + prime_modulo) % prime_modulo;
            return new coords(new_x, new_y);
        }

        //'subtracts' two points i.e. P - Q = P + (-Q)
        public coords subtract(coords op){
            op.negation();
            return this.add(op);
        }

        //negates a point; P -> -P
        public void negation(){
            y = prime_modulo - y;
        }

        //adds a point to itself
        private coords point_doubling(){
            return this.add(this);
        }

        //multiplies a point by x by using doubling and add method; 3P = P + 2P; runs log2 time 
        public coords multiplication(int x){
            int binary = Integer.parseInt(Integer.toBinaryString(x));
            System.out.println(binary);
            coords Q = zero();
            coords N = this;
            while( binary > 0){
                if(binary%10 == 1){
                    Q = Q.add(N);
                }
                N = N.point_doubling();
                binary/=10;
            }
            return Q;
        }

        
    }

    public static void main(String [] args){
       ecc program = new ecc();
       program.run();
    }

    public void run(){
        Scanner reader = new Scanner(System.in);
        do{
            System.out.println("Equation of elliptic curve is y^2 mod p = x^3 + ax + b mod p. \nEnter coefficient a: ");
            coeff_a = reader.nextInt();
            System.out.println("Enter coeffecient b: ");
            coeff_b = reader.nextInt();
            if(singular()){
                System.out.println("Equation of elliptic curve is singular. Enter new coefficients such that 4a^3 != 27b^2");
            }
        }while(singular());

        do{
            System.out.println("Enter prime modulo: ");
            prime_modulo = reader.nextInt();
            if(!isPrime(prime_modulo)){
                System.out.println("Not a prime number, please enter another number");
            }
        }while(!isPrime(prime_modulo));

        reader.close();
        if(coeff_a == 0){
            System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + " + coeff_b + " mod " + prime_modulo);
        }
        else if(coeff_a == 1){
            if(coeff_b == 0){
                System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + x + mod " + prime_modulo);
            }
            else{
                System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + x + " + coeff_b + " mod " + prime_modulo);
            }
        }
        else{
            if(coeff_b == 0){
                System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + " + coeff_a + "x mod " + prime_modulo);

            }
            else{
                System.out.println("Equation has form y^2 mod " + prime_modulo + " = x^3 + " + coeff_a + "x + " + coeff_b + " mod " + prime_modulo);
            }
        }
        coords P = new coords(3, 10);
        coords result = P.multiplication(2);

    }

    private boolean isPrime(int num){
        if(num %2 == 0){
            return false;
        }
        for(int i = 3; i*i <= num ; i+=2){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    private boolean singular(){
        if(4*Math.pow(coeff_a, 3) == 27*Math.pow(coeff_b, 2)){
            return true;
        }

        return false;

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
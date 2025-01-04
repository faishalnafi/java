    class animal {
        void eat(){
            System.out.println("eating.....");
        }
    }

    class dog extends animal {
        void bark() {
            System.out.println("Barking.....");
        }
    }

    class babyDog extends dog {
        void weep() {
            System.out.println("weeping.....");
        }
    }

    public class Pewarisan_OOP {
        public static void main (String[] args) {
            babyDog apah = new babyDog();
            apah.weep();
            apah.bark();
            apah.eat(); 
        }
    }
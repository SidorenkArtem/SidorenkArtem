class Player {

    class Human extends Player{

         private char element;


         public Human(){}

         public void setElement(char element) {
             this.element = element;
         }

         public char getElement() {
             return element;
         }

         public String toString() {
             return "I am a human!";
         }

    }

     class Bot extends Player{

         private char element;
         private int max_random = 9;

         public Bot(){}

         public void setElement(char element) {
             this.element = element;
         }

         public char getElement() {
             return element;
         }


         int botPosition(char[] array_of_positions) {
             int value;
             while (true) {
                 value = (int) (Math.random() * max_random );
                 if (freePosition(array_of_positions, value)) {
                     return value;

                 }else
                    continue;
             }

         }

        boolean freePosition(char[] array_of_positions, int index){
            return array_of_positions[index] == ' ' ? true : false;
        }

         public String toString(){
             return "I am a bot!";
         }


    }
}

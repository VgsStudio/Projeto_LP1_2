package group.mpntm.client;
//import com.google.code.gson;

public class Main {
//    private class Person {
//        public String name;
//
//        public Person(String name) {
//            this.name = name;
//        }
//    }
    public static void main(String[] args) {
        //first create client not initialized yet
        //create gui and inject client
        //on login button pressed event connect method to start login process in the client
        //login process:
            //validate client side input data
            //create a thread to run the request process
            //send request to the server passing username and encrypted password
            //wait for response
            //fire an event sending the server response as argument
        //gui was listening for the event and will handle the success or failure of the login process
        //some the login singleton in the client will be updated with the user data for later use

        Client client = new Client();
        ClientScreen clientScreen = new ClientScreen(client);
        clientScreen.setVisible(true);


//
//   Gson g = new Gson();
////
////         De-serialize to an object
//        Person person = g.fromJson("{\"name\": \"John\"}", Person.class);
//        System.out.println(person.name); //John
//
    }
}

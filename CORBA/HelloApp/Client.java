import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "HelloService";
            Hello helloRef = HelloHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Got reference to HelloService");
            String result = helloRef.sayHello("Student");
            System.out.println("Response from server: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
import CalculatorApp.*;
import FactorialApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client2 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Calculator calc = CalculatorHelper.narrow(ncRef.resolve_str("CalculatorService"));
            Factorial fact = FactorialHelper.narrow(ncRef.resolve_str("FactorialService"));

            System.out.println("Addition: " + calc.add(10, 20));
            System.out.println("Multiplication: " + calc.mul(5, 4));
            System.out.println("Factorial(5): " + fact.compute(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
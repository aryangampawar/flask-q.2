package CalculatorApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client1 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Calculator calc = CalculatorHelper.narrow(ncRef.resolve_str("CalculatorService"));

            System.out.println("Addition (10 + 5): " + calc.add(10, 5));
            System.out.println("Subtraction (10 - 5): " + calc.sub(10, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
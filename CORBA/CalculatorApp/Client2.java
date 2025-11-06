package CalculatorApp;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class Client2 {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Calculator calc = CalculatorHelper.narrow(ncRef.resolve_str("CalculatorService"));

            System.out.println("Multiplication (6 * 4): " + calc.mul(6, 4));
            System.out.println("Division (20 / 4): " + calc.div(20, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
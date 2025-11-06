import StringApp.*;
import CalculatorApp.*;
import FactorialApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class Server {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create & register services
            StringImpl strImpl = new StringImpl();
            CalculatorImpl calcImpl = new CalculatorImpl();
            FactorialImpl factImpl = new FactorialImpl();

            org.omg.CORBA.Object ref1 = rootpoa.servant_to_reference(strImpl);
            org.omg.CORBA.Object ref2 = rootpoa.servant_to_reference(calcImpl);
            org.omg.CORBA.Object ref3 = rootpoa.servant_to_reference(factImpl);

            StringOps strRef = StringOpsHelper.narrow(ref1);
            Calculator calcRef = CalculatorHelper.narrow(ref2);
            Factorial factRef = FactorialHelper.narrow(ref3);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            ncRef.rebind(ncRef.to_name("StringService"), strRef);
            ncRef.rebind(ncRef.to_name("CalculatorService"), calcRef);
            ncRef.rebind(ncRef.to_name("FactorialService"), factRef);

            System.out.println("✅ All services registered.");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
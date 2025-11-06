import java.rmi.*;

public interface Adder extends Remote {
  int add(int x, int y, int clientPort) throws RemoteException;
}
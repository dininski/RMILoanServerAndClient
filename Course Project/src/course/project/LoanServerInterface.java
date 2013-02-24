/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author support
 */
public interface LoanServerInterface extends Remote {

    public double calculateMonthlyPayment(double loanAmount,double numberOfYears,
            double annualInterestRate, String currency) throws RemoteException;
}

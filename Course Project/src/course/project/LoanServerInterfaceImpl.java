/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course.project;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author support
 */
public class LoanServerInterfaceImpl extends UnicastRemoteObject implements LoanServerInterface {

    private double exchangeRate;
    private double rate;
    private double monthlyPmt;
    
    public LoanServerInterfaceImpl() throws RemoteException {
        super();
    }
    
    @Override
    public double calculateMonthlyPayment(double loanAmount, double numberOfYears, 
    double annualInterestRate, String currency) throws RemoteException {
        exchangeRate = FindExchangeRateXML.FindExchangeRatesXML(currency);
        loanAmount = loanAmount*exchangeRate;
        rate = annualInterestRate/1200;
        monthlyPmt = (loanAmount*(rate + rate/(Math.pow(1+rate, numberOfYears*12)-1)));
        String result = String.format("Annual Interest Rate: %.2f, Number of years:%.2f, Loan Amount: %.2f %s, monthly payment in BGN: %.2f\n", annualInterestRate, numberOfYears, loanAmount, currency, monthlyPmt);
        System.out.println(result);
        try {
            saveInFile(result);
        } catch (IOException ex) {
            Logger.getLogger(LoanServerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return monthlyPmt;
    }
    
    private void saveInFile(String result) throws IOException {
        FileWriter fw = new FileWriter("log.txt", true);
        fw.write(result+"\r\n");
        fw.close();
    }
    
}

package model.service;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		double simpleQuota = contract.getTotalValue() / months;
		
		for(int i = 1; i <= months; i++) {
			Date date = addMonths(contract.getDate(), i);
			double updatedQuota = simpleQuota + onlinePaymentService.interest(simpleQuota, i);
			double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
			contract.addInstallment(new Installment(date, fullQuota));
		}
	}

	public Date addMonths (Date date, int N) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, N);
		return cal.getTime();
	}
	
}

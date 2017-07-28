package com.example.starxder.meal.Bean;

public class Summary {
	//结账情况
	BillDetail billDetail;
	//收款明细
	PayDetail payDetail;
	//大类收入
	SortIncome sortIncome;
	
	public BillDetail getBillDetail() {
		return billDetail;
	}
	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}
	public PayDetail getPayDetail() {
		return payDetail;
	}
	public void setPayDetail(PayDetail payDetail) {
		this.payDetail = payDetail;
	}
	public SortIncome getSortIncome() {
		return sortIncome;
	}
	public void setSortIncome(SortIncome sortIncome) {
		this.sortIncome = sortIncome;
	}
}

package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Rental calculation (TC) Detail Service.
 * @author Sanatha
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   25-MAR-2021   		      FX-6064    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationRequestResource {
	
	private String pMode;
	private String pUser;
	private String pTrhdMe;
	private String pTrhdLType;
	private String pTrhdMethod;
	private String pTrhdBrh;
	private String pTrhdTerm;
	private String pTrhdNoPre;
	private String pTrhdNoDw;
	private String pTrhdTr;
	private String pTrhdLocCost;
	private String pTrhdStmYn;
	private String pTrhdStmPer1;
	private String pTrhdStmDuty1;
	private String pTrhdInvTax;
	private String pTrhdInvTaxRt;
	private String pTrhdCurCode;
	private String pTrhdBus;
	private String pTrhdCrib;
	private String pTrhdFlexi;
	private String pTrhdBsCd;
	private String pTrhdBsTr;
	private String pTrhdMgTr;
	private String pTrhdReSeq;
	private String pTrhdCustTyp;
	private String pTrhdReward;
	private String pTrhdQuo;
	private String pTrhdStmApp;
	private String pTrhdInsuCoverFlg;
	private String pTrhdInsuCoverAmt;
	private String pTrhdIntrType;
	private String pTrhdRewardPre;
	private String pTrhdRewardType;
	private String pTrhdRewardBusagent;
	private String pTrhdRewardAddMethod;
	private String pTrhdSplitReward;
	private String pInsuOption;
	private String pInsuAddCrit;
	private String pTrhdDep;
	private String pTrhdRv;
	private String pAttribute1;
	private String pAttribute2;
	private String pAttribute3;
	private String pAttribute4;
	private String pTrhdVatUpfrnt;
	private String pTrhdVatChg;
	
	private List<RentalCalculationpTreqRequestResource> pTreq;
	
	private List<RentalCalculationpOperRequestResource> pOper;
	
	private List<RentalCalculationpTrtxRequestResource> pTrtx;
	
	private List<RentalCalculationpSpChargeRequestResource> pSpCharge;
	
	private List<RentalCalculationpSnrvRequestResource> pSnrv;
	
	private List<RentalCalculationpStruRequestResource> pStru;
	
	
	public String getpMode() {
		return pMode;
	}
	public void setpMode(String pMode) {
		this.pMode = pMode;
	}
	public String getpUser() {
		return pUser;
	}
	public void setpUser(String pUser) {
		this.pUser = pUser;
	}
	public String getpTrhdMe() {
		return pTrhdMe;
	}
	public void setpTrhdMe(String pTrhdMe) {
		this.pTrhdMe = pTrhdMe;
	}
	public String getpTrhdLType() {
		return pTrhdLType;
	}
	public void setpTrhdLType(String pTrhdLType) {
		this.pTrhdLType = pTrhdLType;
	}
	public String getpTrhdMethod() {
		return pTrhdMethod;
	}
	public void setpTrhdMethod(String pTrhdMethod) {
		this.pTrhdMethod = pTrhdMethod;
	}
	public String getpTrhdBrh() {
		return pTrhdBrh;
	}
	public void setpTrhdBrh(String pTrhdBrh) {
		this.pTrhdBrh = pTrhdBrh;
	}
	public String getpTrhdTerm() {
		return pTrhdTerm;
	}
	public void setpTrhdTerm(String pTrhdTerm) {
		this.pTrhdTerm = pTrhdTerm;
	}
	public String getpTrhdNoPre() {
		return pTrhdNoPre;
	}
	public void setpTrhdNoPre(String pTrhdNoPre) {
		this.pTrhdNoPre = pTrhdNoPre;
	}
	public String getpTrhdNoDw() {
		return pTrhdNoDw;
	}
	public void setpTrhdNoDw(String pTrhdNoDw) {
		this.pTrhdNoDw = pTrhdNoDw;
	}
	public String getpTrhdTr() {
		return pTrhdTr;
	}
	public void setpTrhdTr(String pTrhdTr) {
		this.pTrhdTr = pTrhdTr;
	}
	public String getpTrhdLocCost() {
		return pTrhdLocCost;
	}
	public void setpTrhdLocCost(String pTrhdLocCost) {
		this.pTrhdLocCost = pTrhdLocCost;
	}
	public String getpTrhdStmYn() {
		return pTrhdStmYn;
	}
	public void setpTrhdStmYn(String pTrhdStmYn) {
		this.pTrhdStmYn = pTrhdStmYn;
	}
	public String getpTrhdStmPer1() {
		return pTrhdStmPer1;
	}
	public void setpTrhdStmPer1(String pTrhdStmPer1) {
		this.pTrhdStmPer1 = pTrhdStmPer1;
	}
	public String getpTrhdStmDuty1() {
		return pTrhdStmDuty1;
	}
	public void setpTrhdStmDuty1(String pTrhdStmDuty1) {
		this.pTrhdStmDuty1 = pTrhdStmDuty1;
	}
	public String getpTrhdInvTax() {
		return pTrhdInvTax;
	}
	public void setpTrhdInvTax(String pTrhdInvTax) {
		this.pTrhdInvTax = pTrhdInvTax;
	}
	public String getpTrhdInvTaxRt() {
		return pTrhdInvTaxRt;
	}
	public void setpTrhdInvTaxRt(String pTrhdInvTaxRt) {
		this.pTrhdInvTaxRt = pTrhdInvTaxRt;
	}
	public String getpTrhdCurCode() {
		return pTrhdCurCode;
	}
	public void setpTrhdCurCode(String pTrhdCurCode) {
		this.pTrhdCurCode = pTrhdCurCode;
	}
	public String getpTrhdBus() {
		return pTrhdBus;
	}
	public void setpTrhdBus(String pTrhdBus) {
		this.pTrhdBus = pTrhdBus;
	}
	public String getpTrhdCrib() {
		return pTrhdCrib;
	}
	public void setpTrhdCrib(String pTrhdCrib) {
		this.pTrhdCrib = pTrhdCrib;
	}
	public String getpTrhdFlexi() {
		return pTrhdFlexi;
	}
	public void setpTrhdFlexi(String pTrhdFlexi) {
		this.pTrhdFlexi = pTrhdFlexi;
	}
	public String getpTrhdBsCd() {
		return pTrhdBsCd;
	}
	public void setpTrhdBsCd(String pTrhdBsCd) {
		this.pTrhdBsCd = pTrhdBsCd;
	}
	public String getpTrhdBsTr() {
		return pTrhdBsTr;
	}
	public void setpTrhdBsTr(String pTrhdBsTr) {
		this.pTrhdBsTr = pTrhdBsTr;
	}
	public String getpTrhdMgTr() {
		return pTrhdMgTr;
	}
	public void setpTrhdMgTr(String pTrhdMgTr) {
		this.pTrhdMgTr = pTrhdMgTr;
	}
	public String getpTrhdReSeq() {
		return pTrhdReSeq;
	}
	public void setpTrhdReSeq(String pTrhdReSeq) {
		this.pTrhdReSeq = pTrhdReSeq;
	}
	public String getpTrhdCustTyp() {
		return pTrhdCustTyp;
	}
	public void setpTrhdCustTyp(String pTrhdCustTyp) {
		this.pTrhdCustTyp = pTrhdCustTyp;
	}
	public String getpTrhdReward() {
		return pTrhdReward;
	}
	public void setpTrhdReward(String pTrhdReward) {
		this.pTrhdReward = pTrhdReward;
	}
	public String getpTrhdQuo() {
		return pTrhdQuo;
	}
	public void setpTrhdQuo(String pTrhdQuo) {
		this.pTrhdQuo = pTrhdQuo;
	}
	public String getpTrhdStmApp() {
		return pTrhdStmApp;
	}
	public void setpTrhdStmApp(String pTrhdStmApp) {
		this.pTrhdStmApp = pTrhdStmApp;
	}
	public String getpTrhdInsuCoverFlg() {
		return pTrhdInsuCoverFlg;
	}
	public void setpTrhdInsuCoverFlg(String pTrhdInsuCoverFlg) {
		this.pTrhdInsuCoverFlg = pTrhdInsuCoverFlg;
	}
	public String getpTrhdInsuCoverAmt() {
		return pTrhdInsuCoverAmt;
	}
	public void setpTrhdInsuCoverAmt(String pTrhdInsuCoverAmt) {
		this.pTrhdInsuCoverAmt = pTrhdInsuCoverAmt;
	}
	public String getpTrhdIntrType() {
		return pTrhdIntrType;
	}
	public void setpTrhdIntrType(String pTrhdIntrType) {
		this.pTrhdIntrType = pTrhdIntrType;
	}
	public String getpTrhdRewardPre() {
		return pTrhdRewardPre;
	}
	public void setpTrhdRewardPre(String pTrhdRewardPre) {
		this.pTrhdRewardPre = pTrhdRewardPre;
	}
	public String getpTrhdRewardType() {
		return pTrhdRewardType;
	}
	public void setpTrhdRewardType(String pTrhdRewardType) {
		this.pTrhdRewardType = pTrhdRewardType;
	}
	public String getpTrhdRewardBusagent() {
		return pTrhdRewardBusagent;
	}
	public void setpTrhdRewardBusagent(String pTrhdRewardBusagent) {
		this.pTrhdRewardBusagent = pTrhdRewardBusagent;
	}
	public String getpTrhdRewardAddMethod() {
		return pTrhdRewardAddMethod;
	}
	public void setpTrhdRewardAddMethod(String pTrhdRewardAddMethod) {
		this.pTrhdRewardAddMethod = pTrhdRewardAddMethod;
	}
	public String getpTrhdSplitReward() {
		return pTrhdSplitReward;
	}
	public void setpTrhdSplitReward(String pTrhdSplitReward) {
		this.pTrhdSplitReward = pTrhdSplitReward;
	}
	public String getpInsuOption() {
		return pInsuOption;
	}
	public void setpInsuOption(String pInsuOption) {
		this.pInsuOption = pInsuOption;
	}
	public String getpInsuAddCrit() {
		return pInsuAddCrit;
	}
	public void setpInsuAddCrit(String pInsuAddCrit) {
		this.pInsuAddCrit = pInsuAddCrit;
	}
	public String getpTrhdDep() {
		return pTrhdDep;
	}
	public void setpTrhdDep(String pTrhdDep) {
		this.pTrhdDep = pTrhdDep;
	}
	public String getpTrhdRv() {
		return pTrhdRv;
	}
	public void setpTrhdRv(String pTrhdRv) {
		this.pTrhdRv = pTrhdRv;
	}
	public String getpAttribute1() {
		return pAttribute1;
	}
	public void setpAttribute1(String pAttribute1) {
		this.pAttribute1 = pAttribute1;
	}
	public String getpAttribute2() {
		return pAttribute2;
	}
	public void setpAttribute2(String pAttribute2) {
		this.pAttribute2 = pAttribute2;
	}
	public String getpAttribute3() {
		return pAttribute3;
	}
	public void setpAttribute3(String pAttribute3) {
		this.pAttribute3 = pAttribute3;
	}
	public String getpAttribute4() {
		return pAttribute4;
	}
	public void setpAttribute4(String pAttribute4) {
		this.pAttribute4 = pAttribute4;
	}
	public String getpTrhdVatUpfrnt() {
		return pTrhdVatUpfrnt;
	}
	public void setpTrhdVatUpfrnt(String pTrhdVatUpfrnt) {
		this.pTrhdVatUpfrnt = pTrhdVatUpfrnt;
	}
	public String getpTrhdVatChg() {
		return pTrhdVatChg;
	}
	public void setpTrhdVatChg(String pTrhdVatChg) {
		this.pTrhdVatChg = pTrhdVatChg;
	}
	public List<RentalCalculationpTreqRequestResource> getpTreq() {
		return pTreq;
	}
	public void setpTreq(List<RentalCalculationpTreqRequestResource> pTreq) {
		this.pTreq = pTreq;
	}
	public List<RentalCalculationpOperRequestResource> getpOper() {
		return pOper;
	}
	public void setpOper(List<RentalCalculationpOperRequestResource> pOper) {
		this.pOper = pOper;
	}
	public List<RentalCalculationpTrtxRequestResource> getpTrtx() {
		return pTrtx;
	}
	public void setpTrtx(List<RentalCalculationpTrtxRequestResource> pTrtx) {
		this.pTrtx = pTrtx;
	}
	public List<RentalCalculationpSpChargeRequestResource> getpSpCharge() {
		return pSpCharge;
	}
	public void setpSpCharge(List<RentalCalculationpSpChargeRequestResource> pSpCharge) {
		this.pSpCharge = pSpCharge;
	}
	public List<RentalCalculationpSnrvRequestResource> getpSnrv() {
		return pSnrv;
	}
	public void setpSnrv(List<RentalCalculationpSnrvRequestResource> pSnrv) {
		this.pSnrv = pSnrv;
	}
	public List<RentalCalculationpStruRequestResource> getpStru() {
		return pStru;
	}
	public void setpStru(List<RentalCalculationpStruRequestResource> pStru) {
		this.pStru = pStru;
	}
	@Override
	public String toString() {
		return "{'pMode'='" + pMode + "', 'pUser'='" + pUser + "', 'pTrhdMe'='" + pTrhdMe
				+ "', 'pTrhdLType'='" + pTrhdLType + "', 'pTrhdMethod'='" + pTrhdMethod + "', 'pTrhdBrh'='" + pTrhdBrh
				+ "', 'pTrhdTerm'='" + pTrhdTerm + "', 'pTrhdNoPre'='" + pTrhdNoPre + "', 'pTrhdNoDw'='" + pTrhdNoDw + "', 'pTrhdTr'='"
				+ pTrhdTr + "', 'pTrhdLocCost'='" + pTrhdLocCost + "', 'pTrhdStmYn'='" + pTrhdStmYn + "', 'pTrhdStmPer1'='"
				+ pTrhdStmPer1 + "', 'pTrhdStmDuty1'='" + pTrhdStmDuty1 + "', 'pTrhdInvTax'='" + pTrhdInvTax
				+ "', 'pTrhdInvTaxRt'='" + pTrhdInvTaxRt + "', 'pTrhdCurCode'='" + pTrhdCurCode + "', 'pTrhdBus'='" + pTrhdBus
				+ "', 'pTrhdCrib'='" + pTrhdCrib + "', 'pTrhdFlexi'='" + pTrhdFlexi + "', 'pTrhdBsCd'='" + pTrhdBsCd
				+ "', 'pTrhdBsTr'='" + pTrhdBsTr + "', 'pTrhdMgTr'='" + pTrhdMgTr + "', 'pTrhdReSeq'='" + pTrhdReSeq
				+ "', 'pTrhdCustTyp'='" + pTrhdCustTyp + "', 'pTrhdReward'='" + pTrhdReward + "', 'pTrhdQuo'='" + pTrhdQuo
				+ "', 'pTrhdStmApp'='" + pTrhdStmApp + "', 'pTrhdInsuCoverFlg'='" + pTrhdInsuCoverFlg + "', 'pTrhdInsuCoverAmt'='"
				+ pTrhdInsuCoverAmt + "', 'pTrhdIntrType'='" + pTrhdIntrType + "', 'pTrhdRewardPre'='" + pTrhdRewardPre
				+ "', 'pTrhdRewardType'='" + pTrhdRewardType + "', 'pTrhdRewardBusagent'='" + pTrhdRewardBusagent
				+ "', 'pTrhdRewardAddMethod'='" + pTrhdRewardAddMethod + "', 'pTrhdSplitReward'='" + pTrhdSplitReward
				+ "', 'pInsuOption'='" + pInsuOption + "', 'pInsuAddCrit'='" + pInsuAddCrit + "', 'pTrhdDep'='" + pTrhdDep
				+ "', 'pTrhdRv'='" + pTrhdRv + "', 'pAttribute1'='" + pAttribute1 + "', 'pAttribute2'='" + pAttribute2
				+ "', 'pAttribute3'='" + pAttribute3 + "', 'pAttribute4'='" + pAttribute4 + "', 'pTrhdVatUpfrnt'='" + pTrhdVatUpfrnt
				+ "', 'pTrhdVatChg'='" + pTrhdVatChg
				+ "']";
	}
	
	
	
}

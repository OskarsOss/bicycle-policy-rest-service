class RiskTheftScript extends BaseScript {

	@Override
	Object run() {
		return null
	}
	
	// TODO could be common
	def double getRiskBasePremium(String risk) {
		return getRiskBasePremiumData().find{it.RISK_TYPE == risk}.PREMIUM;
	}
	
	def double getSumInsuredFactor(double sumInsured) {
		def factor = getSumInsuredFactorData().find{it.VALUE_FROM < sumInsured && it.VALUE_TO > sumInsured}
		return factor.FACTOR_MAX - (factor.FACTOR_MAX - factor.FACTOR_MIN) * (factor.FACTOR_MIN - sumInsured) / (factor.VALUE_TO - factor.VALUE_FROM)
	}
	
	def double calculatePremium(double sumInsured) {
		def basePremium = getRiskBasePremium("THEFTs")
		def sumInsuredFactor = getSumInsuredFactor(sumInsured)
		return basePremium * sumInsuredFactor;		
	}

}

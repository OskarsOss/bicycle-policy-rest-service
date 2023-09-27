package scripts;

class RiskThirdPartyDamageScript extends CommonCalculationScript {

	@Override
	Object run() {
		return null
	}
	
	def double calculatePremium(HashMap<String, Serializable> args) {
		def basePremium = getRiskBasePremium("THIRD_PARTY_DAMAGE")
		def sumInsuredFactor = getSumInsuredFactor(args["sumInsured"])
		def riskCountFactor = getRiskCountFactor(args["riskCount"])
		return basePremium * sumInsuredFactor * riskCountFactor;		
	}
	
	def double calculateSumInsured(double sumInsured) {
		return 100;
	}

}

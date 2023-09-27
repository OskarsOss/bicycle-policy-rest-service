package scripts;

class RiskDamageScript extends CommonCalculationScript {

	@Override
	Object run() {
		return null
	}
	
	def double calculatePremium(HashMap<String, Object> args) {
		def basePremium = getRiskBasePremium("DAMAGE")
		def sumInsuredFactor = getSumInsuredFactor(args["sumInsured"])
		def ageFactor = getAgeFactor(args["age"], args["make"], args["model"])
		return basePremium * sumInsuredFactor * ageFactor;		
	}
	
	def double calculateSumInsured(double sumInsured) {
		return sumInsured / 2;
	}

}

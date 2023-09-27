package scripts;

class RiskTheftScript extends CommonCalculationScript {

	@Override
	Object run() {
		return null
	}

	def double calculatePremium(HashMap<String, Serializable> args) {
		def basePremium = getRiskBasePremium("THEFT")
		def sumInsuredFactor = getSumInsuredFactor(args["sumInsured"])
		return basePremium * sumInsuredFactor;		
	}
	
	def double calculateSumInsured(double sumInsured) {
		return sumInsured;
	}

}

package scripts;

class CommonCalculationScript extends BaseScript {

	@Override
	Object run() {
		return null
	}
	
	def double getRiskBasePremium(String risk) {
		return (getRiskBasePremiumData().find { it.RISK_TYPE == risk })["PREMIUM"];
	}
	
	def double getSumInsuredFactor(double sumInsured) {
		def factor = getSumInsuredFactorData().find { it.VALUE_FROM <= sumInsured && it.VALUE_TO >= sumInsured }
		return factor.FACTOR_MAX - (factor.FACTOR_MAX - factor.FACTOR_MIN) * (factor.FACTOR_MIN - sumInsured) / (factor.VALUE_TO - factor.VALUE_FROM)
	}

	/*
	 * Filter data by object make, model and age.
	 * If there are no matching data repeat search by make and age. 
	 * If there are no matching data repeat search by age only.
	 */
	def getRelevantBicycleAgeFactorData(int age, String make, String model) {
		def bicycleData = getAgeFactorData().findAll{it.MODEL == model}
		if (bicycleData == null) {
			bicycleData = getAgeFactorData()
		}
		bicycleData = bicycleData.findAll{it.MAKE == make}
		if (bicycleData == null) {
			bicycleData = getAgeFactorData()
		}
		return bicycleData.find { it.VALUE_FROM <= age && it.VALUE_TO >= age }
	}
	
	def double getAgeFactor(int age, String make, String model) {
		def ageFactorData = getRelevantBicycleAgeFactorData(age, make, model)
		return ageFactorData.FACTOR_MAX - (ageFactorData.FACTOR_MAX - ageFactorData.FACTOR_MIN) * (ageFactorData.VALUE_TO - age) / (ageFactorData.VALUE_TO - ageFactorData.VALUE_FROM)
	}
	
	def double getRiskCountFactor(int riskCount) {
		def factorData = getRiskCountFactorData().find { it.VALUE_FROM <= riskCount && it.VALUE_TO >= riskCount }
		return factorData.FACTOR_MAX - (factorData.FACTOR_MAX - factorData.FACTOR_MIN) * (factorData.VALUE_TO - riskCount) / (factorData.VALUE_TO - factorData.VALUE_FROM)
	}
	
}

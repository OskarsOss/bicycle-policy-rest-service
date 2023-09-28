# bicycle-policy-rest-service

Task description and requirements for the project can be found in `task` directory.

## Requirements
- JDK 17

## Running the service
1. Go to project dir
2. execute command `.\mvnw clean package` to build and run tests.
3. run `java -jar .\target\bicycle-policy-rest-service-0.0.1.jar` to launch the service.
Alternatively pass in argument to specify the location of the groovy script files e.g. 
`java -jar .\target\bicycle-policy-rest-service-0.0.1.jar --scripts_location=C:\scripts`

## Groovy script files
- Groovy script files used in calculations are included at `/src/main/resources/scripts` inside the project dir.
It's possible to set a custom directory where to look for scripts files by passing `--scripts_location` argument to app.
- Premium and sum insured calculations are in `.groovy` script files for each possible risk type and naming follows the pattern `risk_{risk_type}.groovy` where `risk_type` is a lowercase name of the risk e.g. `risk_third_party_damage.groovy`. Any new risk files should follow this pattern.
- The script file should include two functions `def double calculatePremium(HashMap<String, Object> args)` and `def double calculateSumInsured(double sumInsured)` that implement specific calculations for both (see existing risk script files as examples).
- Common calculation functions that are used by risk scripts are located in `CommonCalculationScript.groovy`. In addition to the `BaseScript.groovy` that holds data for bicycle and risk details.

## Troubleshooting
- if you are running the project from Eclipse IDE then make sure to install lombok for it. Refer to https://stackoverflow.com/a/45217235
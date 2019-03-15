package org.openmrs.module.southsudanemr.deploy;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.southsudanemr.metadata.EncounterTypes;
import org.openmrs.module.southsudanemr.metadata.PatientIdentifierTypes;
import org.openmrs.module.southsudanemr.metadata.PersonAttributeTypes;
import org.openmrs.module.southsudanemr.metadata.Privileges;
import org.openmrs.module.southsudanemr.metadata.ProgramTypes;
import org.openmrs.module.southsudanemr.metadata.Roles;
import org.springframework.stereotype.Component;

@Component
public class SsCommonMetadataBundle extends AbstractMetadataBundle {
	
	public void install() throws Exception {
		// install the patient identifier type
		install(PatientIdentifierTypes.ART_NUMBER);
		install(PatientIdentifierTypes.HEI_NUMBER);
		
		//install person attribute types here
		install(PersonAttributeTypes.MOBILE_NUMBER);
		install(PersonAttributeTypes.ALTERNATIVE_MOBILE_NUMBER);
		install(PersonAttributeTypes.TREATMENT_SUPPOTER_FIRST_NAME);
		install(PersonAttributeTypes.TREATMENT_SUPPOTER_LAST_NAME);
		install(PersonAttributeTypes.TREATMENT_SUPPOTER_MOBILE_NUMBER);
		install(PersonAttributeTypes.TREATMENT_SUPPOTER_ADDRESS);
		install(PersonAttributeTypes.HOME_BASED_CARE_PROVIDER);
		install(PersonAttributeTypes.VILLAGE);
		install(PersonAttributeTypes.LAND_MARK);
		
		//install encounter types
		install(EncounterTypes.ART_INITIAL_ENCOUNTER_TYPE);
		install(EncounterTypes.ART_FOLLOW_UP_ENCOUNTER_TYPE);
		install(EncounterTypes.ART_DISCONTINUES_ENCOUNTER_TYPE);
		install(EncounterTypes.VITALS_ENCOUNTER_TYPE);
		
		//install programs
		install(ProgramTypes.MH_PROGRAM);
		
		//install privileges
		install(Privileges.APP_SSEMRREPORTS_PRV);
		//install roles here
		install(Roles.SS_USERS);
		
	}
	
}

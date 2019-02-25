package org.openmrs.module.southsudanemr.deploy;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.southsudanemr.metadata.PatientIdentifierTypes;
import org.springframework.stereotype.Component;

@Component
public class SsCommonMetadataBundle extends AbstractMetadataBundle {
	
	public void install() throws Exception {
		// install the patient identifier type
		install(PatientIdentifierTypes.ART_NUMBER);
		install(PatientIdentifierTypes.HEI_NUMBER);
	}
	
}